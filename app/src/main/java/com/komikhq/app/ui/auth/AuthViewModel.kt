package com.komikhq.app.ui.auth

import android.app.Activity
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komikhq.app.BuildConfig
import com.komikhq.app.core.auth.GoogleAuthManager
import com.komikhq.app.data.local.TokenStorage
import com.komikhq.app.data.network.BetterAuthService
import com.komikhq.app.data.network.SignInWithIdTokenRequest
import com.komikhq.sdk.apis.AuthenticationApi
import com.komikhq.sdk.models.SignInWithEmailRequest
import com.komikhq.sdk.models.SignInWithSocialProviderRequest
import com.komikhq.sdk.models.SignUpWithEmailRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException
import javax.inject.Inject

sealed interface AuthUiState {
    data object Idle : AuthUiState
    data object Loading : AuthUiState
    data object Success : AuthUiState
    data class Error(val message: String) : AuthUiState
    data class SocialRedirect(val url: String) : AuthUiState
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authApi: AuthenticationApi,
    private val betterAuthService: BetterAuthService,
    private val tokenStorage: TokenStorage,
    private val googleAuthManager: GoogleAuthManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val json = Json { ignoreUnknownKeys = true }

    fun signIn(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("Email and password cannot be empty")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val response = authApi.signInWithEmail(
                    SignInWithEmailRequest(
                        email = email.trim(),
                        password = password,
                        callbackURL = "https://komikhq.com"
                    )
                )
                val token = extractToken(response)
                if (token != null) {
                    tokenStorage.saveToken(token)
                    _uiState.value = AuthUiState.Success
                } else {
                    _uiState.value = AuthUiState.Error("Sign-in succeeded but session token was not found")
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(parseErrorMessage(e))
            }
        }
    }

    fun signUp(name: String, email: String, password: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("All fields are required")
            return
        }
        if (password.length < 8) {
            _uiState.value = AuthUiState.Error("Password must be at least 8 characters")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val response = authApi.signUpWithEmail(
                    SignUpWithEmailRequest(
                        name = name.trim(),
                        email = email.trim(),
                        password = password,
                        callbackURL = "https://komikhq.com"
                    )
                )
                val token = extractToken(response)
                if (token != null) {
                    tokenStorage.saveToken(token)
                    _uiState.value = AuthUiState.Success
                } else {
                    _uiState.value = AuthUiState.Success
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(parseErrorMessage(e))
            }
        }
    }

    fun signInWithGoogleNative(activity: Activity) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = googleAuthManager.signIn(activity, BuildConfig.GOOGLE_WEB_CLIENT_ID)
            result.onSuccess { authResult ->
                try {
                    val response = betterAuthService.signInWithIdToken(
                        SignInWithIdTokenRequest(
                            provider = "google",
                            idToken = authResult.idToken
                        )
                    )
                    val token = extractToken(response)
                    if (token != null) {
                        tokenStorage.saveToken(token)
                        _uiState.value = AuthUiState.Success
                    } else {
                        _uiState.value = AuthUiState.Error("Signed in with Google but session token was missing")
                    }
                } catch (e: Exception) {
                    _uiState.value = AuthUiState.Error(parseErrorMessage(e))
                }
            }.onFailure { error ->
                if (error is GetCredentialCancellationException) {
                    _uiState.value = AuthUiState.Idle
                } else {
                    _uiState.value = AuthUiState.Error(error.localizedMessage ?: "Google Sign-in failed")
                }
            }
        }
    }

    fun onSocialTokenReceived(token: String) {
        viewModelScope.launch {
            tokenStorage.saveToken(token)
            _uiState.value = AuthUiState.Success
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }

    private fun extractToken(response: Any?): String? {
        val map = response as? Map<*, *> ?: return null
        val directToken = map["token"] as? String
        if (!directToken.isNullOrBlank()) return directToken
        val sessionMap = map["session"] as? Map<*, *>
        return sessionMap?.get("token") as? String
    }

    private fun parseErrorMessage(e: Exception): String {
        if (e is HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            if (!errorBody.isNullOrBlank()) {
                try {
                    val jsonObj = json.parseToJsonElement(errorBody).jsonObject
                    val message = jsonObj["message"]?.jsonPrimitive?.content
                    if (!message.isNullOrBlank()) {
                        return if (message == "Invalid email or password") {
                            "Invalid email or password"
                        } else if (message.contains("already exists", ignoreCase = true)) {
                            "Email already registered. Please sign in."
                        } else {
                            message
                        }
                    }
                } catch (_: Exception) {}
            }
            return when (e.code()) {
                401 -> "Invalid email or password"
                409 -> "Email already registered"
                429 -> "Too many attempts. Please try again later."
                else -> "Server error occurred (${e.code()})"
            }
        }
        return when {
            e.message?.contains("Unable to resolve host") == true -> "No internet connection"
            e.message?.contains("timeout") == true -> "Connection timed out, please try again"
            else -> e.message ?: "An unexpected error occurred"
        }
    }
}

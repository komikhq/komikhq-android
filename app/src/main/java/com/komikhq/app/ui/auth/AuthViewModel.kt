package com.komikhq.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komikhq.app.data.local.TokenStorage
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
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val json = Json { ignoreUnknownKeys = true }

    fun signIn(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("Email dan password tidak boleh kosong")
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
                    _uiState.value = AuthUiState.Error("Login berhasil tetapi token tidak ditemukan")
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(parseErrorMessage(e))
            }
        }
    }

    fun signUp(name: String, email: String, password: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("Semua field wajib diisi")
            return
        }
        if (password.length < 8) {
            _uiState.value = AuthUiState.Error("Password minimal 8 karakter")
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

    fun signInWithGoogle() {
        // Triggered via OAuthWebViewDialog or direct flow
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
                            "Email atau password salah"
                        } else if (message.contains("already exists", ignoreCase = true)) {
                            "Email sudah terdaftar. Silakan masuk."
                        } else {
                            message
                        }
                    }
                } catch (_: Exception) {}
            }
            return when (e.code()) {
                401 -> "Email atau password salah"
                409 -> "Email sudah terdaftar"
                429 -> "Terlalu banyak percobaan. Silakan coba lagi nanti."
                else -> "Terjadi kesalahan server (${e.code()})"
            }
        }
        return when {
            e.message?.contains("Unable to resolve host") == true -> "Tidak ada koneksi internet"
            e.message?.contains("timeout") == true -> "Koneksi time out, silakan coba lagi"
            else -> e.message ?: "Terjadi kesalahan"
        }
    }
}

package com.komikhq.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komikhq.app.data.local.TokenStorage
import com.komikhq.sdk.apis.AuthenticationApi
import com.komikhq.sdk.models.SignInWithEmailRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AuthUiState {
    data object Idle : AuthUiState
    data object Loading : AuthUiState
    data object Success : AuthUiState
    data class Error(val message: String) : AuthUiState
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authApi: AuthenticationApi,
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun signIn(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("Email dan password tidak boleh kosong")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val response = authApi.signInWithEmail(
                    SignInWithEmailRequest(email = email, password = password)
                )
                // Better Auth returns session token in response
                // The response is kotlin.Any, we need to extract the token
                val responseMap = response as? Map<*, *>
                val token = responseMap?.get("token") as? String
                    ?: (responseMap?.get("session") as? Map<*, *>)?.get("token") as? String

                if (token != null) {
                    tokenStorage.saveToken(token)
                    _uiState.value = AuthUiState.Success
                } else {
                    _uiState.value = AuthUiState.Error("Login berhasil tetapi token tidak ditemukan")
                }
            } catch (e: Exception) {
                val msg = when {
                    e.message?.contains("401") == true -> "Email atau password salah"
                    e.message?.contains("404") == true -> "Akun tidak ditemukan"
                    e.message?.contains("Unable to resolve host") == true -> "Tidak ada koneksi internet"
                    else -> e.message ?: "Terjadi kesalahan"
                }
                _uiState.value = AuthUiState.Error(msg)
            }
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }
}


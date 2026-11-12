package com.komikhq.app.data.network

import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

@Serializable
data class SignInWithIdTokenRequest(
    val provider: String = "google",
    val idToken: String
)

interface BetterAuthService {
    @POST("v1/auth/sign-in/id-token")
    suspend fun signInWithIdToken(@Body request: SignInWithIdTokenRequest): Any
}


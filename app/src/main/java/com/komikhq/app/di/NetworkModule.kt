package com.komikhq.app.di

import com.komikhq.app.data.network.AnyJsonConverterFactory
import com.komikhq.app.data.network.AuthInterceptor
import com.komikhq.sdk.apis.AuthenticationApi
import com.komikhq.sdk.apis.UsersApi
import com.komikhq.sdk.infrastructure.ApiClient
import com.komikhq.sdk.infrastructure.Serializer.kotlinxSerializationJson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideApiClient(okHttpClient: OkHttpClient): ApiClient {
        val converterFactories = listOf(
            AnyJsonConverterFactory(),
            ScalarsConverterFactory.create(),
            kotlinxSerializationJson.asConverterFactory("application/json".toMediaType())
        )
        return ApiClient(
            okHttpClientBuilder = okHttpClient.newBuilder(),
            converterFactories = converterFactories
        )
    }

    @Provides
    @Singleton
    fun provideAuthenticationApi(apiClient: ApiClient): AuthenticationApi {
        return apiClient.createService(AuthenticationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersApi(apiClient: ApiClient): UsersApi {
        return apiClient.createService(UsersApi::class.java)
    }
}

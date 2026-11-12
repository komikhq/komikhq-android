package com.komikhq.app.di

import com.komikhq.app.data.network.AnyJsonConverterFactory
import com.komikhq.app.data.network.AnySerializer
import com.komikhq.app.data.network.AuthInterceptor
import com.komikhq.sdk.apis.AuthenticationApi
import com.komikhq.sdk.apis.UsersApi
import com.komikhq.sdk.infrastructure.ApiClient
import com.komikhq.sdk.infrastructure.Serializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
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
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            serializersModule = SerializersModule {
                include(Serializer.kotlinxSerializationAdapters)
                contextual(AnySerializer)
            }
        }

        val converterFactories = listOf(
            AnyJsonConverterFactory(),
            ScalarsConverterFactory.create(),
            json.asConverterFactory("application/json".toMediaType())
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
    fun provideBetterAuthService(apiClient: ApiClient): com.komikhq.app.data.network.BetterAuthService {
        return apiClient.createService(com.komikhq.app.data.network.BetterAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersApi(apiClient: ApiClient): UsersApi {
        return apiClient.createService(UsersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideComicsApi(apiClient: ApiClient): com.komikhq.sdk.apis.ComicsApi {
        return apiClient.createService(com.komikhq.sdk.apis.ComicsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBookmarksApi(apiClient: ApiClient): com.komikhq.sdk.apis.BookmarksApi {
        return apiClient.createService(com.komikhq.sdk.apis.BookmarksApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReadingHistoryApi(apiClient: ApiClient): com.komikhq.sdk.apis.ReadingHistoryApi {
        return apiClient.createService(com.komikhq.sdk.apis.ReadingHistoryApi::class.java)
    }
}

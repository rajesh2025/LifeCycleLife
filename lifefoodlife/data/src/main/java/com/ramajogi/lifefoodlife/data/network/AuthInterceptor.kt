package com.ramajogi.lifefoodlife.data.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
    private val authApiService: AuthApiService,
    private val ioDispatcher: CoroutineDispatcher
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = tokenManager.getAccessToken()
            ?: return chain.proceed(originalRequest) // No token, proceed without auth

        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(authenticatedRequest)

        if (response.code == 401) {
            synchronized(this) {
                // Double-check the token in case another thread refreshed it
                val currentToken = tokenManager.getAccessToken()
                if (currentToken != accessToken) {
                    // Token was refreshed by another thread, retry with new token
                    val retryRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer $currentToken")
                        .build()
                    return chain.proceed(retryRequest)
                }

                // Refresh token using runBlocking
                val newAccessToken = runBlocking(ioDispatcher) {
                    refreshToken()
                }
                if (newAccessToken != null) {
                    tokenManager.saveAccessToken(newAccessToken)
                    val retryRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer $newAccessToken")
                        .build()
                    return chain.proceed(retryRequest)
                }
            }
            // If refresh fails, return the original 401 response
            return response
        }

        return response
    }

    private suspend fun refreshToken(): String? {
        val refreshToken = tokenManager.getRefreshToken() ?: return null
        return try {
            val response = authApiService.refreshToken(RefreshTokenRequest(refreshToken))
            tokenManager.saveRefreshToken(response.refreshToken)
            response.accessToken
        } catch (e: Exception) {
            null
        }
    }
}
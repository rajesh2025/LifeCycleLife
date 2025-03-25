package com.ramajogi.lifefoodlife.data.network

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): TokenResponse
}
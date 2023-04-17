package com.lifecycle.loginapp.data.api.method

import com.lifecycle.loginapp.data.api.ApiClient
import com.lifecycle.loginapp.data.api.request.LoginRequest
import com.lifecycle.loginapp.data.api.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
@POST("/api/authaccount/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest):Response<LoginResponse>

    companion object{
        fun getApi(): UserApi?{
            return ApiClient.client?.create(UserApi::class.java)
        }
    }
}
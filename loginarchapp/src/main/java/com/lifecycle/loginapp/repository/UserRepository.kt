package com.lifecycle.loginapp.repository

import com.lifecycle.loginapp.data.api.method.UserApi
import com.lifecycle.loginapp.data.api.request.LoginRequest
import com.lifecycle.loginapp.data.api.response.LoginResponse
import retrofit2.Response

class UserRepository {
    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return UserApi.getApi()?.loginUser(loginRequest = loginRequest)
    }
}
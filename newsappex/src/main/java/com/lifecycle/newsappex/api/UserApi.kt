package com.lifecycle.newsappex.api

import com.lifecycle.newsappex.models.UserRequest
import com.lifecycle.newsappex.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("users/signin")
    suspend fun userSignIn(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("users/signup")
    suspend fun userSignUp(@Body userRequest: UserRequest): Response<UserResponse>
}
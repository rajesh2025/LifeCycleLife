package com.ramajogi.lifefoodlife.data.network

import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
@POST("orders")
    suspend fun placeDeliveryOrder(@Body request : PlaceOrderRequest): DeliveryOrder

    @GET("orders/{orderId}")
    suspend fun trackDelivery(@Path("orderId") orderId: String): DeliveryOrder?

    @PUT("orders/{orderId}/status")
    suspend fun updateDeliveryStatus(
        @Path("orderId") orderId: String,
        @Body request: UpdateStatusRequest
    ): DeliveryOrder?

    @POST("auth/refresh")
    suspend fun refreshToken(@Body refreshToken: RefreshTokenRequest): TokenResponse
}

data class PlaceOrderRequest(val recipeId: Int)
data class UpdateStatusRequest(val status: DeliveryStatus)
data class RefreshTokenRequest(val refreshToken: String)
data class TokenResponse(val accessToken: String, val refreshToken: String)

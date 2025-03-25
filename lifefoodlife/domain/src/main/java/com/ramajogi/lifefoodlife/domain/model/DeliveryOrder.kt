package com.ramajogi.lifefoodlife.domain.model

import java.util.Date

data class DeliveryOrder(
    val orderId: String,
    val recipeId: Int,
    val status: DeliveryStatus,
    val placedAt: Date,
    val estimatedDeliveryTime: Date?
)

enum class DeliveryStatus {
    PLACED, PREPARING, SHIPPED, DELIVERED, CANCELLED
}
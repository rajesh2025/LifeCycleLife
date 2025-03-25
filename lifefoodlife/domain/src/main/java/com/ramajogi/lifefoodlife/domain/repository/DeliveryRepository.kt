package com.ramajogi.lifefoodlife.domain.repository

import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus

interface DeliveryRepository {
    suspend fun trackDelivery(orderId : String): DeliveryOrder?
    suspend fun placeDeliveryOrder(recipeId : Int): DeliveryOrder
    suspend fun updateDeliveryStatus(orderId: String, newStatus: DeliveryStatus): DeliveryOrder?
}
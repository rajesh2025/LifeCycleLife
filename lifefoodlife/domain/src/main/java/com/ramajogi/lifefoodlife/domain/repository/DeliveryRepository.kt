package com.ramajogi.lifefoodlife.domain.repository

import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import com.ramajogi.lifefoodlife.domain.model.LFLResult

interface DeliveryRepository {
    suspend fun trackDelivery(orderId : String): LFLResult<DeliveryOrder?>
    suspend fun placeDeliveryOrder(recipeId : Int): LFLResult<DeliveryOrder>
    suspend fun updateDeliveryStatus(orderId: String, newStatus: DeliveryStatus): LFLResult<DeliveryOrder?>
}
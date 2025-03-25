package com.ramajogi.lifefoodlife.domain.usecase

import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import com.ramajogi.lifefoodlife.domain.repository.DeliveryRepository

class UpdateDeliveryStatus(private val repository: DeliveryRepository) {

    suspend operator fun invoke(orderId: String, newStatus: DeliveryStatus): DeliveryOrder? {
        // Business rule: Cannot update status if order doesn’t exist
        val existingOrder = repository.trackDelivery(orderId) ?: return null
        
        // Business rule: Prevent invalid transitions (e.g., DELIVERED -> PLACED)
        if (!isValidStatusTransition(existingOrder.status, newStatus)) {
            throw IllegalStateException("Invalid status transition from ${existingOrder.status} to $newStatus")
        }
        
        return repository.updateDeliveryStatus(orderId, newStatus)
    }

    private fun isValidStatusTransition(current: DeliveryStatus, new: DeliveryStatus): Boolean {
        return when (current) {
            DeliveryStatus.PLACED -> new in listOf(DeliveryStatus.PREPARING, DeliveryStatus.CANCELLED)
            DeliveryStatus.PREPARING -> new in listOf(DeliveryStatus.SHIPPED, DeliveryStatus.CANCELLED)
            DeliveryStatus.SHIPPED -> new == DeliveryStatus.DELIVERED
            DeliveryStatus.DELIVERED -> false // No further updates allowed
            DeliveryStatus.CANCELLED -> false // No further updates allowed
        }
    }
}
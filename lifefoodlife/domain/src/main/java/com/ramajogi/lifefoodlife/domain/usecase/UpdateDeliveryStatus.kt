package com.ramajogi.lifefoodlife.domain.usecase

import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import com.ramajogi.lifefoodlife.domain.model.LFLResult
import com.ramajogi.lifefoodlife.domain.repository.DeliveryRepository

class UpdateDeliveryStatus(private val repository: DeliveryRepository) {

    suspend operator fun invoke(
        orderId: String,
        newStatus: DeliveryStatus
    ): LFLResult<DeliveryOrder?> {
        if (orderId.isBlank()) {
            return LFLResult.Failure(
                LFLResult.Failure.ErrorType.InvalidInput(
                    "orderId",
                    "Order ID cannot be empty"
                )
            )
        }
        // Business rule: Cannot update status if order doesn’t exist
        val existingOrder = repository.trackDelivery(orderId)
        return when (existingOrder) {
            is LFLResult.Success -> {
                val currentOrder = existingOrder.data
                if (currentOrder == null) {
                    LFLResult.Failure(LFLResult.Failure.ErrorType.NotFound)
                } else {
                    val currentStatus = currentOrder.status
                    // Business rule: Prevent invalid transitions (e.g., DELIVERED -> PLACED)
                    if (!isValidStatusTransition(currentStatus, newStatus)) {
                        LFLResult.Failure(
                            LFLResult.Failure.ErrorType.InvalidStateTransition(
                                currentStatus.name, newStatus.name
                            )
                        )
                    } else {
                        repository.updateDeliveryStatus(orderId, newStatus)
                    }
                }
            }
            is LFLResult.Failure -> existingOrder // Propagate data-layer error
        }
    }

    private fun isValidStatusTransition(current: DeliveryStatus, new: DeliveryStatus): Boolean {
        return when (current) {
            DeliveryStatus.PLACED -> new in listOf(
                DeliveryStatus.PREPARING,
                DeliveryStatus.CANCELLED
            )

            DeliveryStatus.PREPARING -> new in listOf(
                DeliveryStatus.SHIPPED,
                DeliveryStatus.CANCELLED
            )

            DeliveryStatus.SHIPPED -> new == DeliveryStatus.DELIVERED
            DeliveryStatus.DELIVERED -> false // No further updates allowed
            DeliveryStatus.CANCELLED -> false // No further updates allowed
        }
    }
}
package com.ramajogi.lifefoodlife.domain.usecase

import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.LFLResult
import com.ramajogi.lifefoodlife.domain.repository.DeliveryRepository


class PlaceDeliveryOrder(private val deliveryRepository: DeliveryRepository) {

    suspend operator fun invoke(recipeId: Int): LFLResult<DeliveryOrder> {
        // Business rule validation
        if (recipeId <= 0) {
            return LFLResult.Failure(LFLResult.Failure.ErrorType.InvalidInput("recipeId", "Recipe ID must be positive"))
        }
        // Additional rule: Check if recipe is orderable (hypothetical)
        // For demo, assume all recipes are orderable; in reality, this might query a status
        return deliveryRepository.placeDeliveryOrder(recipeId)
    }
}
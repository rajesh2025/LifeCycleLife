package com.ramajogi.lifefoodlife.domain.usecase

import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.repository.DeliveryRepository


class PlaceDeliveryOrder(private val deliveryRepository: DeliveryRepository) {

    suspend operator fun invoke(recipeId: Int): DeliveryOrder {
        return deliveryRepository.placeDeliveryOrder(recipeId)
    }
}
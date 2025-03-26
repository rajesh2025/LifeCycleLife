package com.ramajogi.lifefoodlife.domain.usecase

import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.LFLResult
import com.ramajogi.lifefoodlife.domain.repository.DeliveryRepository

class TrackDelivery(private val repository: DeliveryRepository) {
    suspend operator fun invoke(orderId: String): LFLResult<DeliveryOrder?> {
        return repository.trackDelivery(orderId)
    }
}
package com.ramajogi.lifefoodlife.presentation.ui.intent

import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus

// Intent represents user actions or events
sealed class DeliveryIntent {
    data class PlaceOrder(val recipeId: Int) : DeliveryIntent()
    data class TrackOrder(val orderId: String) : DeliveryIntent()
    data class UpdateStatus(val orderId: String, val newStatus: DeliveryStatus) : DeliveryIntent()
    object ClearError : DeliveryIntent()
}

// State represents the UI's current state
data class DeliveryState(
    val deliveryOrder: DeliveryOrder? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
package com.ramajogi.lifefoodlife.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import com.ramajogi.lifefoodlife.domain.usecase.PlaceDeliveryOrder
import com.ramajogi.lifefoodlife.domain.usecase.TrackDelivery
import com.ramajogi.lifefoodlife.domain.usecase.UpdateDeliveryStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val placeDeliveryOrder: PlaceDeliveryOrder,
    private val trackDelivery: TrackDelivery,
    private val updateDeliveryStatus: UpdateDeliveryStatus
) : ViewModel() {
    private val _deliveryOrder = MutableStateFlow<DeliveryOrder?>(null)
    val deliveryOrder: StateFlow<DeliveryOrder?> = _deliveryOrder

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun placeOrder(recipeId: Int) {
        viewModelScope.launch {
            try {
                val order = placeDeliveryOrder(recipeId)
                _deliveryOrder.value = order
            } catch (e: Exception) {
                _errorMessage.value = "Failed to place order: ${e.message}"
            }
        }
    }

    fun trackOrder(orderId: String) {
        viewModelScope.launch {
            _deliveryOrder.value = trackDelivery(orderId)
        }
    }

    fun updateStatus(orderId: String, newStatus: DeliveryStatus) {
        viewModelScope.launch {
            try {
                val updatedOrder = updateDeliveryStatus(orderId, newStatus)
                _deliveryOrder.value = updatedOrder
                if (updatedOrder == null) _errorMessage.value = "Order not found"
            } catch (e: IllegalStateException) {
                _errorMessage.value = e.message
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun setDeliveryOrder(order: DeliveryOrder?) {
        _deliveryOrder.value = order
    }
}
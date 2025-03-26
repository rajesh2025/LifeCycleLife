package com.ramajogi.lifefoodlife.presentation.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import com.ramajogi.lifefoodlife.domain.model.LFLResult
import com.ramajogi.lifefoodlife.domain.usecase.PlaceDeliveryOrder
import com.ramajogi.lifefoodlife.domain.usecase.TrackDelivery
import com.ramajogi.lifefoodlife.domain.usecase.UpdateDeliveryStatus
import com.ramajogi.lifefoodlife.presentation.ui.intent.DeliveryIntent
import com.ramajogi.lifefoodlife.presentation.ui.intent.DeliveryState
import com.ramajogi.lifefoodlife.presentation.utils.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val placeDeliveryOrder: PlaceDeliveryOrder,
    private val trackDelivery: TrackDelivery,
    private val updateDeliveryStatus: UpdateDeliveryStatus
) : ViewModel() {

    private val _state = MutableStateFlow(DeliveryState())
    val state: StateFlow<DeliveryState> = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _state.value = _state.value.copy(
            isLoading = false, errorMessage = when (throwable) {
                is IllegalStateException -> throwable.message
                else -> "Error: ${throwable.message}"
            }
        )
    }

    fun processIntent(intent: DeliveryIntent) {
        when (intent) {
            is DeliveryIntent.PlaceOrder -> placeOrder(intent.recipeId)
            is DeliveryIntent.TrackOrder -> trackOrder(intent.orderId)
            is DeliveryIntent.UpdateStatus -> updateStatus(intent.orderId, intent.newStatus)
            DeliveryIntent.ClearError -> clearError()
        }
    }

    fun placeOrder(recipeId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            when (val result = placeDeliveryOrder(recipeId)) {
                is LFLResult.Success -> {
                    _state.value = _state.value.copy(
                        deliveryOrder = result.data, isLoading = false
                    )
                }
                is LFLResult.Failure -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = Utility.filterDeliveryErrorMsg(error = result.error)
                    )
                }
            }
        }
    }

    fun trackOrder(orderId: String) {
        viewModelScope.launch(exceptionHandler) {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            when (val result = trackDelivery(orderId)) {
                is LFLResult.Success -> {
                    _state.value = _state.value.copy(
                        deliveryOrder = result.data, isLoading = false
                    )
                }
                is LFLResult.Failure -> _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = Utility.filterDeliveryErrorMsg(error = result.error)
                )
            }
        }
    }

    fun updateStatus(orderId: String, newStatus: DeliveryStatus) {
        viewModelScope.launch(exceptionHandler) {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            when (val result = updateDeliveryStatus(orderId, newStatus)) {
                is LFLResult.Success -> {
                    _state.value = _state.value.copy(
                        deliveryOrder = result.data, isLoading = false
                    )
                }
                is LFLResult.Failure -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = Utility.filterDeliveryErrorMsg(error = result.error)
                    )
                }
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(errorMessage = null)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setDeliveryOrder(order: DeliveryOrder?) {
        _state.value = _state.value.copy(
            deliveryOrder = order, isLoading = false
        )
    }
}
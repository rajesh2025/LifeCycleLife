package com.ramajogi.lifefoodlife.presentation.utils

import com.ramajogi.lifefoodlife.domain.model.LFLResult

object Utility {

    fun filterRecipeErrorMsg(error: LFLResult.Failure.ErrorType): String {
        return when (error) {
            LFLResult.Failure.ErrorType.NetworkError -> "No internet connection"
            is LFLResult.Failure.ErrorType.ServerError -> "Server error: ${error.code}"
            is LFLResult.Failure.ErrorType.ClientError -> "Client error: ${error.code}"
            LFLResult.Failure.ErrorType.Unauthorized -> "Please log in"
            LFLResult.Failure.ErrorType.NotFound -> "Recipes not found"
            LFLResult.Failure.ErrorType.Forbidden -> "Access denied"
            is LFLResult.Failure.ErrorType.InvalidInput -> "${error.field}: ${error.message}"
            is LFLResult.Failure.ErrorType.InvalidStateTransition -> "Cannot change from ${error.currentStatus} to ${error.newStatus}"
            LFLResult.Failure.ErrorType.ResourceNotAvailable -> "Recipe not available for order"
            is LFLResult.Failure.ErrorType.Unknown -> "Unknown error: ${error.exception.message}"
        }
    }

    fun filterDeliveryErrorMsg(error: LFLResult.Failure.ErrorType): String {
        return when (error) {
            LFLResult.Failure.ErrorType.NetworkError -> "No internet connection"
            is LFLResult.Failure.ErrorType.ServerError -> "Server error: ${error.code}"
            is LFLResult.Failure.ErrorType.ClientError -> "Client error: ${error.code}"
            LFLResult.Failure.ErrorType.Unauthorized -> "Please log in"
            LFLResult.Failure.ErrorType.NotFound -> "Order not found"
            LFLResult.Failure.ErrorType.Forbidden -> "Access denied"
            is LFLResult.Failure.ErrorType.InvalidInput -> "${error.field}: ${error.message}"
            is LFLResult.Failure.ErrorType.InvalidStateTransition -> "Cannot change from ${error.currentStatus} to ${error.newStatus}"
            LFLResult.Failure.ErrorType.ResourceNotAvailable -> "Resource unavailable"
            is LFLResult.Failure.ErrorType.Unknown -> "Unknown error: ${error.exception.message}"
        }
    }

}
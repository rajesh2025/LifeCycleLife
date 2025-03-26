package com.ramajogi.lifefoodlife.domain.model

import java.io.IOException

sealed class LFLResult<out T> {
    data class Success<out T>(val data: T) : LFLResult<T>()

    data class Failure(val error: ErrorType) : LFLResult<Nothing>() {

        constructor(fromException: Failure, error: ErrorType) : this(error)

        sealed class ErrorType {
            object NetworkError : ErrorType()
            data class ServerError(val code: Int, val message: String?) : ErrorType()
            data class ClientError(val code: Int, val message: String?) : ErrorType()
            object Unauthorized : ErrorType()
            object NotFound : ErrorType()
            object Forbidden : ErrorType()
            // Domain-specific errors
            data class Unknown(val exception: Throwable) : ErrorType()
            data class InvalidInput(val field: String, val message: String): ErrorType()
            data class InvalidStateTransition(val currentStatus: String, val newStatus: String?): ErrorType()
            object ResourceNotAvailable : ErrorType() // e.g., recipe not orderable
        }

        companion object {
            fun fromException(exception: Throwable): Failure {
                return when (exception) {
                    is IOException -> Failure(ErrorType.NetworkError)
                    is Exception -> {// change with network response type rom server
                        val code = exception.hashCode()// change code
                        val message = exception.message
                        when (code) {
                            401 -> Failure(ErrorType.Unauthorized)
                            403 -> Failure(ErrorType.Forbidden)
                            404 -> Failure(ErrorType.NotFound)
                            in 400..499 -> Failure(ErrorType.ClientError(code, message))
                            in 500..599 -> Failure(ErrorType.ServerError(code, message))
                            else -> Failure(ErrorType.Unknown(exception))
                        }
                    }
                    else -> Failure(ErrorType.Unknown(exception))
                }
            }
        }
    }
}
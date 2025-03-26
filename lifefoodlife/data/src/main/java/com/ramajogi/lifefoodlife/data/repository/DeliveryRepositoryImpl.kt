package com.ramajogi.lifefoodlife.data.repository

import com.ramajogi.lifefoodlife.data.db.DeliveryDao
import com.ramajogi.lifefoodlife.data.db.toEntity
import com.ramajogi.lifefoodlife.data.network.ApiService
import com.ramajogi.lifefoodlife.data.network.PlaceOrderRequest
import com.ramajogi.lifefoodlife.data.network.UpdateStatusRequest
import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import com.ramajogi.lifefoodlife.domain.model.LFLResult
import com.ramajogi.lifefoodlife.domain.model.LFLResult.Failure.Companion.fromException
import com.ramajogi.lifefoodlife.domain.repository.DeliveryRepository
import java.io.IOException
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(
    private val dao: DeliveryDao,
    private val api: ApiService
) : DeliveryRepository {

    override suspend fun trackDelivery(orderId: String): LFLResult<DeliveryOrder?> {
        return try {
            val localOrder = dao.getOrderById(orderId)?.toDomainModel()
            val networkOrder = api.trackDelivery(orderId)?.also { dao.insert(it.toEntity()) }
            LFLResult.Success(networkOrder ?: localOrder)
        } catch (e: Exception) {
            LFLResult.Failure(fromException(e), LFLResult.Failure.ErrorType.Unknown(e)).also {//TODO according network response type
                val localOrder = dao.getOrderById(orderId)?.toDomainModel()
                if (localOrder != null) return LFLResult.Success(localOrder) // Fallback to local
            }
        }
    }

    override suspend fun placeDeliveryOrder(recipeId: Int): LFLResult<DeliveryOrder> {
        return try {
            val order = api.placeDeliveryOrder(PlaceOrderRequest(recipeId))
            dao.insert(order.toEntity())
            LFLResult.Success(order)
        } catch (e: Exception) {
            LFLResult.Failure(fromException(e), LFLResult.Failure.ErrorType.Unknown(e))
        }
    }

    override suspend fun updateDeliveryStatus(orderId: String, newStatus: DeliveryStatus): LFLResult<DeliveryOrder?> {
        return try {
            val updatedOrder = api.updateDeliveryStatus(orderId, UpdateStatusRequest(newStatus))
            updatedOrder?.let { dao.update(it.toEntity()) }
            LFLResult.Success(updatedOrder)
        } catch (e: Exception) {
            LFLResult.Failure(fromException(e), LFLResult.Failure.ErrorType.Unknown(e)).also {
                val localOrder = dao.getOrderById(orderId)?.toDomainModel()
                if (localOrder != null) return LFLResult.Success(localOrder) // Fallback to local
            }
        }
    }


}
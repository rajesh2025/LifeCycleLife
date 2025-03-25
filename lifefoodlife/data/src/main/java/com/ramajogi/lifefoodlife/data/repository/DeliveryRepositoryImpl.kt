package com.ramajogi.lifefoodlife.data.repository

import com.ramajogi.lifefoodlife.data.db.DeliveryDao
import com.ramajogi.lifefoodlife.data.db.toEntity
import com.ramajogi.lifefoodlife.data.network.ApiService
import com.ramajogi.lifefoodlife.data.network.PlaceOrderRequest
import com.ramajogi.lifefoodlife.data.network.UpdateStatusRequest
import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import com.ramajogi.lifefoodlife.domain.repository.DeliveryRepository
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(
    private val dao: DeliveryDao,
    private val api: ApiService
) : DeliveryRepository {
    override suspend fun trackDelivery(orderId: String): DeliveryOrder? {
        val localOrder = dao.getOrderById(orderId)?.toDomainModel()
        return localOrder ?: api.trackDelivery(orderId)?.also { dao.insert(it.toEntity()) }
    }

    override suspend fun placeDeliveryOrder(recipeId: Int): DeliveryOrder {
        val order = api.placeDeliveryOrder(PlaceOrderRequest(recipeId))
        dao.insert(order.toEntity())
        return order
    }

    override suspend fun updateDeliveryStatus(orderId: String, newStatus: DeliveryStatus): DeliveryOrder? {
        val updatedOrder = api.updateDeliveryStatus(orderId, UpdateStatusRequest(newStatus))
        updatedOrder?.let { dao.update(it.toEntity()) }
        return updatedOrder
    }


}
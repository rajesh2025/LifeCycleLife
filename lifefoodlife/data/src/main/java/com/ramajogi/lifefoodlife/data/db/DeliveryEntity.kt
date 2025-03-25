package com.ramajogi.lifefoodlife.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import java.util.Date

@Entity(tableName = "delivery_orders")
data class DeliveryEntity(
    @PrimaryKey val orderId: String,
    val recipeId: Int,
    val status: DeliveryStatus,
    val placedAt: Long,
    val estimatedDeliveryTime: Long?
) {
    fun toDomainModel(): DeliveryOrder = DeliveryOrder(
        orderId, recipeId, status, Date(placedAt), estimatedDeliveryTime?.let { Date(it) }
    )
}

fun DeliveryOrder.toEntity(): DeliveryEntity = DeliveryEntity(
    orderId, recipeId, status, placedAt.time, estimatedDeliveryTime?.time
)
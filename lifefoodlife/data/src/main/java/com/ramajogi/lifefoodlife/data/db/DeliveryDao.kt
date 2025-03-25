package com.ramajogi.lifefoodlife.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DeliveryDao {
    @Insert
    suspend fun insert(order: DeliveryEntity)

    @Query("SELECT * FROM delivery_orders WHERE orderId = :orderId")
    suspend fun getOrderById(orderId: String): DeliveryEntity?

    @Update
    suspend fun update(order: DeliveryEntity)
}
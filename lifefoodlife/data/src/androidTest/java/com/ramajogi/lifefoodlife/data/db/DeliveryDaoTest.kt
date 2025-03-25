package com.ramajogi.lifefoodlife.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DeliveryDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: DeliveryDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        dao = db.deliveryDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testUpdateDeliveryStatus() = runBlocking {
        val order = DeliveryEntity("ORD123", 1, DeliveryStatus.PLACED, System.currentTimeMillis(), null)
        dao.insert(order)

        val updatedOrder = order.copy(status = DeliveryStatus.SHIPPED)
        dao.update(updatedOrder)

        val result = dao.getOrderById("ORD123")
        assertEquals(DeliveryStatus.SHIPPED, result?.status)
    }
}
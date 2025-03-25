package com.ramajogi.lifefoodlife.domain.model

import org.junit.Before
import org.junit.Test
import java.util.Date
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull


class DeliveryOrderTest {

    private lateinit var placedAt: Date
    private lateinit var estimatedDelivery: Date

    @Before
    fun setUp() {
        placedAt = Date()
        estimatedDelivery = Date(placedAt.time + 3600000) // 1 hour later
    }

    @Test
    fun testDeliveryOrderCreation() {
        val order = createDeliveryOrder()

        assertEquals("ORD123", order.orderId)
        assertEquals(1, order.recipeId)
        assertEquals(DeliveryStatus.PLACED, order.status)
        assertEquals(placedAt, order.placedAt)
        assertEquals(estimatedDelivery, order.estimatedDeliveryTime)
    }

    @Test
    fun testDeliveryOrderCreationWithNullEstimatedTime() {
        val order = createDeliveryOrder(estimatedDeliveryTime = null)

        assertEquals("ORD123", order.orderId)
        assertEquals(1, order.recipeId)
        assertEquals(DeliveryStatus.PLACED, order.status)
        assertEquals(placedAt, order.placedAt)
        assertNull(order.estimatedDeliveryTime)
    }

    @Test
    fun testDeliveryOrderStatusTransition() {
        val order = createDeliveryOrder()
        //Assuming there is a method to change the status in the DeliveryOrder class
        val preparingOrder = order.updateStatus(DeliveryStatus.PREPARING)
        assertEquals(DeliveryStatus.PREPARING, preparingOrder.status)
        // Check the dates
        assertEquals(preparingOrder.placedAt, preparingOrder.placedAt)
        assertEquals(preparingOrder.estimatedDeliveryTime, preparingOrder.estimatedDeliveryTime)
    }

    @Test
    fun testDeliveryOrderEstimatedDeliveryNotNull(){
        val order = createDeliveryOrder()
        assertNotNull(order.estimatedDeliveryTime)
    }

    @Test
    fun testDeliveryOrderCancelled() {
        val order = createDeliveryOrder()
        //Assuming there is a method to change the status in the DeliveryOrder class
        val cancelOrder = order.updateStatus(DeliveryStatus.CANCELLED)

        assertEquals(DeliveryStatus.CANCELLED, cancelOrder.status)
    }

    // Helper function to create a DeliveryOrder for DRY
    private fun createDeliveryOrder(
        orderId: String = "ORD123",
        recipeId: Int = 1,
        status: DeliveryStatus = DeliveryStatus.PLACED,
        placedAt: Date = this.placedAt,
        estimatedDeliveryTime: Date? = this.estimatedDelivery
    ): DeliveryOrder {
        return DeliveryOrder(orderId, recipeId, status, placedAt, estimatedDeliveryTime)
    }
}
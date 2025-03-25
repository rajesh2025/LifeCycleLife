package com.ramajogi.lifefoodlife.domain.usecase

import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import com.ramajogi.lifefoodlife.domain.repository.DeliveryRepository
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UpdateDeliveryStatusTest {

    private val repository = mock<DeliveryRepository>()
    private val useCase = UpdateDeliveryStatus(repository)

    @Test
    fun testUpdateStatusFromPlacedToPreparing() = runBlocking {
        val order = DeliveryOrder("ORD123", 1, DeliveryStatus.PLACED, Date(), null)
        val updatedOrder = order.copy(status = DeliveryStatus.PREPARING)

        `when`(repository.trackDelivery("ORD123")).thenReturn(order)
        `when`(repository.updateDeliveryStatus("ORD123", DeliveryStatus.PREPARING)).thenReturn(updatedOrder)

        val result = useCase("ORD123", DeliveryStatus.PREPARING)
        assertEquals(DeliveryStatus.PREPARING, result?.status)
        assertEquals("ORD123", result?.orderId)
    }

    @Test
    fun testUpdateStatusFromShippedToDelivered() = runBlocking {
        val order = DeliveryOrder("ORD456", 2, DeliveryStatus.SHIPPED, Date(), Date())
        val updatedOrder = order.copy(status = DeliveryStatus.DELIVERED)

        `when`(repository.trackDelivery("ORD456")).thenReturn(order)
        `when`(repository.updateDeliveryStatus("ORD456", DeliveryStatus.DELIVERED)).thenReturn(updatedOrder)

        val result = useCase("ORD456", DeliveryStatus.DELIVERED)
        assertEquals(DeliveryStatus.DELIVERED, result?.status)
        assertEquals("ORD456", result?.orderId)
    }

    @Test(expected = IllegalStateException::class)
    fun testInvalidTransitionFromDeliveredToPreparing() = runBlocking {
        val order = DeliveryOrder("ORD789", 3, DeliveryStatus.DELIVERED, Date(), Date())

        `when`(repository.trackDelivery("ORD789")).thenReturn(order)

        val result = useCase("ORD789", DeliveryStatus.PREPARING) // Should throw exception
        assertEquals(DeliveryStatus.DELIVERED, result?.status)
    }

    @Test
    fun testUpdateNonExistentOrder() = runBlocking {
        `when`(repository.trackDelivery("ORD999")).thenReturn(null)

        val result = useCase("ORD999", DeliveryStatus.SHIPPED)
        assertNull(result)
    }

    @Test
    fun testCancelOrderFromPreparing() = runBlocking {
        val order = DeliveryOrder("ORD101", 4, DeliveryStatus.PREPARING, Date(), null)
        val updatedOrder = order.copy(status = DeliveryStatus.CANCELLED)

        `when`(repository.trackDelivery("ORD101")).thenReturn(order)
        `when`(repository.updateDeliveryStatus("ORD101", DeliveryStatus.CANCELLED)).thenReturn(updatedOrder)

        val result = useCase("ORD101", DeliveryStatus.CANCELLED)
        assertEquals(DeliveryStatus.CANCELLED, result?.status)
        assertEquals("ORD101", result?.orderId)
    }
}
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


class TrackDeliveryTest {
    private val repository = mock<DeliveryRepository>()
    private val useCase = TrackDelivery(repository)

    @Test
    fun testTrackDelivery() = runBlocking {
        val order = DeliveryOrder("ORD123", 1, DeliveryStatus.SHIPPED, Date(), null)
        `when`(repository.trackDelivery("ORD123")).thenReturn(order)
        val result = useCase("ORD123")
        assertEquals("ORD123", result?.orderId)
        assertEquals(DeliveryStatus.SHIPPED, result?.status)
    }

    @Test
    fun testTrackDeliveryNotFound() = runBlocking {
        `when`(repository.trackDelivery("ORD999")).thenReturn(null)

        val result = useCase("ORD999")
        assertEquals(null, result)
    }
}
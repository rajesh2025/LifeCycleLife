package com.ramajogi.lifefoodlife.domain.usecase

import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import com.ramajogi.lifefoodlife.domain.repository.DeliveryRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import java.util.Date
import kotlin.test.Test


class PlaceDeliveryOrderTest {
    private val repository = mock<DeliveryRepository>()
    private val useCase = PlaceDeliveryOrder(repository)

    @Test
    fun testPlaceDeliveryOrder() = runBlocking {
        val order = DeliveryOrder("ORD123", 1, DeliveryStatus.PLACED, Date(), null)
        `when`(repository.placeDeliveryOrder(1)).thenReturn(order)

        val result = useCase(1)
        assertEquals("ORD123", result.orderId)
        assertEquals(1, result.recipeId)
        assertEquals(DeliveryStatus.PLACED, result.status)
    }
}
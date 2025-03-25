package com.ramajogi.lifefoodlife.presentation.ui.viewmodel

import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import com.ramajogi.lifefoodlife.domain.usecase.PlaceDeliveryOrder
import com.ramajogi.lifefoodlife.domain.usecase.TrackDelivery
import com.ramajogi.lifefoodlife.domain.usecase.UpdateDeliveryStatus
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.DeliveryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.util.Date

@ExperimentalCoroutinesApi
class DeliveryViewModelTest {
    private val placeDeliveryOrder = mock<PlaceDeliveryOrder>()
    private val trackDelivery = mock<TrackDelivery>()
    private val updateDeliveryStatus = mock<UpdateDeliveryStatus>()
    private lateinit var viewModel: DeliveryViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DeliveryViewModel(placeDeliveryOrder, trackDelivery, updateDeliveryStatus)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testPlaceOrder() = runTest {
        val order = DeliveryOrder("ORD123", 1, DeliveryStatus.PLACED, Date(), Date())
        `when`(placeDeliveryOrder(1)).thenReturn(order)

        viewModel.placeOrder(1)
        assertEquals(order, viewModel.deliveryOrder.value)
    }
}
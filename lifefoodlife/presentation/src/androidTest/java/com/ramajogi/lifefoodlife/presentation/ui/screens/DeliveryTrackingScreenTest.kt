package com.ramajogi.lifefoodlife.presentation.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavController
import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import com.ramajogi.lifefoodlife.domain.usecase.PlaceDeliveryOrder
import com.ramajogi.lifefoodlife.domain.usecase.TrackDelivery
import com.ramajogi.lifefoodlife.domain.usecase.UpdateDeliveryStatus
import com.ramajogi.lifefoodlife.presentation.ui.screens.DeliveryTrackingScreen
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.DeliveryViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import java.util.Date

class DeliveryTrackingScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val navController = mock<NavController>()
    private val placeDeliveryOrder = mock<PlaceDeliveryOrder>()
    private val trackDelivery = mock<TrackDelivery>()
    private val updateDeliveryStatus = mock<UpdateDeliveryStatus>()

    @Test
    fun testDeliveryTrackingShowsStatus() {
        val order = DeliveryOrder("ORD123", 1, DeliveryStatus.SHIPPED, Date(), Date())
        val viewModel = DeliveryViewModel(placeDeliveryOrder, trackDelivery, updateDeliveryStatus).apply {
            setDeliveryOrder(order)
        }

        composeTestRule.setContent {
            DeliveryTrackingScreen(viewModel, "ORD123", navController)
        }

        composeTestRule.onNodeWithText("Status: SHIPPED").assertExists()
    }
}
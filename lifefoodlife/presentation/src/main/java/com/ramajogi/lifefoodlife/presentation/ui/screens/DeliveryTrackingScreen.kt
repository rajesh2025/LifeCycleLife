package com.ramajogi.lifefoodlife.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.DeliveryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryTrackingScreen(viewModel: DeliveryViewModel, orderId: String?, navController: NavController) {
    val order by viewModel.deliveryOrder.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    LaunchedEffect(orderId) {
        orderId?.let { viewModel.trackOrder(it) }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Delivery Tracking") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            order?.let {
                Text("Order ID: ${it.orderId}", style = MaterialTheme.typography.headlineSmall)
                Text("Status: ${it.status}", style = MaterialTheme.typography.bodyLarge)
                it.estimatedDeliveryTime?.let { time ->
                    Text("Estimated Delivery: $time", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.updateStatus(it.orderId, DeliveryStatus.SHIPPED) }) {
                        Text("Mark Shipped")
                    }
                    Button(onClick = { viewModel.updateStatus(it.orderId, DeliveryStatus.DELIVERED) }) {
                        Text("Mark Delivered")
                    }
                    Button(onClick = { viewModel.updateStatus(it.orderId, DeliveryStatus.CANCELLED) }) {
                        Text("Cancel")
                    }
                }
            } ?: Text("Loading order...")
            error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.clearError() }) { Text("Dismiss") }
            }
        }
    }
}
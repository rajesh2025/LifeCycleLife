package com.ramajogi.lifefoodlife.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramajogi.lifefoodlife.domain.model.DeliveryOrder
import com.ramajogi.lifefoodlife.domain.model.DeliveryStatus
import com.ramajogi.lifefoodlife.presentation.ui.intent.DeliveryIntent
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.DeliveryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryTrackingScreen(
    viewModel: DeliveryViewModel,
    orderId: String?,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value

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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (orderId != null) {
                Button(onClick = { viewModel.processIntent(DeliveryIntent.TrackOrder(orderId)) }) {
                    Text("Track Order")
                }

                Spacer(modifier = Modifier.height(16.dp))

                when {
                    state.isLoading -> {
                        CircularProgressIndicator()
                    }

                    state.errorMessage != null -> {
                        Text(text = state.errorMessage, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.processIntent(DeliveryIntent.ClearError) }) {
                            Text("Dismiss")
                        }
                    }

                    state.deliveryOrder != null -> {
                        DeliveryOrderItem(state.deliveryOrder, viewModel)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            viewModel.processIntent(
                                DeliveryIntent.UpdateStatus(orderId, DeliveryStatus.SHIPPED)
                            )
                        }) {
                            Text("Mark as Shipped")
                        }
                    }
                }
            } else {
                Text("No order ID provided", style = MaterialTheme.typography.bodyMedium)
            }
//           if (errorMessage == null) {
//                Text("Loading order...", modifier = Modifier.align(Alignment.CenterHorizontally))
//            }
        }
    }
}

@Composable
fun DeliveryOrderItem(
    order: DeliveryOrder,
    viewModel: DeliveryViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            order.let {
                Text(
                    text = "Order ID: ${order.orderId}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = "Status: ${order.status}", style = MaterialTheme.typography.bodySmall)
                Text(
                    text = "Recipe ID: ${order.recipeId}",
                    style = MaterialTheme.typography.bodySmall
                )
                it.estimatedDeliveryTime?.let { time ->
                    Text("Estimated Delivery: $time", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        viewModel.updateStatus(
                            it.orderId,
                            DeliveryStatus.SHIPPED
                        )
                    }) {
                        Text("Mark Shipped")
                    }
                    Button(onClick = {
                        viewModel.updateStatus(
                            it.orderId,
                            DeliveryStatus.DELIVERED
                        )
                    }) {
                        Text("Mark Delivered")
                    }
                    Button(onClick = {
                        viewModel.updateStatus(
                            it.orderId,
                            DeliveryStatus.CANCELLED
                        )
                    }) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}

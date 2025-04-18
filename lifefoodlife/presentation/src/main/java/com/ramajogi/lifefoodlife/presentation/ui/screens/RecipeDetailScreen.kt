package com.ramajogi.lifefoodlife.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramajogi.lifefoodlife.presentation.ui.intent.RecipeIntent
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.DeliveryViewModel
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.RecipeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeViewModel: RecipeViewModel,
    deliveryViewModel: DeliveryViewModel,
    recipeId: Int?,
    navController: NavController
) {
    val recipeState = recipeViewModel.state.collectAsState().value
    val deliveryState = deliveryViewModel.state.collectAsState().value
    LaunchedEffect(recipeId) {
        recipeState.takeIf { it.selectedRecipe?.id == recipeId }
            ?: recipeViewModel.processIntent(RecipeIntent.LoadRecipes("Vegan")) // Fallback
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Recipe Details") }) }
    ) { padding ->
        AnimatedVisibility(visible = recipeState.selectedRecipe != null, enter = expandIn()) {
            recipeState.selectedRecipe?.let {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Text(it.title, style = MaterialTheme.typography.headlineMedium)
                    Text("Category: ${it.category}", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        "Instructions: ${it.instructions}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    recipeState.nutritionInfo?.let { info ->
                        Text(
                            "Total Calories: ${info.totalCalories}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            deliveryViewModel.placeOrder(it.id)
                            deliveryState.deliveryOrder?.orderId?.let { orderId ->
                                navController.navigate("delivery_tracking/$orderId")
                            }
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Order Now")
                    }
                }
            }
        }
    }
}
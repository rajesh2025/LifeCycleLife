package com.ramajogi.lifefoodlife.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.DeliveryViewModel
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.RecipeViewModel

@Composable
fun LFLMainScreen(recipeViewModel: RecipeViewModel, deliveryViewModel: DeliveryViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "recipe_list") {
        composable("recipe_list") {
            RecipeListScreen(viewModel = recipeViewModel, navController = navController)
        }
        composable("recipe_detail/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
            RecipeDetailScreen(recipeViewModel, deliveryViewModel, recipeId, navController)
        }
        composable("delivery_tracking/{orderId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId")
            DeliveryTrackingScreen(deliveryViewModel, orderId, navController)
        }
        composable("settings") {
            SettingsScreen(navController)
        }
    }
}
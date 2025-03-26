package com.ramajogi.lifefoodlife.presentation.ui.intent

import com.ramajogi.lifefoodlife.domain.model.Recipe
import com.ramajogi.lifefoodlife.domain.usecase.CalculateNutrition

// Intent represents user actions or events
sealed class RecipeIntent {
    data class LoadRecipes(val category: String) : RecipeIntent()
    data class SelectRecipe(val recipe: Recipe) : RecipeIntent()
    object ClearError : RecipeIntent()
}

// State represents the UI's current state
data class RecipeState(
    val recipes: List<Recipe> = emptyList(),
    val selectedRecipe: Recipe? = null,
    val nutritionInfo: CalculateNutrition.NutritionInfo? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

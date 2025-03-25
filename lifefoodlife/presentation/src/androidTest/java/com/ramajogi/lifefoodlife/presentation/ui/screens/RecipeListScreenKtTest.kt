package com.ramajogi.lifefoodlife.presentation.ui.screens

import org.junit.Assert.*


import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavController
import com.ramajogi.lifefoodlife.domain.model.Ingredient
import com.ramajogi.lifefoodlife.domain.model.Recipe
import com.ramajogi.lifefoodlife.domain.usecase.CalculateNutrition
import com.ramajogi.lifefoodlife.domain.usecase.GetRecipesByCategory
import com.ramajogi.lifefoodlife.presentation.ui.screens.RecipeListScreen
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.RecipeViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

class RecipeListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val navController = mock<NavController>()
    private val getRecipesByCategory = mock<GetRecipesByCategory>()
    private val calculateNutrition = mock<CalculateNutrition>()

    @Test
    fun testRecipeListDisplaysRecipes() {
        val recipes = listOf(Recipe(1, "Vegan Soup", "Vegan", listOf(Ingredient("Tomato", 200.0, 18.0)), "Boil"))
        val viewModel = RecipeViewModel(getRecipesByCategory, calculateNutrition).apply {
            setRecipes(recipes)
        }

        composeTestRule.setContent {
            RecipeListScreen(viewModel, navController)
        }

        composeTestRule.onNodeWithText("Vegan Soup").assertExists()
    }
}
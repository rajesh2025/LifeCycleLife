package com.ramajogi.lifefoodlife.presentation.ui.viewmodel


import com.ramajogi.lifefoodlife.domain.model.Ingredient
import com.ramajogi.lifefoodlife.domain.model.Recipe
import com.ramajogi.lifefoodlife.domain.usecase.CalculateNutrition
import com.ramajogi.lifefoodlife.domain.usecase.GetRecipesByCategory
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.RecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class RecipeViewModelTest {
    private val getRecipesByCategory = mock<GetRecipesByCategory>()
    private val calculateNutrition = mock<CalculateNutrition>()
    private lateinit var viewModel: RecipeViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RecipeViewModel(getRecipesByCategory, calculateNutrition)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testLoadRecipes() = runTest {
        val recipes = listOf(Recipe(1, "Vegan Soup", "Vegan", listOf(Ingredient("Tomato", 200.0, 18.0)), "Boil"))
        `when`(getRecipesByCategory("Vegan")).thenReturn(recipes)

        viewModel.loadRecipes("Vegan")
        assertEquals(recipes, viewModel.recipes.value)
    }
}
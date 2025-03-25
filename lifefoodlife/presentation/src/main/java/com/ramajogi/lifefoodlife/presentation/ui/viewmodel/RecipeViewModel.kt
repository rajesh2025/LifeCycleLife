package com.ramajogi.lifefoodlife.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramajogi.lifefoodlife.domain.model.Recipe
import com.ramajogi.lifefoodlife.domain.usecase.CalculateNutrition
import com.ramajogi.lifefoodlife.domain.usecase.GetRecipesByCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getRecipesByCategory: GetRecipesByCategory,
    private val calculateNutrition: CalculateNutrition
) : ViewModel() {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> = _selectedRecipe

    private val _nutritionInfo = MutableStateFlow<CalculateNutrition.NutritionInfo?>(null)
    val nutritionInfo: StateFlow<CalculateNutrition.NutritionInfo?> = _nutritionInfo

    fun loadRecipes(category: String) {
        viewModelScope.launch {
            _recipes.value = getRecipesByCategory(category)
        }
    }

    fun selectRecipe(recipe: Recipe) {
        _selectedRecipe.value = recipe
        _nutritionInfo.value = calculateNutrition(recipe)
    }

    fun setRecipes(recipes: List<Recipe>) {
        _recipes.value = recipes
    }
}
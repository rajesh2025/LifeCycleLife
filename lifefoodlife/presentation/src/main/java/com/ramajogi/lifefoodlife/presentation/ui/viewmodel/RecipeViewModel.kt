package com.ramajogi.lifefoodlife.presentation.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramajogi.lifefoodlife.domain.model.LFLResult
import com.ramajogi.lifefoodlife.domain.model.Recipe
import com.ramajogi.lifefoodlife.domain.usecase.CalculateNutrition
import com.ramajogi.lifefoodlife.domain.usecase.GetRecipesByCategory
import com.ramajogi.lifefoodlife.presentation.ui.intent.RecipeIntent
import com.ramajogi.lifefoodlife.presentation.ui.intent.RecipeState
import com.ramajogi.lifefoodlife.presentation.utils.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getRecipesByCategory: GetRecipesByCategory,
    private val calculateNutrition: CalculateNutrition
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _state.value = _state.value.copy(
            isLoading = false,
            errorMessage = "Error: ${throwable.message}"
        )
    }

    private val _state = MutableStateFlow(RecipeState())
    val state: StateFlow<RecipeState> = _state.asStateFlow()

    fun processIntent(intent: RecipeIntent) {
        when (intent) {
            is RecipeIntent.LoadRecipes -> loadRecipes(intent.category)
            is RecipeIntent.SelectRecipe -> selectRecipe(intent.recipe)
            RecipeIntent.ClearError -> clearError()
        }
    }

    fun loadRecipes(category: String) {
        viewModelScope.launch(exceptionHandler) {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            when (val result = getRecipesByCategory(category)) {
                is LFLResult.Success ->{
                    _state.value = _state.value.copy(
                        recipes = result.data,
                        isLoading = false
                    )
            }
                is LFLResult.Failure ->{
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = Utility.filterRecipeErrorMsg(result.error)
                    )
                }
            }
        }
    }


    fun selectRecipe(recipe: Recipe) {
        viewModelScope.launch(exceptionHandler) {
            when (val result = calculateNutrition(recipe)) {
                is LFLResult.Success -> {
                    _state.value = _state.value.copy(
                        selectedRecipe = recipe,
                        nutritionInfo = result.data
                    )
                }
                is LFLResult.Failure -> {
                    _state.value = _state.value.copy(
                        selectedRecipe = recipe,
                        errorMessage = Utility.filterRecipeErrorMsg(result.error)
                    )
                }
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(errorMessage = null)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setRecipes(recipes: List<Recipe>) {
        _state.value = _state.value.copy(
            recipes = recipes, isLoading = false
        )
    }
}
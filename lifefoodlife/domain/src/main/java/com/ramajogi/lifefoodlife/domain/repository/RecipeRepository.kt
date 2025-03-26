package com.ramajogi.lifefoodlife.domain.repository

import com.ramajogi.lifefoodlife.domain.model.Recipe
import com.ramajogi.lifefoodlife.domain.model.LFLResult

interface RecipeRepository {
    suspend fun getRecipeByCategory(category : String): LFLResult<List<Recipe>>
    suspend fun getRecipeById(id : Int): LFLResult<Recipe?>
}
package com.ramajogi.lifefoodlife.domain.repository

import com.ramajogi.lifefoodlife.domain.model.Recipe

interface RecipeRepository {
    suspend fun getRecipeByCategory(category : String): List<Recipe>
    suspend fun getRecipeById(id : Int): Recipe?
}
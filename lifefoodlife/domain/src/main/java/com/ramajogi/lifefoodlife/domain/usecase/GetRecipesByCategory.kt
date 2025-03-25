package com.ramajogi.lifefoodlife.domain.usecase

import com.ramajogi.lifefoodlife.domain.model.Recipe
import com.ramajogi.lifefoodlife.domain.repository.RecipeRepository


class GetRecipesByCategory(private val repository: RecipeRepository) {
    suspend operator fun invoke(category: String): List<Recipe>{
        return repository.getRecipeByCategory(category)
    }
}
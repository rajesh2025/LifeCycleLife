package com.ramajogi.lifefoodlife.domain.usecase

import com.ramajogi.lifefoodlife.domain.model.LFLResult
import com.ramajogi.lifefoodlife.domain.model.Recipe
import com.ramajogi.lifefoodlife.domain.repository.RecipeRepository

val validCategories = arrayOf("Vegan", "Low-Calorie", "Non-Vegan")

class GetRecipesByCategory(private val repository: RecipeRepository) {
    suspend operator fun invoke(category: String): LFLResult<List<Recipe>>{
        // Business rule validation
        if (category.isBlank()) {
            return LFLResult.Failure(LFLResult.Failure.ErrorType.InvalidInput("category", "Category cannot be empty"))
        }
        if (category !in validCategories) {
            return LFLResult.Failure(LFLResult.Failure.ErrorType.InvalidInput("category", "Invalid category: $category"))
        }
        return repository.getRecipeByCategory(category)
    }
}



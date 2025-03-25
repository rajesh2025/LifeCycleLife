package com.ramajogi.lifefoodlife.domain.usecase

import com.ramajogi.lifefoodlife.domain.model.Recipe


class CalculateNutrition {

    operator fun invoke(recipe: Recipe): NutritionInfo {
        val totalCalories = recipe.ingredients.sumOf { it ->
            (it.quantity / 100.0) * it.caloriesPer100g
        }
        return NutritionInfo(totalCalories)
    }

    data class NutritionInfo(val totalCalories: Double)

}
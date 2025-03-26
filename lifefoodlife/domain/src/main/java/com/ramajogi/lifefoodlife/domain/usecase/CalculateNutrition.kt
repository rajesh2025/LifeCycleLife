package com.ramajogi.lifefoodlife.domain.usecase

import com.ramajogi.lifefoodlife.domain.model.LFLResult
import com.ramajogi.lifefoodlife.domain.model.Recipe


class CalculateNutrition {

    operator fun invoke(recipe: Recipe): LFLResult<NutritionInfo> {
        val totalCalories = recipe.ingredients.sumOf { it ->
            (it.quantity / 100.0) * it.caloriesPer100g
        }
        return LFLResult.Success(NutritionInfo(totalCalories))
    }

    data class NutritionInfo(val totalCalories: Double)

}
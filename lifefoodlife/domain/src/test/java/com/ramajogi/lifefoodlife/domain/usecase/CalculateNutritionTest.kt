package com.ramajogi.lifefoodlife.domain.usecase

import com.ramajogi.lifefoodlife.domain.model.Ingredient
import com.ramajogi.lifefoodlife.domain.model.Recipe
import junit.framework.TestCase.assertEquals
import kotlin.test.Test


class CalculateNutritionTest {
private val useCase = CalculateNutrition()

    @Test
    fun testCalculateNutrition(){
        val recipe = Recipe(
            id = 1,
            title = "Test Recipe",
            category = "Test",
            ingredients = listOf(
                Ingredient("Tomato", 200.0, 18.0), // 36 calories
                Ingredient("Olive Oil", 10.0, 884.0) // 88.4 calories
            ),
            instructions = "Cook."
        )
        val result = useCase(recipe)
        assertEquals(36.0 + 88.4, result.totalCalories, 0.01)
    }
}
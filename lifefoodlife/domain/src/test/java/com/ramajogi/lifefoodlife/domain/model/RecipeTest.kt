package com.ramajogi.lifefoodlife.domain.model

import org.junit.Test
import org.junit.Assert.assertEquals

class RecipeTest {

    @Test
    fun testRecipeCreation(){
        val ingredients = listOf<Ingredient>(
            Ingredient("Tomato", 200.0, 18.0),
            Ingredient("Olive Oil", 10.0, 884.0)
        )

        val recipe = Recipe(
            id = 1,
            title = "Tomato Soup",
            category = "Vegan",
            ingredients = ingredients,
            instructions = "Boil tomatoes, add oil."
        )

        assertEquals(1, recipe.id)
        assertEquals("Tomato Soup", recipe.title)
        assertEquals("Vegan", recipe.category)
        assertEquals(2, recipe.ingredients.size)
        assertEquals("Boil tomatoes, add oil.", recipe.instructions)
    }




}
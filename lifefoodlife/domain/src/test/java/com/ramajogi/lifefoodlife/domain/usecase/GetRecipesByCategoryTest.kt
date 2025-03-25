package com.ramajogi.lifefoodlife.domain.usecase

import com.ramajogi.lifefoodlife.domain.model.Ingredient
import com.ramajogi.lifefoodlife.domain.model.Recipe
import com.ramajogi.lifefoodlife.domain.repository.RecipeRepository
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import kotlin.test.Test
import kotlin.test.assertEquals

class GetRecipesByCategoryTest {
    private val repository = mock<RecipeRepository>()
    private val useCase = GetRecipesByCategory(repository)

    @Test
    fun testGetRecipesByCategory() = runBlocking {
        val recipe = listOf(
            Recipe(1, "Vegan Soup", "Vegan", listOf(Ingredient("Tomato", 200.0, 18.0)), "Boil.")
        )
        `when`(repository.getRecipeByCategory("Vegan")).thenReturn(recipe)
        val result = useCase("Vegan")
        assertEquals(1, result.size)
        assertEquals("Vegan Soup", result[0].title)
    }



}
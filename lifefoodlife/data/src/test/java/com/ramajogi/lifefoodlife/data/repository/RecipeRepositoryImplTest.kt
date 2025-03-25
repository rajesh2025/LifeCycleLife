package com.ramajogi.lifefoodlife.data.repository

import com.ramajogi.lifefoodlife.data.db.RecipeDao
import com.ramajogi.lifefoodlife.data.db.RecipeEntity
import com.ramajogi.lifefoodlife.domain.model.Ingredient
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import kotlin.test.Test

class RecipeRepositoryImplTest {
    private val dao = mock<RecipeDao>()
    private val repository = RecipeRepositoryImpl(dao)

    @Test
    fun testGetRecipesByCategory() = runBlocking {
        val recipes = listOf(
            RecipeEntity(
                1,
                "Vegan Soup",
                "Vegan",
                listOf(Ingredient("Tomato", 200.0, 18.0)),
                "Boil"
            )
        )
        `when`(dao.getRecipesByCategory("Vegan")).thenReturn(recipes)

        val result = repository.getRecipeByCategory("Vegan")
        assertEquals(1, result.size)
        assertEquals("Vegan Soup", result[0].title)
    }
}
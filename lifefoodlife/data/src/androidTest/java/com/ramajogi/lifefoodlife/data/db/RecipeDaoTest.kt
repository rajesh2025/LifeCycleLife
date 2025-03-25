package com.ramajogi.lifefoodlife.data.db

import androidx.room.Room
import com.ramajogi.lifefoodlife.domain.model.Ingredient
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import androidx.test.core.app.ApplicationProvider

class RecipeDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: RecipeDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        dao = db.recipeDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testGetRecipesByCategory() = runBlocking {
        val recipes = listOf(
            RecipeEntity(1, "Vegan Soup", "Vegan", listOf(Ingredient("Tomato", 200.0, 18.0)), "Boil"),
            RecipeEntity(2, "Chicken Salad", "Non-Vegan", listOf(Ingredient("Chicken", 150.0, 165.0)), "Mix")
        )
        dao.insertAll(recipes)

        val veganRecipes = dao.getRecipesByCategory("Vegan")
        assertEquals(1, veganRecipes.size)
        assertEquals("Vegan Soup", veganRecipes[0].title)
    }
}
package com.ramajogi.lifefoodlife.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipeDao {
    @Insert
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Query("SELECT * FROM recipes WHERE category = :category")
    suspend fun getRecipesByCategory(category: String): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Int): RecipeEntity?
}
package com.ramajogi.lifefoodlife.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ramajogi.lifefoodlife.domain.model.Ingredient
import com.ramajogi.lifefoodlife.domain.model.Recipe

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val category: String,
    val ingredients: List<Ingredient>, // Room can serialize lists with TypeConverters
    val instructions: String
) {
    fun toDomainModel(): Recipe = Recipe(id, title, category, ingredients, instructions)
}

fun Recipe.toEntity(): RecipeEntity = RecipeEntity(id, title, category, ingredients, instructions)
package com.ramajogi.lifefoodlife.domain.model

data class Recipe(
    val id: Int,
    val title: String,
    val category: String,
    val ingredients: List<Ingredient>,
    val instructions: String
)

data class Ingredient(
    val name: String,
    val quantity: Double, // in grams
    val caloriesPer100g: Double // calories per 100 grams
)
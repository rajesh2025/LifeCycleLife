package com.ramajogi.lifefoodlife.data.repository

import com.ramajogi.lifefoodlife.data.db.RecipeDao
import com.ramajogi.lifefoodlife.data.db.toEntity
import com.ramajogi.lifefoodlife.domain.model.Recipe
import com.ramajogi.lifefoodlife.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val dao: RecipeDao
) : RecipeRepository {

    override suspend fun getRecipeByCategory(category: String): List<Recipe> {
        return dao.getRecipesByCategory(category).map { it.toDomainModel() }
    }

    override suspend fun getRecipeById(id: Int): Recipe? {
        return dao.getRecipeById(id)?.toDomainModel()
    }

    suspend fun insertInitialRecipes(recipes: List<Recipe>) {
        dao.insertAll(recipes.map { it.toEntity() })
    }
}
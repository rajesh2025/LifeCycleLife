package com.ramajogi.lifefoodlife.data.repository


import com.ramajogi.lifefoodlife.data.db.RecipeDao
import com.ramajogi.lifefoodlife.data.db.toEntity
import com.ramajogi.lifefoodlife.data.network.ApiService
import com.ramajogi.lifefoodlife.domain.model.Recipe
import com.ramajogi.lifefoodlife.domain.repository.RecipeRepository
import com.ramajogi.lifefoodlife.domain.model.LFLResult
import com.ramajogi.lifefoodlife.domain.model.LFLResult.Failure.Companion.fromException

import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val dao: RecipeDao,
    private val api: ApiService
) : RecipeRepository {

    override suspend fun getRecipeByCategory(category: String): LFLResult<List<Recipe>> {
        return try {
            val networkRecipes = api.getRecipesByCategory(category)
            dao.insertAll(networkRecipes.map { it.toEntity() })
            LFLResult.Success(networkRecipes)
        } catch (e: Exception) {
            LFLResult.Failure(fromException(e), LFLResult.Failure.ErrorType.Unknown(e)).also {
                val localRecipes = dao.getRecipesByCategory(category).map { it.toDomainModel() }
                if (localRecipes.isNotEmpty()) return LFLResult.Success(localRecipes) // Fallback to local
            }
        }
    }

    override suspend fun getRecipeById(id: Int): LFLResult<Recipe?> {
        return try {
            val networkRecipe = api.getRecipeById(id)
            networkRecipe?.let { dao.insertAll(listOf(it.toEntity())) }
            LFLResult.Success(networkRecipe)
        } catch (e: Exception) {
            LFLResult.Failure(fromException(e), LFLResult.Failure.ErrorType.Unknown(e)).also {//TODO according network response type
                val localRecipe = dao.getRecipeById(id)?.toDomainModel()
                if (localRecipe != null) return LFLResult.Success(localRecipe) // Fallback to local
            }
        }
    }


    suspend fun insertInitialRecipes(recipes: List<Recipe>) {
        dao.insertAll(recipes.map { it.toEntity() })
    }
}
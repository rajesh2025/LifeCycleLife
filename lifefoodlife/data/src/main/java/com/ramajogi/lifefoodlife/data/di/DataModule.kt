package com.ramajogi.lifefoodlife.data.di

import android.content.Context
import com.ramajogi.lifefoodlife.data.db.AppDatabase
import com.ramajogi.lifefoodlife.data.db.DeliveryDao
import com.ramajogi.lifefoodlife.data.db.RecipeDao
import com.ramajogi.lifefoodlife.data.network.ApiService
import com.ramajogi.lifefoodlife.data.network.AuthApiService
import com.ramajogi.lifefoodlife.data.network.AuthInterceptor
import com.ramajogi.lifefoodlife.data.network.TokenManager
import com.ramajogi.lifefoodlife.data.repository.DeliveryRepositoryImpl
import com.ramajogi.lifefoodlife.data.repository.RecipeRepositoryImpl
import com.ramajogi.lifefoodlife.domain.repository.DeliveryRepository
import com.ramajogi.lifefoodlife.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideRecipeDao(database: AppDatabase): RecipeDao = database.recipeDao()

    @Provides
    @Singleton
    fun provideDeliveryDao(database: AppDatabase): DeliveryDao = database.deliveryDao()


    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        tokenManager: TokenManager,
        authApiService: AuthApiService,
        ioDispatcher: CoroutineDispatcher
    ): AuthInterceptor {
        return AuthInterceptor(tokenManager, authApiService, ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(dao: RecipeDao): RecipeRepository {
        return RecipeRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideDeliveryRepository(dao: DeliveryDao, api: ApiService): DeliveryRepository {
        return DeliveryRepositoryImpl(dao, api)
    }
}
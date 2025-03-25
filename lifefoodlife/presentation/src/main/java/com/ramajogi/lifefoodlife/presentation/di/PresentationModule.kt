package com.ramajogi.lifefoodlife.presentation.di

import com.ramajogi.lifefoodlife.domain.repository.DeliveryRepository
import com.ramajogi.lifefoodlife.domain.repository.RecipeRepository
import com.ramajogi.lifefoodlife.domain.usecase.CalculateNutrition
import com.ramajogi.lifefoodlife.domain.usecase.GetRecipesByCategory
import com.ramajogi.lifefoodlife.domain.usecase.PlaceDeliveryOrder
import com.ramajogi.lifefoodlife.domain.usecase.TrackDelivery
import com.ramajogi.lifefoodlife.domain.usecase.UpdateDeliveryStatus
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.DeliveryViewModel
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.RecipeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PresentationModule {
    @Provides
    @ViewModelScoped
    fun provideRecipeViewModel(
        getRecipesByCategory: GetRecipesByCategory,
        calculateNutrition: CalculateNutrition
    ): RecipeViewModel {
        return RecipeViewModel(getRecipesByCategory, calculateNutrition)
    }

    @Provides
    @ViewModelScoped
    fun provideDeliveryViewModel(
        placeDeliveryOrder: PlaceDeliveryOrder,
        trackDelivery: TrackDelivery,
        updateDeliveryStatus: UpdateDeliveryStatus
    ): DeliveryViewModel {
        return DeliveryViewModel(placeDeliveryOrder, trackDelivery, updateDeliveryStatus)
    }

    @Provides
    @ViewModelScoped
    fun provideGetRecipesByCategory(repository: RecipeRepository): GetRecipesByCategory {
        return GetRecipesByCategory(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideCalculateNutrition(): CalculateNutrition {
        return CalculateNutrition()
    }

    @Provides
    @ViewModelScoped
    fun providePlaceDeliveryOrder(repository: DeliveryRepository): PlaceDeliveryOrder {
        return PlaceDeliveryOrder(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideTrackDelivery(repository: DeliveryRepository): TrackDelivery {
        return TrackDelivery(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateDeliveryStatus(repository: DeliveryRepository): UpdateDeliveryStatus {
        return UpdateDeliveryStatus(repository)
    }
}
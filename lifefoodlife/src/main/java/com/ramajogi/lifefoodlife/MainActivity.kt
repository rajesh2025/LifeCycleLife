package com.ramajogi.lifefoodlife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.ramajogi.lifefoodlife.presentation.ui.screens.LFLMainScreen
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.DeliveryViewModel
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val recipeViewModel: RecipeViewModel by viewModels()
    private val deliveryViewModel: DeliveryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            LifeCycleLifeTheme {
                    LFLMainScreen(recipeViewModel, deliveryViewModel)
//                }
            }
        }

}



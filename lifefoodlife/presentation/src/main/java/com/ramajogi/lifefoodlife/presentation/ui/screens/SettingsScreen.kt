package com.ramajogi.lifefoodlife.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var theme by remember { mutableStateOf("Light") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Theme", style = MaterialTheme.typography.headlineSmall)
            CategoryDropdown(
                categories = listOf("Light", "Dark", "System"),
                selectedCategory = theme,
                onCategorySelected = { theme = it }
            )
        }
    }
}
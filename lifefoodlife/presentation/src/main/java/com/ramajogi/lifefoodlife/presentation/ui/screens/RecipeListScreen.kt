package com.ramajogi.lifefoodlife.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramajogi.lifefoodlife.domain.model.Recipe
import com.ramajogi.lifefoodlife.presentation.ui.intent.RecipeIntent
import com.ramajogi.lifefoodlife.presentation.ui.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(viewModel: RecipeViewModel, navController: NavController) {
    val state = viewModel.state.collectAsState().value

    var selectedCategory by remember { mutableStateOf("Vegan") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Healthy Recipes") },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            CategoryDropdown(
                categories = listOf("Vegan", "Low-Calorie", "Non-Vegan"),
                selectedCategory = selectedCategory,
                onCategorySelected = {
                    selectedCategory = it
                    viewModel.loadRecipes(it)
                }
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }
//            (state.recipes.isEmpty() && state.errorMessage == null) -> {
//                Text("No recipes found")
//            }

            state.errorMessage != null -> {
                Text(text = state.errorMessage, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.processIntent(RecipeIntent.ClearError) }) {
                    Text("Dismiss")
                }
            }

            state.recipes.isNotEmpty() -> {
                LazyColumn {
                    items(state.recipes.size) { index ->
                        AnimatedVisibility(
                            visible = true,
                            enter = slideInVertically(),
                            exit = slideOutVertically()
                        ) {
                            RecipeItem(state.recipes[index]) {
                                viewModel.processIntent(RecipeIntent.SelectRecipe(state.recipes[index]))
                                navController.navigate("recipe_detail/${state.recipes[index].id}")
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(recipe.title, style = MaterialTheme.typography.headlineSmall)
            Text("Category: ${recipe.category}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun CategoryDropdown(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = selectedCategory,
            onValueChange = {},
            label = { Text("Category") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}
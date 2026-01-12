package com.example.yummynutrition.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.yummynutrition.ui.theme.screens.WelcomeScreen
import com.example.yummynutrition.ui.theme.screens.HomeScreen
import com.example.yummynutrition.ui.theme.screens.NutritionScreen
import com.example.yummynutrition.ui.theme.screens.RecipeDetailScreen
import com.example.yummynutrition.ui.theme.screens.RecipesScreen
import com.example.yummynutrition.viewmodel.MainViewModel

sealed class Screen(
    val route: String,
    val label: String,
    val icon: ImageVector?
) {
    object Welcome : Screen("welcome", "Welcome", null)

    object Home : Screen("home", "Home", Icons.Default.Home)
    object Recipes : Screen("recipes", "Recipes", Icons.Default.Search)
    object Nutrition : Screen("nutrition", "Nutrition", Icons.Default.Star)


    object RecipeDetail : Screen("recipe_detail/{id}", "Detail", null) {
        fun createRoute(id: String) = "recipe_detail/$id"
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {

    // ✅ Un solo ViewModel compartido por todas las pantallas
    val mainViewModel: MainViewModel = viewModel()

    val items = listOf(
        Screen.Home,
        Screen.Recipes,
        Screen.Nutrition
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // ✅ NO mostrar BottomBar en detalle
    val showBottomBar = items.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    items.forEach { screen ->
                        NavigationBarItem(
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(Screen.Home.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                screen.icon?.let { Icon(it, contentDescription = screen.label) }
                            },
                            label = { Text(screen.label) }
                        )
                    }
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.Welcome.route) {
                WelcomeScreen(navController = navController)
            }

            composable(Screen.Home.route) {
                HomeScreen(navController = navController, viewModel = mainViewModel)
            }

            composable(Screen.Recipes.route) {
                RecipesScreen(navController = navController, viewModel = mainViewModel)
            }

            composable(Screen.Nutrition.route) {
                NutritionScreen(viewModel = mainViewModel)
            }

            composable(Screen.RecipeDetail.route) { backStack ->
                val id = backStack.arguments?.getString("id") ?: ""
                RecipeDetailScreen(
                    id = id,
                    navController = navController,
                    viewModel = mainViewModel
                )
            }
        }

    }
}


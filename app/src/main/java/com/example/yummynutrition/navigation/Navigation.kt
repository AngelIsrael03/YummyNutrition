package com.example.yummynutrition.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.yummynutrition.data.prefs.UserPrefs
import com.example.yummynutrition.ui.theme.screens.*
import com.example.yummynutrition.viewmodel.MainViewModel
import com.example.yummynutrition.ui.theme.Meals
import com.example.yummynutrition.ui.theme.screens.HomeScreen
import com.example.yummynutrition.ui.theme.screens.RecipesScreen
import com.example.yummynutrition.ui.theme.screens.NutritionScreen
import com.example.yummynutrition.ui.theme.screens.RecipeDetailScreen
import com.example.yummynutrition.ui.theme.screens.SplashScreen
import com.example.yummynutrition.ui.theme.screens.WelcomeScreen

// 🔹 Definición de rutas
sealed class Screen(
    val route: String,
    val label: String,
    val icon: ImageVector?
) {
    object Splash : Screen("splash", "", null)
    object Name : Screen("name", "", null)
    object Welcome : Screen("welcome", "", null)
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Recipes : Screen("recipes", "Recipes", Icons.Default.Search)
    object Nutrition : Screen("nutrition", "Nutrition", Icons.Default.Star)
    object Cart : Screen("cart", "Cart", null)
    object Meals : Screen("meals", "Meals", null)

    object RecipeDetail : Screen("recipe_detail/{id}", "Detail", null) {
        fun createRoute(id: String) = "recipe_detail/$id"
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {

    // ✅ ViewModel compartido
    val mainViewModel: MainViewModel = viewModel()

    // 🔹 Verificar si hay nombre guardado
    val context = LocalContext.current
    val savedName by UserPrefs.nameFlow(context).collectAsState(initial = "")

    // 🔹 Pantallas con BottomBar
    val bottomBarScreens = listOf(
        Screen.Home,
        Screen.Recipes,
        Screen.Nutrition
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomBar = bottomBarScreens.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomBarScreens.forEach { screen ->
                        NavigationBarItem(
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                screen.icon?.let {
                                    Icon(it, contentDescription = screen.label)
                                }
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
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(padding)
        ) {

            // 🔹 Splash (Logo)
            composable(Screen.Splash.route) {
                SplashScreen {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            }

            // 🔹 Welcome (Bienvenida)
            composable(Screen.Welcome.route) {
                WelcomeScreen {
                    // Si hay nombre, va a Home. Si no, va a NameScreen
                    val nextRoute = if (savedName.isBlank()) {
                        Screen.Name.route
                    } else {
                        Screen.Home.route
                    }
                    navController.navigate(nextRoute) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            }

            // 🔹 NameScreen (Pedir nombre)
            composable(Screen.Name.route) {
                NameScreen {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Name.route) { inclusive = true }
                    }
                }
            }

            // 🔹 Home
            composable(Screen.Home.route) {
                HomeScreen(
                    navController = navController,
                    viewModel = mainViewModel
                )
            }

            // 🔹 Recipes
            composable(Screen.Recipes.route) {
                RecipesScreen(
                    navController = navController,
                    viewModel = mainViewModel
                )
            }

            // 🔹 Nutrition
            composable(Screen.Nutrition.route) {
                NutritionScreen(viewModel = mainViewModel)
            }

            // 🔹 Cart
            composable(Screen.Cart.route) {
                CartScreen(
                    navController = navController,
                    viewModel = mainViewModel
                )
            }

            // 🔹 Meals (configuración de comidas)
            composable(Screen.Meals.route) {
                Meals()
            }

            // 🔹 Detalle receta
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
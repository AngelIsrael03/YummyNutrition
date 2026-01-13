package com.example.yummynutrition.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.ui.draw.shadow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yummynutrition.data.model.FoodItem
import com.example.yummynutrition.data.prefs.UserPrefs
import com.example.yummynutrition.navigation.Screen
import com.example.yummynutrition.ui.theme.md_theme_light_background
import com.example.yummynutrition.ui.theme.md_theme_light_primary
import com.example.yummynutrition.ui.theme.md_theme_light_secondary
import com.example.yummynutrition.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    // --- Nombre guardado ---
    val context = LocalContext.current

    val savedName by UserPrefs.nameFlow(context).collectAsState(initial = "")

    // --- Datos nutricionales ---
    val food: FoodItem? by viewModel.nutrition.collectAsState()

    val foodName = food?.description?.uppercase() ?: "NO FOOD SELECTED"
    val totalCalories = food.nutrientValue("Energy").toInt()
    val protein = food.nutrientValue("Protein").toInt()
    val carbs = food.nutrientValue("Carbohydrate").toInt()
    val fats = food.nutrientValue("Total lipid", "Fat").toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(md_theme_light_background)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        // 🔥 HEADER
        Text(
            text = if (savedName.isNotBlank()) "Hello, $savedName 👋" else "Hello 👋",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF1C1C1C)
        )

        Text(
            text = "Your nutritional summary today",
            color = Color(0xFF616161)
        )

        // 🔥 TARJETA PRINCIPAL
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(28.dp))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("TOTAL CALORIES", color = Color(0xFF9E9E9E))
                Text(
                    "$totalCalories kcal",
                    style = MaterialTheme.typography.headlineLarge,
                    color = md_theme_light_primary
                )
                Text(
                    foodName,
                    color = Color(0xFF757575),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        // 🔥 MACROS
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(24.dp))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    "Macronutrients",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF1C1C1C)
                )

                MacroBar("Protein", protein, md_theme_light_primary)
                MacroBar("Carbohydrates", carbs, md_theme_light_secondary)
                MacroBar("Fats", fats, Color(0xFFFF9800))
            }
        }

        // 🔥 CONFIGURAR COMIDAS
        Card(
            onClick = { navController.navigate(Screen.Meals.route) },
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = md_theme_light_primary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(18.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    Icons.Default.Restaurant,
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    "Configure my meals",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        // 🔥 RECETAS
        Button(
            onClick = { navController.navigate(Screen.Recipes.route) },
            colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_secondary),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Explore healthy recipes", color = Color.White)
        }

        Button(
            onClick = { navController.navigate(Screen.Nutrition.route) },
            colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_secondary),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Search nutrition info", color = Color.White)
        }
    }
}

// 🔹 MacroBar mejorada
@Composable
fun MacroBar(label: String, value: Int, barColor: Color) {
    val progress = (value / 200f).coerceIn(0f, 1f)

    Column {
        Text(label, color = Color(0xFF424242))
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .height(14.dp)
                .fillMaxWidth()
                .background(Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
        ) {
            Box(
                modifier = Modifier
                    .height(14.dp)
                    .fillMaxWidth(progress)
                    .background(barColor, RoundedCornerShape(12.dp))
            )
        }
    }
}

// 🔹 Helper
private fun FoodItem?.nutrientValue(vararg keys: String): Double {
    val item = this ?: return 0.0
    return item.foodNutrients
        .firstOrNull { n -> keys.any { key -> n.nutrientName.contains(key, true) } }
        ?.value ?: 0.0
}
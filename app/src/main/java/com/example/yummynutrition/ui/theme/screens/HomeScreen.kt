package com.example.yummynutrition.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import com.example.yummynutrition.ui.theme.md_theme_dark_background
import com.example.yummynutrition.ui.theme.md_theme_dark_primary
import com.example.yummynutrition.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    // --- Nombre guardado (DataStore) ---
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val savedName by UserPrefs.nameFlow(context).collectAsState(initial = "")
    var showNameDialog by remember { mutableStateOf(false) }
    var nameInput by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (savedName.isBlank()) {
            showNameDialog = true
        }
    }


    // --- Tu lógica actual (NO la tocamos) ---
    val food: FoodItem? by viewModel.nutrition.collectAsState()

    val foodName = food?.description?.uppercase() ?: "No food selected"

    val totalCalories = food.nutrientValue("Energy").toInt()
    val protein = food.nutrientValue("Protein").toInt()
    val carbs = food.nutrientValue("Carbohydrate").toInt()
    val fats = food.nutrientValue("Total lipid", "Fat").toInt()

    // --- UI ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(md_theme_dark_background)
            .padding(20.dp)
    ) {

        // ✅ Saludo
        Text(
            text = if (savedName.isNotBlank()) "Hola, $savedName 👋" else "Hola 👋",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Today’s Nutrition Summary",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = foodName,
            style = MaterialTheme.typography.titleMedium,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Total Calories", color = Color(0xFFCCCCCC))
                Text(
                    "$totalCalories kcal",
                    color = md_theme_dark_primary,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Macronutrient Breakdown",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        MacroBar("Protein", protein)
        MacroBar("Carbs", carbs)
        MacroBar("Fats", fats)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate(Screen.Recipes.route) },
            colors = ButtonDefaults.buttonColors(containerColor = md_theme_dark_primary),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Explore Healthy Recipes", color = Color.Black)
        }
    }

    // ✅ Diálogo (solo si no hay nombre guardado)
    if (showNameDialog) {
        val clean = nameInput.trim()

        AlertDialog(
            onDismissRequest = { /* no cerrar tocando fuera */ },
            title = { Text("¿Cómo te llamas?") },
            text = {
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    singleLine = true,
                    label = { Text("Nombre") }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showNameDialog = false   // 👈 cerrar primero

                        scope.launch {
                            UserPrefs.setName(context, clean)
                        }
                    },
                    enabled = clean.isNotBlank()
                ) {
                    Text("Guardar")
                }

            }
        )
    }
}

// Home screen that displays the daily nutrition summary
@Composable
fun MacroBar(label: String, value: Int) {
    val progress = (value / 200f).coerceIn(0f, 1f)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, color = Color(0xFFEFEFEF))
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .height(14.dp)
                .fillMaxWidth()
                .background(Color(0xFF2A2A2A), RoundedCornerShape(12.dp))
        ) {
            Box(
                modifier = Modifier
                    .height(14.dp)
                    .fillMaxWidth(progress)
                    .background(md_theme_dark_primary, RoundedCornerShape(12.dp))
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

private fun FoodItem?.nutrientValue(vararg keys: String): Double {
    val item = this ?: return 0.0
    return item.foodNutrients
        .firstOrNull { n -> keys.any { key -> n.nutrientName.contains(key, ignoreCase = true) } }
        ?.value ?: 0.0
}

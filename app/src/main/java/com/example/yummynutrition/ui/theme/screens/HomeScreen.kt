package com.example.yummynutrition.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yummynutrition.navigation.Screen
import com.example.yummynutrition.ui.theme.md_theme_dark_background
import com.example.yummynutrition.ui.theme.md_theme_dark_primary
import com.example.yummynutrition.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val nutrition by viewModel.nutrition.collectAsState()

    val totalCalories = nutrition.sumOf { it.calories }.toInt()
    val protein = nutrition.sumOf { it.proteinG }.toInt()
    val carbs = nutrition.sumOf { it.carbsG }.toInt()
    val fats = nutrition.sumOf { it.fatTotalG }.toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(md_theme_dark_background)
            .padding(20.dp)
    ) {

        Text(
            text = "Today’s Nutrition",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge
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
            "Macronutrients",
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
            Text("Search Recipes", color = Color.Black)
        }
    }
}

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

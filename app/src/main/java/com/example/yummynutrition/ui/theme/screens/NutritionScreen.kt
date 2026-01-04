package com.example.yummynutrition.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yummynutrition.ui.theme.md_theme_dark_background
import com.example.yummynutrition.ui.theme.md_theme_dark_primary
import com.example.yummynutrition.viewmodel.MainViewModel

@Composable
fun NutritionScreen(
    viewModel: MainViewModel = viewModel()
) {
    var query by remember { mutableStateOf("") }
    val food by viewModel.nutrition.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(md_theme_dark_background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search food (banana, rice, apple)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = md_theme_dark_primary,
                focusedLabelColor = md_theme_dark_primary,
                cursorColor = md_theme_dark_primary
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (query.isNotBlank()) {
                    viewModel.getNutrition(query)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = md_theme_dark_primary)
        ) {
            Text("Get Nutrition", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(24.dp))

        food?.let { item ->
            Text(
                text = item.description,
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            item.foodNutrients.forEach { nutrient ->
                nutrient.value?.let { value ->
                    Text(
                        text = "${nutrient.nutrientName}: $value ${nutrient.unitName}",
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

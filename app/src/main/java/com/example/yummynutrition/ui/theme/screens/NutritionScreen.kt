package com.example.yummynutrition.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yummynutrition.ui.theme.md_theme_light_background
import com.example.yummynutrition.ui.theme.md_theme_light_primary
import com.example.yummynutrition.ui.theme.md_theme_light_secondary
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
            .background(md_theme_light_background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Buscar Nutrición",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF1C1C1C),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search food (banana, rice, apple)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = md_theme_light_primary,
                focusedLabelColor = md_theme_light_primary,
                cursorColor = md_theme_light_primary,
                unfocusedBorderColor = Color(0xFFBDBDBD)
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
            colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_primary)
        ) {
            Text("Get Nutrition", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        food?.let { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = item.description,
                        color = Color(0xFF1C1C1C),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)

                    Spacer(modifier = Modifier.height(8.dp))

                    item.foodNutrients.forEach { nutrient ->
                        nutrient.value?.let { value ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = nutrient.nutrientName,
                                    color = Color(0xFF616161),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "$value ${nutrient.unitName}",
                                    color = md_theme_light_primary,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
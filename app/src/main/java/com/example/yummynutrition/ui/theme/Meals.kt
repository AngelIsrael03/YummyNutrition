package com.example.yummynutrition.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MealsScreen() {
    var mealsCount by remember { mutableStateOf("") }
    var confirmed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text("¿Cuántas comidas te indicó tu nutriólogo?",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = mealsCount,
            onValueChange = { mealsCount = it },
            label = { Text("Número de comidas") }
        )

        Button(onClick = { confirmed = true }) {
            Text("Generar comidas")
        }

        if (confirmed) {
            val count = mealsCount.toIntOrNull() ?: 0
            repeat(count) { index ->
                MealCard(index + 1)
            }
        }
    }
}

@Composable
fun MealCard(number: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Comida $number", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Hora") })
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Comida") })

            Row {
                Text("Activar alarma")
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = false, onCheckedChange = {})
            }
        }
    }
}

package com.example.yummynutrition.ui.theme

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.yummynutrition.data.model.Meal
import java.util.*

@Composable
fun Meals() {

    var mealsCount by remember { mutableStateOf("") }
    var meals by remember { mutableStateOf(listOf<Meal>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            "Configura tus comidas",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = mealsCount,
            onValueChange = { mealsCount = it },
            label = { Text("¿Cuántas comidas te indicó tu nutriólogo?") },
            singleLine = true
        )

        Button(
            onClick = {
                val count = mealsCount.toIntOrNull() ?: 0
                meals = List(count) { index -> Meal(id = index + 1) }
            },
            enabled = mealsCount.isNotBlank()
        ) {
            Text("Generar comidas")
        }

        meals.forEach { meal ->
            MealCard(
                meal = meal,
                onUpdate = { updated ->
                    meals = meals.map {
                        if (it.id == updated.id) updated else it
                    }
                }
            )
        }
    }
}

@Composable
fun MealCard(
    meal: Meal,
    onUpdate: (Meal) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text("Comida ${meal.id}", style = MaterialTheme.typography.titleMedium)

            // ⏰ HORA
            OutlinedTextField(
                value = meal.hour,
                onValueChange = {},
                label = { Text("Hora") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                val time = String.format("%02d:%02d", hour, minute)
                                onUpdate(meal.copy(hour = time))
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                        ).show()
                    }) {
                        Icon(Icons.Default.Schedule, contentDescription = null)
                    }
                }
            )

            // 🍽️ COMIDA
            OutlinedTextField(
                value = meal.description,
                onValueChange = {
                    onUpdate(meal.copy(description = it))
                },
                label = { Text("¿Qué vas a comer?") },
                modifier = Modifier.fillMaxWidth()
            )

            // 🔔 ALARMA
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Activar alarma")
                Switch(
                    checked = meal.alarmEnabled,
                    onCheckedChange = {
                        onUpdate(meal.copy(alarmEnabled = it))
                    }
                )
            }
        }
    }
}

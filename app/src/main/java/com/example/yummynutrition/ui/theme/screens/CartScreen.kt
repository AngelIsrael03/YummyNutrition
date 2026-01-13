package com.example.yummynutrition.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yummynutrition.ui.theme.md_theme_light_background
import com.example.yummynutrition.ui.theme.md_theme_light_primary
import com.example.yummynutrition.ui.theme.md_theme_light_secondary
import com.example.yummynutrition.viewmodel.MainViewModel

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val cart by viewModel.cart.collectAsState()
    val totalCalories = viewModel.getCartTotalCalories()
    val totalProtein = viewModel.getCartTotalProtein()
    val totalCarbs = viewModel.getCartTotalCarbs()
    val totalFats = viewModel.getCartTotalFats()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(md_theme_light_background)
            .padding(16.dp)
    ) {

        // 🔙 HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = md_theme_light_primary
                )
            }
            Text(
                text = "My Food Intake",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF1C1C1C),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        if (cart.isEmpty()) {
            // CARRITO VACÍO
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🛒",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = "Your food intake is empty",
                        color = Color(0xFF616161),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(top = 24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_primary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Add Foods", color = Color.White)
                    }
                }
            }
            return
        }

        // LISTA DE ITEMS
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cart) { cartItem ->
                CartItemCard(
                    cartItem = cartItem,
                    onRemove = { viewModel.removeFromCart(cartItem.id) },
                    onQuantityChange = { newQuantity ->
                        viewModel.updateQuantity(cartItem.id, newQuantity)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // RESUMEN
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Summary",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF1C1C1C)
                )

                SummaryRow("Calories", "$totalCalories kcal")
                SummaryRow("Protein", "$totalProtein g", md_theme_light_primary)
                SummaryRow("Carbs", "$totalCarbs g", md_theme_light_secondary)
                SummaryRow("Fats", "$totalFats g", Color(0xFFFF9800))

                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Total Items",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF616161)
                    )
                    Text(
                        "${cart.size}",
                        style = MaterialTheme.typography.titleMedium,
                        color = md_theme_light_primary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // BOTONES DE ACCIÓN
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { viewModel.clearCart() },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEE)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Clear Intake", color = Color(0xFF1C1C1C))
            }

            Button(
                onClick = { /* TODO: Guardar comidas */ },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_primary),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Save Meal", color = Color.White)
            }
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: com.example.yummynutrition.viewmodel.CartItem,
    onRemove: () -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cartItem.foodItem.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF1C1C1C),
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove",
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Qty: ${cartItem.quantity}",
                    color = Color(0xFF616161),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${(cartItem.foodItem.nutrientValue("Energy").toInt() * cartItem.quantity)} kcal",
                    color = md_theme_light_primary,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { onQuantityChange(cartItem.quantity - 1) },
                    modifier = Modifier.size(36.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEE)),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("-", color = Color(0xFF1C1C1C), style = MaterialTheme.typography.bodySmall)
                }

                Text(
                    text = cartItem.quantity.toString(),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Button(
                    onClick = { onQuantityChange(cartItem.quantity + 1) },
                    modifier = Modifier.size(36.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEE)),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("+", color = Color(0xFF1C1C1C), style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String, color: Color = Color(0xFF1C1C1C)) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color(0xFF616161),
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = value,
            color = color,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

// Helper
private fun com.example.yummynutrition.data.model.FoodItem.nutrientValue(key: String): Double {
    return this.foodNutrients
        .firstOrNull { n -> n.nutrientName.contains(key, true) }
        ?.value ?: 0.0
}
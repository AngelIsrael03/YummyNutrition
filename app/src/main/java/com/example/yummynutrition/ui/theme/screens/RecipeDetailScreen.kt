package com.example.yummynutrition.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.yummynutrition.viewmodel.MainViewModel

@Composable
fun RecipeDetailScreen(
    id: String,
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val recipe by viewModel.selectedRecipe.collectAsState()

    LaunchedEffect(id) {
        viewModel.loadRecipeById(id)
    }

    if (recipe == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val meal = recipe!!

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111111))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // 🔙 BOTÓN BACK
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Image(
            painter = rememberAsyncImagePainter(meal.image),
            contentDescription = meal.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            meal.name,
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Instructions", color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text(meal.instructions, color = Color.LightGray)
    }
}

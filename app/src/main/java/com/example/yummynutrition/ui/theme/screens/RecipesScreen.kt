package com.example.yummynutrition.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.yummynutrition.navigation.Screen
import com.example.yummynutrition.viewmodel.MainViewModel

@Composable
fun RecipesScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    var search by remember { mutableStateOf("") }
    val recipes by viewModel.recipes.collectAsState()

    LaunchedEffect(search) {
        if (search.length > 2) {
            viewModel.searchRecipes(search)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111111))
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            label = { Text("Search recipes") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFF9300),
                focusedLabelColor = Color(0xFFFF9300),
                cursorColor = Color(0xFFFF9300),
                unfocusedBorderColor = Color.Gray,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(recipes) { recipe ->
                RecipeCard(
                    title = recipe.name,
                    image = recipe.image,
                    onClick = {
                        navController.navigate(
                            Screen.RecipeDetail.createRoute(recipe.idMeal)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun RecipeCard(
    title: String,
    image: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
    ) {
        Column {

            Image(
                painter = rememberAsyncImagePainter(image),
                contentDescription = title,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

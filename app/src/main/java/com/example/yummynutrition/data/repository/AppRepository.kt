package com.example.yummynutrition.data.repository

import com.example.yummynutrition.data.api.NutritionApiService
import com.example.yummynutrition.data.api.RecipesApiService
import com.example.yummynutrition.data.api.RetrofitClient
import com.example.yummynutrition.data.model.FoodItem
import com.example.yummynutrition.data.model.RecipeItem
import com.example.yummynutrition.data.model.RecipeResponse

private const val USDA_API_KEY = "urDKI8sx9DNjW4ZDIlyacaGg0DbfJ623Li45H6bU"

class AppRepository {

    private val recipesApi =
        RetrofitClient.getRecipesInstance()
            .create(RecipesApiService::class.java)

    private val nutritionApi =
        RetrofitClient.getNutritionInstance()
            .create(NutritionApiService::class.java)

    // 🔍 Buscar recetas
    suspend fun searchRecipes(query: String): RecipeResponse? =
        try {
            recipesApi.searchRecipes(query)
        } catch (_: Exception) {
            null
        }

    // 🍌 Buscar nutrición (USDA)
    suspend fun getNutrition(food: String): FoodItem? =
        try {
            nutritionApi.searchFood(
                query = food,
                apiKey = USDA_API_KEY
            ).foods.firstOrNull()
        } catch (_: Exception) {
            null
        }

    // 📖 Receta por ID
    suspend fun getRecipeById(id: String): RecipeItem? =
        try {
            recipesApi.getRecipeById(id).meals?.firstOrNull()
        } catch (_: Exception) {
            null
        }
}

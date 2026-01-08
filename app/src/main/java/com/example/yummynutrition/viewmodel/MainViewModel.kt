package com.example.yummynutrition.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yummynutrition.data.model.FoodItem
import com.example.yummynutrition.data.model.RecipeItem
import com.example.yummynutrition.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repo = AppRepository()

    private val _recipes = MutableStateFlow<List<RecipeItem>>(emptyList())
    val recipes: StateFlow<List<RecipeItem>> = _recipes

    private val _nutrition = MutableStateFlow<FoodItem?>(null)
    val nutrition: StateFlow<FoodItem?> = _nutrition
    private val _selectedRecipe = MutableStateFlow<RecipeItem?>(null)
    val selectedRecipe: StateFlow<RecipeItem?> = _selectedRecipe

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            val response = repo.searchRecipes(query)
            _recipes.value = response?.meals ?: emptyList()
        }
    }

    fun getNutrition(food: String) {
        viewModelScope.launch {
            _nutrition.value = repo.getNutrition(food)
        }
    }



    fun loadRecipeById(id: String) {
        viewModelScope.launch {
            _selectedRecipe.value = repo.getRecipeById(id)
        }
    }
}

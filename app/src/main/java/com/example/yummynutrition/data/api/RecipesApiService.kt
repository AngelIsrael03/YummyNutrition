package com.example.yummynutrition.data.api

import com.example.yummynutrition.data.model.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesApiService {

    @GET("search.php")
    suspend fun searchRecipes(
        @Query("s") query: String
    ): RecipeResponse

    @GET("lookup.php")
    suspend fun getRecipeById(
        @Query("i") id: String
    ): RecipeResponse
}

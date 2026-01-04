package com.example.yummynutrition.data.api

import com.example.yummynutrition.data.model.NutritionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NutritionApiService {

    @GET("foods/search")
    suspend fun searchFood(
        @Query("query") query: String,
        @Query("api_key") apiKey: String
    ): NutritionResponse
}

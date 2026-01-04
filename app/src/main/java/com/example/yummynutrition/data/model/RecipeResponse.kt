package com.example.yummynutrition.data.model

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("meals")
    val meals: List<RecipeItem>?
)

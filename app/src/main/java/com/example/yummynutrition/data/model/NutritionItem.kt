package com.example.yummynutrition.data.model

import com.google.gson.annotations.SerializedName

data class NutritionItem(
    val name: String,
    @SerializedName("calories") val calories: Double,
    @SerializedName("protein_g") val proteinG: Double,
    @SerializedName("fat_total_g") val fatTotalG: Double,
    @SerializedName("carbohydrates_total_g") val carbsG: Double
)

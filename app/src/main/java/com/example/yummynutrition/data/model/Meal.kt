package com.example.yummynutrition.data.model

data class Meal(
    val id: Int,
    var hour: String = "",
    var description: String = "",
    var alarmEnabled: Boolean = false
)

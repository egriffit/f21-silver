package com.example.workout_companion.api.edamam.entities

data class FoodData (
    val name: String,
    val foodId: String,
    val calories: Int,
    val carbohydrates: Double,
    val protein: Double,
    val fat: Double
)
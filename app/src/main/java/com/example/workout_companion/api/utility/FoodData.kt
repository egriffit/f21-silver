package com.example.workout_companion.api.utility

data class FoodData (
    val name: String,
    val foodId: String,
    val calories: Int,
    val carbohydrates: Double,
    val protein: Double,
    val fat: Double,
    val servings: Map<String, Double>
)
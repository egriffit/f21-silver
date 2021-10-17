package com.example.workout_companion.api.nutrition_api_ninja.entities

data class ApiNinjaNutritionItem(
    val calories: Double,
    val carbohydrates_total_g: Double,
    val cholesterol_mg: Int,
    val fat_saturated_g: Double,
    val fat_total_g: Double,
    val fiber_g: Double,
    val name: String,
    val potassium_mg: Int,
    val protein_g: Double,
    val serving_size_g: Double,
    val sodium_mg: Int,
    val sugar_g: Double
)
package com.example.workout_companion.sampleData

import com.example.workout_companion.entity.FoodTypeEntity

val sampleFoodTypeList = listOf(
    FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
    80.0, 100.0, 1.3, 98.0, 0.7),
    FoodTypeEntity(2, "lettuce", "-1",
        80.0, 80.0, 1.3, 98.0, 0.7)
)

val sampleFoodTypeOneFoodList = listOf(FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
    80.0, 100.0, 1.3, 98.0, 0.7))


val sampleFoodUpdateExample = FoodTypeEntity(1, "carrot", "-1",
    80.0, 100.0, 1.3, 98.0, 0.7)
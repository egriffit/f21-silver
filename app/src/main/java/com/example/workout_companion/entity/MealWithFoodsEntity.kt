package com.example.workout_companion.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * An entity joining the food_in_meal, meal, and food_type tables in the database
 *
 * @property foodWithMeal      Entity for the food_in_meal table
 * @property meal               Entity for the meal table
 * @property food               Entity for the food_type table
 */
data class MealWithFoodsEntity (
    @Embedded val foodWithMeal: FoodInMealEntity,
    @Relation(
        parentColumn = "meal_id",
        entityColumn = "id"
    )
    val meal: MealEntity,
    @Relation(
        parentColumn = "food_id",
        entityColumn = "id"
    )
    val food: FoodTypeEntity
)
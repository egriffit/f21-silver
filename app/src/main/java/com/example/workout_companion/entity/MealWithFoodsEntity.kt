package com.example.workout_companion.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * An entity joining the food_in_meal, meal, and food_type tables in the database
 *
 * @property meal               Entity for the FoodInMealWithNameEntity view
 * @property food               Entity for the food_type table
 */
data class MealWithFoodsEntity (
    @Embedded val food_in_meal: FoodInMealEntity,
    @Relation(
        parentColumn = "meal_id",
        entityColumn = "id"
    )
    val meal: MealEntity,
    @Relation(
        parentColumn = "food_id",
         entityColumn = "id"
    )
    val foods: List<FoodTypeEntity>

)


package com.example.workout_companion.entity

import java.time.LocalDate
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * An entity for the meal table
 *
 * @property meal_id                    id for meal in meal table
 * @property food_in                    id for food in food_type table
 * @property servings                   number of servings of food in meal
 */

@Entity(tableName = "food_in_meal",
        primaryKeys = ["meal_id", "food_id"])
data class FoodInMealEntity(
    @ColumnInfo(name = "meal_id")
    var meal_id: Int,

    @ColumnInfo(name = "food_id")
    var food_id: Int,

    @ColumnInfo(name = "servings")
    var servings: Double
)

package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * An entity for the nutrition_plan_type table in the database
 *
 * @property id             primary key
 * @property goal_id        Foreign key joining the nutrition_plan_type table with the goal_type table
 * @property calorie        The target amount of  calories per day
 * @property carbohydrate   The target percentage of dietary carbohydrates per day
 * @property protein        The target percentage of dietary protein per day
 * @property fat            The target percentage of dietary fat per day
 */
@Entity(tableName = "nutrition_plan_type")
data class NutritionPlanTypeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "goal_id")
    var goal_id: Int,

    @ColumnInfo(name = "calorie")
    var calorie: Double,

    @ColumnInfo(name = "carbohydrate")
    var carbohydrate: Double,

    @ColumnInfo(name = "protein")
    var protein: Double,

    @ColumnInfo(name = "fat")
    var fat: Double
    )

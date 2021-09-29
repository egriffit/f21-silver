package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class to store the properties of the NutritionPlanTypeEntity
 *
 * Defines the nutrition_plan_type table and columns for RoomDatabase
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

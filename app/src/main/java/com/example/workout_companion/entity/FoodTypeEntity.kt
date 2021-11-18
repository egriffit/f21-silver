package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * An entity for the food_type table
 *
 * @property id                         Primary key, food id
 * @property name                       Name of food
 * @property edamam_id                  id for edamam api, -1 by default
 * @property serving_size               serving_size in grams
 * @property calories                   Total calories
 * @property carbohydrates              Carbohydrates in grams
 * @property protein                     protein in grams
 * @property fat                        fat in grams
 */
@Entity(tableName = "food_type")
data class FoodTypeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "edamam_id")
    var edamam_id: String,

    @ColumnInfo(name = "serving_size")
    var serving_size: Double,

    @ColumnInfo(name = "calories")
    var calories: Double,

    @ColumnInfo(name = "carbohydrates")
    var carbohydrates: Double,

    @ColumnInfo(name = "protein")
    var protein: Double,

    @ColumnInfo(name = "fat")
    var fat: Double
)

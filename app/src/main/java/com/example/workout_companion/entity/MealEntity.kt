package com.example.workout_companion.entity

import java.time.LocalDate
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * An entity for the meal table
 *
 * @property id                         Primary key, food id
 * @property type                       Name of meal
 * @property calories                   Total calories
 * @property carbohydrates              Carbohydrates in grams
 * @property protein                    protein in grams
 * @property fat                        fat in grams
 * @property date                       date of meal, localDate
 */

@Entity(tableName = "meal")
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "calories")
    var calories: Double,

    @ColumnInfo(name = "carbohydrates")
    var carbohydrates: Double,

    @ColumnInfo(name = "protein")
    var protein: Double,

    @ColumnInfo(name = "fat")
    var fat: Double,

    @ColumnInfo(name = "date")
    var date: LocalDate
)

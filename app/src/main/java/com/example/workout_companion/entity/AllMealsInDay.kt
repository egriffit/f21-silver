package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import java.time.LocalDate

data class AllMealsInDay(
    var calories: Double,

    var carbohydrates: Double,

    var protein: Double,

    var fat: Double,

    var date: LocalDate
)

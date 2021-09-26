package com.example.workout_companion.entity

import androidx.room.*
import com.example.workout_companion.utility.DateTimeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "user")
data class UserEntity(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "experience_level")
        var experience_level: String,

        @ColumnInfo(name = "sex")
        var sex: String,

        @ColumnInfo(name = "birth_date")
        @TypeConverters(DateTimeConverter::class)
        //var birth_date: Date,
        var birth_date: LocalDate,

        @ColumnInfo(name = "max_workouts_per_week")
        var max_workouts_per_week: Int,

        @ColumnInfo(name = "activity_level")
        var activity_level: String
)
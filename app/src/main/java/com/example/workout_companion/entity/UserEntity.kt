package com.example.workout_companion.entity

import androidx.room.*
import com.example.workout_companion.utility.DateTimeConverter
import java.time.LocalDate

/**
 * An entity for the user table in the database
 *
 * @property name                       Primary key, the name of the user
 * @property experience_level           The user's reported workout experience, beginner, intermediate, expert
 * @property sex                        The user's gender
 * @property birth_date                 The user's date of birth
 * @property max_workouts_per_week      The number of workouts the user can do per week
 * @property activity_level             The user's reported activity level
 */
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
package com.example.workout_companion.entity

import androidx.room.*
import com.example.workout_companion.enumeration.*
import com.example.workout_companion.utility.*
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
@Entity(tableName = "user",
        foreignKeys = [
                ForeignKey(entity = GoalTypeEntity::class,
                        parentColumns = ["id"],
                        childColumns = ["goal_id"],
                        onDelete = ForeignKey.NO_ACTION
                )
        ]
)
data class UserEntity(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "experience_level")
        @TypeConverters(ExperienceLevelConverter::class)
        var experience_level: ExperienceLevel,

        @ColumnInfo(name = "sex")
        @TypeConverters(SexConverter::class)
        var sex: Sex,

        @ColumnInfo(name = "birth_date")
        @TypeConverters(DateTimeConverter::class)
        //var birth_date: Date,
        var birth_date: LocalDate,

        @ColumnInfo(name = "max_workouts_per_week")
        var max_workouts_per_week: Int,

        @ColumnInfo(name = "height")
        var height: Double,

        @ColumnInfo(name = "weight")
        var weight: Double,

        @ColumnInfo(name = "activity_level")
        @TypeConverters(ActivityLevelConverter::class)
        var activity_level: ActivityLevel,

        @ColumnInfo(name = "goal_id", index = true)
        var goal_id: Int,
)
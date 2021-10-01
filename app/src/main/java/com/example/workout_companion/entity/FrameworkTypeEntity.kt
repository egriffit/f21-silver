package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * An entity representing a workout framework table in the database
 *
 * @property id                 the primary key of the framework.
 * @property name               the descriptive name of the framework.
 * @property workouts_per_week  the number of workouts this framework prescribes per week.
 */
@Entity(tableName = "framework_type")
data class FrameworkTypeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "workouts_per_week")
    var workouts_per_week: Int,
)
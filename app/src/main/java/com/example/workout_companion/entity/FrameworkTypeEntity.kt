package com.example.workout_companion.entity

import androidx.room.*

/**
 * An entity representing a workout framework table in the database
 *
 * @property id                 the primary key of the framework.
 * @property name               the descriptive name of the framework.
 * @property workouts_per_week  the number of workouts this framework prescribes per week.
 */
@Entity(tableName = "framework_type",
        foreignKeys = [ForeignKey(entity = GoalTypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["goal_id"],
            onDelete = ForeignKey.CASCADE)]
)
data class FrameworkTypeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "goal_id", index = true)
    var goal_id: Int,

    @ColumnInfo(name = "workouts_per_week")
    var workouts_per_week: Int,
)
package com.example.workout_companion.mock.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "framework_type")
data class FrameworkTypeEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "goal_id")
    var goal_id: Int,

    @ColumnInfo(name = "workouts_per_week")
    var workouts_per_week: Int,
)
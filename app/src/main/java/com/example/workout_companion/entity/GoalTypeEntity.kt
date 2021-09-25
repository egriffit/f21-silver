package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goal_type")
data class GoalTypeEntity(
    @PrimaryKey(autoGenerate = false)

    @ColumnInfo(name = "goal")
    var goal: String
)
package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "framework_type",
    foreignKeys = [ForeignKey(entity = GoalTypeEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("goal_id"),
        onDelete = ForeignKey.RESTRICT)]
)
data class FrameworkTypeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(index = true)
    var goal_id: Int,

    @ColumnInfo(name = "workouts_per_week")
    var numWorkouts: Int,
)
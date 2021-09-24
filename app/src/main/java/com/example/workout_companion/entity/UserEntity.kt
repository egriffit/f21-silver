package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "experience_level")
        var experience_level: String,

        @ColumnInfo(name = "sex")
        var sex: String,

        @ColumnInfo(name = "age")
        var age: Int,

        @ColumnInfo(name = "max_workouts_per_week")
        var max_workouts_per_week: Int,

        @ColumnInfo(name = "activity_level")
        var activity_level: String
)
package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A current_user_goal table in the database
 *
 * @property nutrition_plan_type_id   part of the key and foreign key for nutrition_plan_type table
 * @property framework_type_id        part of the key and foreign key for framework_type table
 */
@Entity(tableName = "current_user_goal")
data class CurrentUserGoalEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "nutrition_plan_type_id")
    var nutrition_plan_type_id: Int,

    @ColumnInfo(name = "framework_type_id")
    var framework_type_id: Int
)
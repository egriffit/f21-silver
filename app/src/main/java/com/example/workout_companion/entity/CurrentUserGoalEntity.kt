package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_user_goal",
primaryKeys = arrayOf("nutrition_plan_type_id", "framework_type_id"))
data class CurrentUserGoalEntity (
    @ColumnInfo(name = "nutrition_plan_type_id")
    var nutrition_plan_type_id: Int,

    @ColumnInfo(name = "framework_type_id")
    var framework_type_id: Int

)
package com.example.workout_companion.mock.entity

import androidx.room.*

@DatabaseView("""SELECT framework_type.id, name, workouts_per_week, goal_id, goal
FROM framework_type
INNER JOIN goal_type
ON framework_type.goal_id = goal_type.id""")
data class FrameworkWithGoalEntity (
    val id: Int,
    val name: String,
    val workouts_per_week: Int,
    val goal_id: Int,
    val goal: String
)
package com.example.workout_companion.database

import com.example.workout_companion.entity.GoalTypeEntity

/**
 * A collection of the goals that exist in our model. Add any new goals here
 * and they will be added to the database automatically.
 */
val GOALS = mapOf(
    Pair(0, GoalTypeEntity(0, "Build Muscle", 250)),
    Pair(1, GoalTypeEntity(1, "Gain Strength", 500)),
    Pair(2, GoalTypeEntity(2, "Lose Weight", -500)),
)
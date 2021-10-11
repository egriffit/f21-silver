package com.example.workout_companion.utility

import com.example.workout_companion.entity.GoalTypeEntity
import kotlin.math.roundToInt

/**
 * Estimates the caloric intake of an individual
 *
 * @property weight The weight (in kgs).
 * @property height The height (in cm).
 * @property age The age (in years).
 * @property activityLevel The perceived activity level.
 * @property gender The biological gender.
 * @property goal The current goal.
 *
 * @return An estimation of caloric intake for an individual.
 */
fun estimateCaloricIntake(
    weight: Double,
    height: Double,
    age: Int,
    activityLevel: ActivityLevel,
    gender: Sex,
    goal: GoalTypeEntity
): Int {
    return ((9.99 * weight + 6.25 * height - 5 * age + 166 * gender.scaleFactor - 161) * activityLevel.scaleFactor).roundToInt() + goal.caloric_addition
}
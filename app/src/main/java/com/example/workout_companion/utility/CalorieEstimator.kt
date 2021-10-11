package com.example.workout_companion.utility

import kotlin.math.roundToInt

/**
 * Estimates the caloric intake of an individual
 *
 * @property weight The weight (in kgs).
 * @property height The height (in cm).
 * @property age The age (in years).
 * @property activityLevel The perceived activity level.
 * @property gender The biological gender.
 *
 * @return An estimation of caloric intake for an individual.
 */
fun estimateCaloricIntake(
    weight: Double,
    height: Double,
    age: Int,
    activityLevel: ActivityLevel,
    gender: Sex,
): Int {
    return ((9.99 * weight + 6.25 * height - 5 * age + 166 * gender.scaleFactor - 161) * activityLevel.scaleFactor).roundToInt()
}
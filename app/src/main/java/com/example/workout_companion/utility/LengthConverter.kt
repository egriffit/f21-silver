package com.example.workout_companion.utility

import kotlin.math.floor

/**
 * Utility class for converting between length types
 *
 * Conversions supported:
 *      Inches to Centimeters
 *      Feet, Inches to Centimeters
 *      Centimeters to Inches
 *      Centimeters to Feet, Inches
 */
object LengthConverter {

    // Scale factors
    private const val FT_TO_IN = 12.0
    private const val IN_TO_FT = 1 / FT_TO_IN
    private const val IN_TO_CM = 2.54
    private const val CM_TO_IN = 1 / IN_TO_CM

    /**
     * Convert inches to centimeters
     *
     * @property inches The inches to convert.
     *
     * @return The inches converted to centimeters.
     */
    fun toCentimeters (inches: Double) : Double {
        return inches * IN_TO_CM
    }

    /**
     * Convert a height (in feet and inches) to centimeters
     *
     * @property feet The feet portion of the height.
     * @property inches The inches portion of the height.
     *
     * @return The height converted to centimeters.
     */
    fun toCentimeters (feet: Double, inches: Double) : Double {
        return (feet * FT_TO_IN + inches) * IN_TO_CM
    }

    /**
     * Convert centimeters to inches
     *
     * @property centimeters The centimeters to convert to inches.
     *
     * @return The centimeters converted to inches.
     */
    fun toInches(centimeters: Double) : Double {
        return centimeters * CM_TO_IN
    }

    /**
     * Convert centimeters to height (in feet and inches)
     *
     * @property centimeters The centimeters to convert to height.
     *
     * @return The centimeters converted to height (in feet and inches).
     */
    fun toFeetAndInches(centimeters: Double) : Pair<Double, Double> {
        var inches = toInches(centimeters)
        val feet = floor(inches * IN_TO_FT)
        inches -= feet * FT_TO_IN

        return Pair(feet, inches)
    }
}
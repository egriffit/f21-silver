package com.example.workout_companion.enumeration

import androidx.room.TypeConverter
import java.lang.IllegalArgumentException

/**
 * Enumeration of all activity levels
 *
 * Source for these entries: https://www.actabit.com/physical-activity-levels-pal/
 *
 * @property descName The descriptive name of the activity level
 * @property scaleFactor The scale factor used for calculating total daily energy expenditure
 */
enum class ActivityLevel(val descName: String, val scaleFactor: Double) {
    SEDENTARY("Not active at all", 1.2),
    SLIGHTLY_ACTIVE("Active 1-3 days/week", 1.375),
    MODERATELY_ACTIVE("Active 3-5 days/week", 1.55),
    VERY_ACTIVE("Active 6-7 days/week", 1.725),
    EXTRA_ACTIVE("Physical job or active 2x/day", 1.9),
}

/**
 * Converter between scale factors and ActivityLevel enum class
 */
object ActivityLevelConverter {

    /**
     * Converts an ActivityLevel to its scale factor
     *
     * @property level The ActivityLevel enumeration to convert.
     *
     * @return The scale factor of the ActivityLevel.
     */
    @TypeConverter
    @JvmStatic
    fun fromActivityLevel(level: ActivityLevel): Double {
        return level.scaleFactor
    }

    /**
     * Converts a scale factor to its ActivityLevel enumeration
     *
     * @property factor The scale factor to convert.
     *
     * @return The ActivityLevel enumeration.
     *
     * @throws IllegalArgumentException
     */
    @TypeConverter
    @JvmStatic
    fun toActivityLevel(factor: Double): ActivityLevel {
        for (enum in ActivityLevel.values()) {
            if (factor == enum.scaleFactor) {
                return enum
            }
        }

        throw IllegalArgumentException("No ActivityLevel has a scale factor of $factor")
    }
}
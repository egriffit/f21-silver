package com.example.workout_companion.utility

import androidx.room.TypeConverter
import java.lang.IllegalArgumentException

/**
 * Enumerations of experience level
 */
enum class ExperienceLevel(val descName: String) {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    EXPERT("Expert"),
}

/**
 * Converts between descriptive name and experience level
 */
object ExperienceLevelConverter {

    /**
     * Converts an experience level to its descriptive name
     *
     * @property level The experience level to convert.
     *
     * @return The descriptive name.
     */
    @TypeConverter
    @JvmStatic
    fun fromExperienceLevel(level: ExperienceLevel): String {
        return level.descName
    }

    /**
     * Converts a descriptive name to its experience level
     *
     * @property name The descriptive name to convert.
     *
     * @return The experience level.
     *
     * @throws IllegalArgumentException
     */
    @TypeConverter
    @JvmStatic
    fun toExperienceLevel(name: String): ExperienceLevel {
        for (enum in ExperienceLevel.values()) {
            if (enum.descName == name) {
                return enum
            }
        }

        throw IllegalArgumentException("$name is not a valid experience level")
    }
}
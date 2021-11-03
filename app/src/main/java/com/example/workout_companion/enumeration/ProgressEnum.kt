package com.example.workout_companion.enumeration

import androidx.room.TypeConverter
import java.lang.IllegalArgumentException

/**
 * Enumeration class for the state of progress of an item
 *
 * @property descName The descriptive name of the state
 */
enum class Progress(val descName: String) {
    NOT_STARTED("Not Started"),
    IN_PROGRESS("In Progress"),
    COMPLETE("Complete"),
}

/**
 * Converter between a progress enumeration and its descriptive name
 */
object ProgressConverter {

    /**
     * Convert a progress enumeration to a string
     *
     * @property progress The progress enumeration to convert.
     *
     * @return The descriptive name of the progress
     */
    @TypeConverter
    @JvmStatic
    fun toString(progress: Progress): String {
        return progress.descName
    }

    /**
     * Convert a string to its accompanying progress enumeration
     *
     * @property name The descriptive name of the progress enumeration.
     *
     * @throws IllegalArgumentException if an invalid name is supplied
     *
     * @return The progress enumeration.
     */
    @TypeConverter
    @JvmStatic
    fun toProgress(name: String): Progress {
        Progress.values().forEach { progress ->
            if (progress.descName == name) return progress
        }

        throw IllegalArgumentException("$name is not a valid Progress enumeration.")
    }
}


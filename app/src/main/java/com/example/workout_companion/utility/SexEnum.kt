package com.example.workout_companion.utility

import androidx.room.TypeConverter
import java.lang.IllegalArgumentException

/**
 * Enumeration for biological sexes
 *
 * @property descName The descriptive name of the biological sex.
 */
enum class Sex(val descName: String, val scaleFactor: Int) {
    MALE("Male", 1),
    FEMALE("Female", 0),
}

/**
 * Converter between biological sexes and their descriptive names
 */
object SexConverter {

    /**
     * Convert a sex to its descriptive name
     *
     * @property sex The enumerated sex to convert.
     *
     * @return The descriptive name.
     */
    @TypeConverter
    @JvmStatic
    fun fromSex(sex: Sex): String {
        return sex.descName
    }

    /**
     * Convert a descriptive name to its sex enumeration
     *
     * @property name The descriptive name of the enumeration.
     *
     * @return The enumerated sex.
     *
     * @throws IllegalArgumentException
     */
    @TypeConverter
    @JvmStatic
    fun toSex(name: String): Sex {
        for (enum in Sex.values()) {
            if (enum.descName == name) {
                return enum
            }
        }

        throw IllegalArgumentException("$name is not a valid biological sex")
    }
}
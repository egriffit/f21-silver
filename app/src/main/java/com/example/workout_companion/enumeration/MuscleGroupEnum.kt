package com.example.workout_companion.enumeration

import androidx.room.TypeConverter
import java.lang.IllegalArgumentException

/**
 * Enumeration of all muscle groups
 */
enum class MuscleGroup {
    CHEST, BACK, TRICEPS, BICEPS, SHOULDERS, ABS, LEGS
}

/**
 * Converter between strings and MuscleGroup enumeration class
 */
object MuscleGroupConverter {

    @TypeConverter
    @JvmStatic
    fun fromMuscleGroup(group: MuscleGroup) : String {
        return group.name
    }

    @TypeConverter
    @JvmStatic
    fun toMuscleGroup(name: String) : MuscleGroup {
        for (enum in MuscleGroup.values()) {
            if (enum.name == name) {
                return enum
            }
        }

        throw IllegalArgumentException("$name is an invalid MuscleGroup enumeration")
    }
}
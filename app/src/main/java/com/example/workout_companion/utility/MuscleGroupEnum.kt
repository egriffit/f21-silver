package com.example.workout_companion.utility

import androidx.room.TypeConverter
import java.lang.IllegalArgumentException

enum class MuscleGroup {
    CHEST, BACK, TRICEPS, BICEPS, SHOULDERS, ABS, LEGS
}

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
            if (enum.name.equals(name)) {
                return enum
            }
        }

        throw IllegalArgumentException("$name is an invalid MuscleGroup enumeration")
    }
}
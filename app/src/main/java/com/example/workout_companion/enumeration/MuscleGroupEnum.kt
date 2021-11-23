package com.example.workout_companion.enumeration

import androidx.room.TypeConverter
import com.example.workout_companion.api.wger.utility.muscleName
import java.lang.IllegalArgumentException

/**
 * Enumeration of all muscle groups
 */
enum class MuscleGroup(val wgerMuscles: List<muscleName>) {
    CHEST(listOf(muscleName.PECTORALIS_MAJOR, muscleName.SERRATUS_ANTERIOR)),
    BACK(listOf(muscleName.TRAPEZIUS, muscleName.LATISSIMUS_DORSI)),
    TRICEPS(listOf(muscleName.TRICEPS_BRACHII)),
    BICEPS(listOf(muscleName.BICEPS_BRACHII, muscleName.BICEPS_FEMORIS, muscleName.BRACHIALIS)),
    SHOULDERS(listOf(muscleName.ANTERIOR_DELTOID)),
    ABS(listOf(muscleName.RECTUS_ADOMINIS, muscleName.OBLIQUUS_EXTERNUS_ABDOMINIS)),
    LEGS(listOf(muscleName.GLUTEUS_MAXIMUS, muscleName.QUADRICEPS_FEMORIS)),
    CALVES(listOf(muscleName.GASTROCNEMIUS, muscleName.SOLEUS)),
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
package com.example.workout_companion.api.wger.utility

import androidx.room.TypeConverter
import com.example.workout_companion.enumeration.MuscleGroup
import java.lang.IllegalArgumentException

enum class muscleName(val descName: String, val id: Int) {
    BICEPS_BRACHII("Biceps brachii", 1),
    ANTERIOR_DELTOID("Anterior deltoid", 2),
    SERRATUS_ANTERIOR("Serratus anterior", 3),
    PECTORALIS_MAJOR("Pectoralis major", 4),
    TRICEPS_BRACHII("Triceps brachii", 5),
    RECTUS_ADOMINIS("Rectus abdominis", 6),
    GASTROCNEMIUS("Gastrocnemius", 7),
    GLUTEUS_MAXIMUS("Gluteus maximus", 8),
    TRAPEZIUS("Trapezius", 9),
    QUADRICEPS_FEMORIS("Quadriceps femoris", 10),
    BICEPS_FEMORIS("Biceps femoris", 11),
    LATISSIMUS_DORSI("Latissimus dorsi", 12),
    BRACHIALIS("Brachialis", 13),
    OBLIQUUS_EXTERNUS_ABDOMINIS("Obliquus externus abdominis", 14),
    SOLEUS("Soleus", 15),
}
object muscleNameConverter {
    @TypeConverter
    @JvmStatic
    fun toMuscleName(name: String): Int {
        for (m in muscleName.values()) {
            if (m.descName == name) {
                return m.id
            }
        }
        throw IllegalArgumentException("$name is an invalid muscleName enumeration")
    }

    fun fromMuscleName(id: Int): String {
        for (m in muscleName.values()) {
            if (m.id == id) {
                return m.descName
            }
        }
        throw IllegalArgumentException("$id is an invalid muscleName enumeration")
    }
}
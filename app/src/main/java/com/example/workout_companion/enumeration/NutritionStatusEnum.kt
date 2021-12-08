package com.example.workout_companion.enumeration

import androidx.room.TypeConverter
import java.lang.IllegalArgumentException

/**
 * Enumeration of all nutrition statuses
 *
 * @property descName The descriptive name of the nutritional status
 */
enum class NutritionStatusEnum(val descName: String) {
    ON_TRACK("On Track"),
    BELOW_TARGET("Below Target"),
    ABOVE_TARGET("Above Target"),
}

/**
 * Converter between desc_name and nutrition statuses
 */
object NutritionStatusConverter {

    /**
     * Converts a NutritionStatusEnum to its scale factor
     *
     * @property status The NutritionStatus enumeration to convert.
     *
     * @return The nutrition status.
     */
    @TypeConverter
    @JvmStatic
    fun fromActivityLevel(status: NutritionStatusEnum): String {
        return status.descName
    }

    /**
     * Converts a descriptive name to its nutrition status
     *
     * @property name The descriptive name to convert.
     *
     * @return The nutrition status.
     *
     * @throws IllegalArgumentException
     */
    @TypeConverter
    @JvmStatic
    fun toNutritionalStatus(factor: String): NutritionStatusEnum {
        for (enum in NutritionStatusEnum.values()) {
            if (factor == enum.descName) {
                return enum
            }
        }

        throw IllegalArgumentException("No NutritionStatus has a scale factor of $factor")
    }
}
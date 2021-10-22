package com.example.workout_companion.utility

import androidx.room.TypeConverter
import java.lang.IllegalArgumentException

/**
 * Enumeration of the possible goals the user can select
 *
 * @property descName The descriptive name of the enumeration.
 */
enum class MainGoal(val descName: String) {
    BUILD_MUSCLE("Build Muscle"),
    INCREASE_STRENGTH("Increase Strength"),
    LOSE_WEIGHT("Lose Weight"),
}

/**
 * Converter between String and MainGoal, for use with storing MainGoal's in the database
 */
object MainGoalConverter {

    /**
     * Converts from a MainGoal to a String
     *
     * @property goal The MainGoal to convert.
     *
     * @return The string value of the MainGoal
     */
    @TypeConverter
    @JvmStatic
    fun fromMainGoal(goal: MainGoal) : String {
        return goal.name
    }

    /**
     * Converts from a MainGoal's string to a MainGoal object
     *
     * @property name The name attribute of the MainGoal.
     *
     * @throws IllegalArgumentException if the name does not match any MainGoal's.
     *
     * @return The MainGoal object matching the name.
     */
    @TypeConverter
    @JvmStatic
    fun toMainGoal(name: String) : MainGoal {
        MainGoal.values().forEach { goal ->
            if (goal.name == name) {
                return goal
            }
        }

        throw IllegalArgumentException("$name is not a valid MainGoal")
    }
}
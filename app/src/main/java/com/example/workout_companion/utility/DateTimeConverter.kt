package com.example.workout_companion.utility

import androidx.room.TypeConverter
import java.time.*
import java.time.format.DateTimeFormatter

/***
 * Helper object used to convert Strings to LocalTime objects and vis versa
 *
 * Modified from https://medium.com/androiddevelopers/room-time-2b4cf9672b98
 */
object DateTimeConverter {
    /**
     * DataTimeFormatter object with the pattern used to format LocalDateStrings
     */
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    /**
     * Method that takes a string, parses it and then returns a LocalDate object
     * @param value, a string
     * @return LocalDate
     */
    @TypeConverter
    @JvmStatic
    fun toLocalDate(value: String?): LocalDate? {

        return value?.let {
            return LocalDate.parse(value, formatter)
        }
    }

    /**
     * Method that takes a LocalDate object, parses it and then returns a String
     * @param value, a LocalDate object
     * @return String, date formatted as a String
     */
    @TypeConverter
    @JvmStatic
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(formatter)
    }
}
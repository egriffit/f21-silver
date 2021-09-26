package com.example.workout_companion.utility

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQueries.localDate





//https://medium.com/androiddevelopers/room-time-2b4cf9672b98
object DateTimeConverter {
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun toLocalDate(value: String?): LocalDate? {

        return value?.let {
            return LocalDate.parse(value, formatter)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    @JvmStatic
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(formatter)
    }
}
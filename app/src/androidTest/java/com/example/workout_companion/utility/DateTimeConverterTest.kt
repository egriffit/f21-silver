package com.example.workout_companion.utility

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.Month

@RunWith(AndroidJUnit4::class)
class DateTimeConverterTest: TestCase()
{
    @Test
    fun testToLocalDate(){
        val date: LocalDate = LocalDate.of(2021, Month.SEPTEMBER, 27)
        val dateString: String = "2021-09-27"
        val conversion: LocalDate? = DateTimeConverter.toLocalDate(dateString)
        assertEquals(conversion, date)
    }

    @Test
    fun testFromLocalDate(){
        val date: LocalDate = LocalDate.of(2021, Month.SEPTEMBER, 27)
        val conversion: String? = DateTimeConverter.fromLocalDate(date)
        assertEquals(conversion, "2021-09-27")
    }
}
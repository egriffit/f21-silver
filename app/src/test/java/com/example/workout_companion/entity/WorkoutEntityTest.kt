package com.example.workout_companion.entity

import com.example.workout_companion.enumeration.Progress
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class WorkoutEntityTest {

    @Test
    fun constructorTest() {
        val date = LocalDate.of(2021, 10, 31)
        val frameworkDayId = 0
        val status = Progress.NOT_STARTED

        val entity = WorkoutEntity(date, status, frameworkDayId)

        assertEquals(entity.date, date)
        assertEquals(entity.framework_day_id, frameworkDayId)
        assertEquals(entity.status, status)
    }
}
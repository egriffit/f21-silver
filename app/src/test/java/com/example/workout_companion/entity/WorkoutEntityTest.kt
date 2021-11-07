package com.example.workout_companion.entity

import com.example.workout_companion.enumeration.Progress
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class WorkoutEntityTest {

    @Test
    fun constructorTest() {
        val date = LocalDate.of(2021, 10, 31)
        val frameworkId = 0
        val status = Progress.NOT_STARTED

        val entity = WorkoutEntity(date, status, frameworkId)

        assertEquals(entity.date, date)
        assertEquals(entity.framework_id, frameworkId)
        assertEquals(entity.status, status)
    }
}
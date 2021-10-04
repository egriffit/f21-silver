package com.example.workout_companion.entity

import org.junit.Assert.assertEquals
import org.junit.Test

class FrameworkDayEntityTest {

    @Test
    fun constructorTest() {
        val day = FrameworkDayEntity(0, 1, "Test Day")

        assertEquals(day.id, 0)
        assertEquals(day.framework_type_id, 1)
        assertEquals(day.name, "Test Day")
    }
}
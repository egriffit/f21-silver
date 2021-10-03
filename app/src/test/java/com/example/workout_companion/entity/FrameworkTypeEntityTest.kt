package com.example.workout_companion.entity

import org.junit.Test
import org.junit.Assert.assertEquals

class FrameworkTypeEntityTest {
    @Test
    fun constructorTest() {
        val frameworkType = FrameworkTypeEntity(0, "Test Framework", 0,3)

        assertEquals(frameworkType.id, 0)
        assertEquals(frameworkType.name, "Test Framework")
        assertEquals(frameworkType.goal_id, 0)
        assertEquals(frameworkType.workouts_per_week, 3)
    }
}
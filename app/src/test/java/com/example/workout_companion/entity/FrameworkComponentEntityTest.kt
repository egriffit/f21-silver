package com.example.workout_companion.entity

import com.example.workout_companion.enumeration.MuscleGroup
import org.junit.Assert.assertEquals
import org.junit.Test

class FrameworkComponentEntityTest {
    @Test
    fun constructorTest() {
        val component = FrameworkComponentEntity(0, 1, MuscleGroup.BICEPS, 3, 24)

        assertEquals(component.id, 0)
        assertEquals(component.framework_day_id, 1)
        assertEquals(component.muscle_group, MuscleGroup.BICEPS)
        assertEquals(component.target_sets, 3)
        assertEquals(component.target_reps, 24)
    }
}
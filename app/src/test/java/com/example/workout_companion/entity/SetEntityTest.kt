package com.example.workout_companion.entity

import com.example.workout_companion.enumeration.Progress
import org.junit.Assert.assertEquals
import org.junit.Test

class SetEntityTest {

    @Test
    fun constructorTest() {
        val id = 0
        val frameworkComponentSetId = 0
        val reps = 30
        val weight = 185.0
        val progress = Progress.IN_PROGRESS
        val wgerId = 5
        val set = SetEntity(id, frameworkComponentSetId, reps, weight, progress, wgerId)

        assertEquals(id, set.id)
        assertEquals(frameworkComponentSetId, set.framework_component_set_id)
        assertEquals(reps, set.reps)
        assertEquals(weight, set.weight, 0.01)
        assertEquals(progress, set.status)
        assertEquals(wgerId, set.wger_id)
    }
}
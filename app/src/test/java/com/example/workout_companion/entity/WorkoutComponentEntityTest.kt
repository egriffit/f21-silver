package com.example.workout_companion.entity

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class WorkoutComponentEntityTest {

    @Test
    fun constructorTest() {
        val date = LocalDate.of(2021, 10, 10)
        val componentId = 0
        val id = 0
        val wgerId = 12
        val workoutComponent = WorkoutComponentEntity(id, date, componentId, wgerId)

        assertEquals(date, workoutComponent.workout_date)
        assertEquals(id, workoutComponent.id)
        assertEquals(componentId, workoutComponent.component_id)
        assertEquals(wgerId, workoutComponent.wger_id)
    }
}
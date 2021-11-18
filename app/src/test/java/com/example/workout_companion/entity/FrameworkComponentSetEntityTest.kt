package com.example.workout_companion.entity

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class FrameworkComponentSetEntityTest {

    @Test
    fun constructorTest() {
        val date = LocalDate.of(2021, 10, 10)
        val componentId = 0
        val id = 0
        val componentSet = FrameworkComponentSetEntity(id, date, componentId)

        assertEquals(date, componentSet.workout_date)
        assertEquals(id, componentSet.id)
        assertEquals(componentId, componentSet.component_id)
    }
}
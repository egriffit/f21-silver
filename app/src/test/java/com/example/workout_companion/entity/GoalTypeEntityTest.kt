package com.example.workout_companion.entity

import org.junit.Assert.assertEquals
import org.junit.Test

class GoalTypeEntityTest {

    @Test
    fun constructorTest() {
        val id = 1242
        val name = "Test Goal"
        val goal = GoalTypeEntity(id, name)

        assertEquals(goal.id, id)
        assertEquals(goal.name, name)
    }
}
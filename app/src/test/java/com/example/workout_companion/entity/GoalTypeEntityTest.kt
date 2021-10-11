package com.example.workout_companion.entity

import org.junit.Assert.assertEquals
import org.junit.Test

class GoalTypeEntityTest {

    @Test
    fun constructorTest() {
        val id = 1242
        val name = "Test Goal"
        val cal = 500
        val goal = GoalTypeEntity(id, name, cal)

        assertEquals(goal.id, id)
        assertEquals(goal.goal, name)
        assertEquals(goal.caloric_addition, cal)
    }
}
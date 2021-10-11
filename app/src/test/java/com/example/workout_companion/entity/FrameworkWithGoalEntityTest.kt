package com.example.workout_companion.entity

import org.junit.Assert
import org.junit.Test

class FrameworkWithGoalEntityTest {
    @Test
    fun constructorTest() {
        val id = 1
        val name = "Framework_1"
        val workoutsPerWeek = 1
        val goalId = 1
        val goal = "Gain Strength"

        val frameworkWithGoal =
            FrameworkWithGoalEntity(id, name, workoutsPerWeek, goalId, goal)

        Assert.assertEquals(frameworkWithGoal.id, id)
        Assert.assertEquals(frameworkWithGoal.name, name)
        Assert.assertEquals(frameworkWithGoal.workouts_per_week, workoutsPerWeek)
        Assert.assertEquals(frameworkWithGoal.goal_id, goalId)
        Assert.assertEquals(frameworkWithGoal.goal, goal)
    }
}
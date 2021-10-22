package com.example.workout_companion.utility

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException

@RunWith(AndroidJUnit4::class)
class MainGoalEnumTest : TestCase() {

    @Test
    fun convertToMainGoalTest() {
        val name = MainGoal.BUILD_MUSCLE.name
        val mainGoal = MainGoalConverter.toMainGoal(name)

        assertEquals(MainGoal.BUILD_MUSCLE, mainGoal)
    }

    @Test
    fun convertFromMainGoalTest() {
        val mainGoal = MainGoal.LOSE_WEIGHT
        val name = MainGoalConverter.fromMainGoal(mainGoal)

        assertEquals(MainGoal.LOSE_WEIGHT.name, name)
    }

    @Test(expected = IllegalArgumentException::class)
    fun convertFromBadNameTest() {
        val name = "Not a goal!"
        MainGoalConverter.toMainGoal(name)
    }
}
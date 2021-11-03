package com.example.workout_companion.utility

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.entity.GoalTypeEntity
import com.example.workout_companion.enumeration.ActivityLevel
import com.example.workout_companion.enumeration.Sex
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalorieEstimatorTest {

    @Test
    fun maleEstimateTest() {
        val height = 170.0
        val weight = 80.0
        val age = 20
        val activityLevel = ActivityLevel.MODERATELY_ACTIVE
        val gender = Sex.MALE
        val goal = GoalTypeEntity(1, "Test Goal", 500)

        val estimate = estimateCaloricIntake(
            height = height,
            weight = weight,
            age = age,
            activityLevel = activityLevel,
            gender = gender,
            goal = goal)
        val calculated = 2740 + goal.caloric_addition

        assertEquals(calculated, estimate)
    }

    @Test
    fun femaleEstimateTest() {
        val height = 130.0
        val weight = 60.0
        val age = 30
        val activityLevel = ActivityLevel.VERY_ACTIVE
        val gender = Sex.FEMALE
        val goal = GoalTypeEntity(1, "Test Goal", -500)

        val estimate = estimateCaloricIntake(
            height = height,
            weight = weight,
            age = age,
            activityLevel = activityLevel,
            gender = gender,
            goal = goal)
        val calculated = 1900 + goal.caloric_addition

        assertEquals(calculated, estimate)
    }

    @Test
    fun checkNoNegativeTest() {
        val height = 170.0
        val weight = 80.0
        val age = 20
        val activityLevel = ActivityLevel.MODERATELY_ACTIVE
        val gender = Sex.MALE
        // Use a goal with a ridiculous deficit to check for negative returns
        val goal = GoalTypeEntity(1, "Test Goal", -40000)

        val estimate = estimateCaloricIntake(
            height = height,
            weight = weight,
            age = age,
            activityLevel = activityLevel,
            gender = gender,
            goal = goal)

        assertEquals(0, estimate)
    }
}
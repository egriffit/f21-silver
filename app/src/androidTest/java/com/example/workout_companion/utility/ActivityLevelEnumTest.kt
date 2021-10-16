package com.example.workout_companion.utility

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException

@RunWith(AndroidJUnit4::class)
class ActivityLevelEnumTest : TestCase() {

    @Test
    fun convertToString() {
        val enum = ActivityLevel.MODERATELY_ACTIVE
        assertEquals(ActivityLevelConverter.fromActivityLevel(enum), ActivityLevel.MODERATELY_ACTIVE.scaleFactor)
    }

    @Test
    fun convertToActivityLevel() {
        val scaleFactor = ActivityLevel.SEDENTARY.scaleFactor
        assertEquals(ActivityLevelConverter.toActivityLevel(scaleFactor), ActivityLevel.SEDENTARY)
    }

    @Test(expected = IllegalArgumentException::class)
    fun badConvertToActivityLevel() {
        val badFactor = 0.32
        ActivityLevelConverter.toActivityLevel(badFactor)
    }
}
package com.example.workout_companion.enumeration

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException

@RunWith(AndroidJUnit4::class)
class ExperienceLevelEnumTest {

    @Test
    fun convertToString() {
        val enum = ExperienceLevel.BEGINNER
        TestCase.assertEquals(ExperienceLevelConverter.fromExperienceLevel(enum), "Beginner")
    }

    @Test
    fun convertToExperienceLevel() {
        val name = "Intermediate"
        TestCase.assertEquals(
            ExperienceLevelConverter.toExperienceLevel(name),
            ExperienceLevel.INTERMEDIATE
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun badConvertToExperienceLevel() {
        val badString = "BAD"
        ExperienceLevelConverter.toExperienceLevel(badString)
    }

}
package com.example.workout_companion.utility

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException

@RunWith(AndroidJUnit4::class)
class MuscleGroupEnumTest : TestCase() {

    @Test
    fun convertToString() {
        val enum = MuscleGroup.BACK
        assertEquals(MuscleGroupConverter.fromMuscleGroup(enum), "BACK")
    }

    @Test
    fun convertToMuscleGroup() {
        val absString = "ABS"
        assertEquals(MuscleGroupConverter.toMuscleGroup(absString), MuscleGroup.ABS)
    }

    @Test(expected = IllegalArgumentException::class)
    fun badConvertToMuscleGroup() {
        val badString = "BAD"
        MuscleGroupConverter.toMuscleGroup(badString)
    }
}
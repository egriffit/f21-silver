package com.example.workout_companion.enumeration

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException

@RunWith(AndroidJUnit4::class)
class ProgressEnumTest : TestCase() {

    @Test
    fun convertToString() {
        assertEquals(ProgressConverter.toString(Progress.IN_PROGRESS), "In Progress")
    }

    @Test
    fun convertToProgress() {
        assertEquals(ProgressConverter.toProgress("Not Started"), Progress.NOT_STARTED)
    }

    @Test(expected = IllegalArgumentException::class)
    fun badConvertToString() {
        ProgressConverter.toProgress("Not a progress")
    }
}
package com.example.workout_companion.enumeration

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException

@RunWith(AndroidJUnit4::class)
class SexEnumTest {
    
    @Test
    fun convertToString() {
        val enum = Sex.MALE
        TestCase.assertEquals(SexConverter.fromSex(enum), "Male")
    }

    @Test
    fun convertToSex() {
        val name = "Female"
        TestCase.assertEquals(SexConverter.toSex(name), Sex.FEMALE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun badConvertToSex() {
        val badString = "BAD"
        SexConverter.toSex(badString)
    }

}
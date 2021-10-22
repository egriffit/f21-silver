package com.example.workout_companion.utility

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith

const val DELTA = 0.01

@RunWith(AndroidJUnit4::class)
class LengthConverterTest : TestCase() {

    @Test
    fun inchesToCentimetersTest() {
        val inches = 12.0
        assertEquals(30.48, LengthConverter.toCentimeters(inches), DELTA)
    }

    @Test
    fun heightToCentimetersTest() {
        val feet = 6.0
        val inches = 1.0
        assertEquals(185.42, LengthConverter.toCentimeters(feet, inches), DELTA)
    }

    @Test
    fun centimetersToInchesTest() {
        val cm = 30.48
        val m = LengthConverter.toInches(cm)

        assertEquals(12.0, LengthConverter.toInches(cm), DELTA)
    }

    @Test
    fun centimetersToHeightTest() {
        val cm = 165.0
        val feetInchesPair = LengthConverter.toFeetAndInches(cm)
        assertEquals(5.0, feetInchesPair.first, DELTA)
        assertEquals(4.96, feetInchesPair.second, DELTA)
    }
}
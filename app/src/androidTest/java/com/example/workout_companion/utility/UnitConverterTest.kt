package com.example.workout_companion.utility

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith

const val DELTA = 0.01

@RunWith(AndroidJUnit4::class)
class UnitConverterTest : TestCase() {

    @Test
    fun inchesToCentimetersTest() {
        val inches = 12.0
        assertEquals(30.48, UnitConverter.toCentimeters(inches), DELTA)
    }

    @Test
    fun heightToCentimetersTest() {
        val feet = 6.0
        val inches = 1.0
        assertEquals(185.42, UnitConverter.toCentimeters(feet, inches), DELTA)
    }

    @Test
    fun centimetersToInchesTest() {
        val cm = 30.48
        val m = UnitConverter.toInches(cm)

        assertEquals(12.0, UnitConverter.toInches(cm), DELTA)
    }

    @Test
    fun centimetersToHeightTest() {
        val cm = 165.0
        val feetInchesPair = UnitConverter.toFeetAndInches(cm)
        assertEquals(5.0, feetInchesPair.first, DELTA)
        assertEquals(4.96, feetInchesPair.second, DELTA)
    }

    @Test
    fun poundsToKilogramsTest() {
        val pounds = 135.5
        assertEquals(61.46, UnitConverter.toKilograms(pounds), DELTA)
    }

    @Test
    fun kilogramsToPoundsTest() {
        val kg = 70.5
        assertEquals(155.43, UnitConverter.toPounds(kg), DELTA)
    }
}
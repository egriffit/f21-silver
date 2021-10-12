package com.example.workout_companion.utility

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class HeightTest(){
    @Test
    fun constructorFeetInchesTest(){

        var inches = 6
        var feet: Int = 5
        var totalInches = 66
        var displayFeet = "5ft 6in"

        //metric
        var centimeters: Double = 167.64
        var meters: Double = 1.68
        var displayMeters: String = "1.68m"
        var displayCentimeters: String = "167.64cm"
        var myHeight = Height(5, 6)

        Assert.assertEquals(myHeight.inches, inches)
        Assert.assertEquals(myHeight.feet, feet)
        Assert.assertEquals(myHeight.totalInches, totalInches)
        Assert.assertEquals(myHeight.displayFeet, displayFeet)
//        Assert.assertEquals(myHeight.centimeters, centimeters, .000000001)
//        Assert.assertEquals(myHeight.meters, meters, .000000001)
//        Assert.assertEquals(myHeight.displayMeters, displayMeters)
//        Assert.assertEquals(myHeight.displayCentimeters, displayCentimeters)
    }

    @Test
    fun constructorCentimetersTest(){
        var inches = 6
        var feet: Int = 5
        var totalInches = 66
        var displayFeet = "5ft 6in"

        //metric
        var centimeters: Double = 167.64
        var meters: Double = 1.68
        var displayMeters: String = "1.68m"
        var displayCentimeters: String = "167.64cm"
        var myHeight = Height(167.64)

        Assert.assertEquals( centimeters, myHeight.centimeters, .00001)
        Assert.assertEquals(meters, myHeight.meters, .00001)
        Assert.assertEquals(displayMeters, myHeight.displayMeters, )
        Assert.assertEquals(displayCentimeters, myHeight.displayCentimeters )
//        Assert.assertEquals(myHeight.totalInches, totalInches)
//        Assert.assertEquals(myHeight.feet, feet)
//        Assert.assertEquals(myHeight.inches, inches)
//        Assert.assertEquals(myHeight.displayFeet, displayFeet)
    }
}


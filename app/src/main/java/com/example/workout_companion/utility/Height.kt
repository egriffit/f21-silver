package com.example.workout_companion.utility

import java.math.RoundingMode
import java.text.DecimalFormat

class Height {
    //imperial
    var inches: Int = 0
    var feet: Int = 0
    var totalInches: Int = 0
    var displayFeet = ""

    //metric
    var centimeters: Double = 0.0
    var meters: Double = 0.0
    var displayMeters: String = ""
    var displayCentimeters: String = ""


    constructor(feet: Int, inches: Int)
    {
        this.feet = feet
        this.inches = inches
        this.totalInches = getTotalInches(feet, inches)
        this.displayFeet = "${feet}ft ${inches}in"

        //conversions
    }

    constructor(centimeters: Double){
        this.centimeters = centimeters
        this.meters = cmToMeters(centimeters)
        this.displayMeters = "${meters}m"
        this.displayCentimeters = "${centimeters}cm"
        //conversions
    }

    private fun getTotalInches(feet: Int, inches: Int): Int{
        return 12*feet + inches
    }

    private fun cmToMeters(centimeters: Double): Double{
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(centimeters/100).toDouble()
    }

//    fun convertToCentimeters(inches: Double):  Double{
//        return round(inches * 2.54)
//    }
//
//    fun convertToCentimeters(inches: Int):  Double{
//        return round(inches.toDouble() * 2.54)
//    }
//    fun convertToInches(centimeters: Double):  Double{
//        return round(centimeters / 2.54)
//    }
//
//    fun convertToInches(centimeters: Int):  Double{
//        return round(centimeters.toDouble() / 2.54)
//    }
//
//    fun displayCentimetersInFeet(centimeters: Int, format: String = "abbreviated_units"):  String{
//        var totalInches = convertToInches(centimeters)
//        var feet = round(totalInches / 12).toInt()
//        var inches = totalInches - (feet * 12)
//        var displayFeet: String = ""
//        if(format == "abbreviated") {
//            var displayFeet = "${feet}\' ${inches}\'"
//        }
//        if(format == "abbreviated_units") {
//            var displayFeet = "${feet}ft ${inches}in"
//        }
//        if(format == "full") {
//            var displayFeet = "${feet} feet  ${inches} inches"
//        }
//
//        return displayFeet
//    }
//
//    fun displayCentimetersInFeet(centimeters: Double, format: String = "abbreviated_units"):  String{
//        var totalInches = convertToInches(centimeters)
//        var feet = round(totalInches / 12).toInt()
//        var inches = totalInches - (feet * 12)
//        var displayFeet: String = ""
//        if(format == "abbreviated") {
//            var displayFeet = "${feet}\' ${inches}\'"
//        }
//        if(format == "abbreviated_units") {
//            var displayFeet = "${feet}ft ${inches}in"
//        }
//        if(format == "full") {
//            var displayFeet = "${feet} feet  ${inches} inches"
//        }
//
//        return displayFeet
//    }
//
//    fun displayInchesInFeet(inches: Int, format: String = "abbreviated_units"):  String{
//        var totalInches = inches.toDouble()
//        var feet = round(totalInches / 12).toInt()
//        var inches = totalInches - (feet * 12)
//        var displayFeet: String = ""
//        if(format == "abbreviated") {
//            var displayFeet = "${feet}\' ${inches}\'"
//        }
//        if(format == "abbreviated_units") {
//            var displayFeet = "${feet}ft ${inches}in"
//        }
//        if(format == "full") {
//            var displayFeet = "${feet} feet  ${inches} inches"
//        }
//
//        return displayFeet
//    }
//
//    fun displayInchesInFeet(inches: Double, format: String = "abbreviated_units"):  String{
//        var totalInches = inches.toDouble()
//        var feet = round(totalInches / 12).toInt()
//        var inches = totalInches - (feet * 12)
//        var displayFeet: String = ""
//        if(format == "abbreviated") {
//            var displayFeet = "${feet}\' ${inches}\'"
//        }
//        if(format == "abbreviated_units") {
//            var displayFeet = "${feet}ft ${inches}in"
//        }
//        if(format == "full") {
//            var displayFeet = "${feet} feet  ${inches} inches"
//        }
//
//        return displayFeet
//    }

}
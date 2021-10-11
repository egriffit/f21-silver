package com.example.workout_companion.utility

import kotlin.math.round

class Height {
    var centimeters: Double = 0.0
    var inches: Int = 0
    var displayFeet: String = ""

    constructor(displayInFeet: String, type: String)
    {

    }
    constructor(units: Double, type: String){
        if(type == "cm")
        {
            this.centimeters = units
            this.inches = convertToInches(units).toInt()
            this.displayFeet = displayCentimetersInFeet(units, "abbreviated_units")
        }
        if(type == "in"){
            this.inches = units.toInt()
            this.centimeters = convertToCentimeters(units)
            this.displayFeet = displayCentimetersInFeet(centimeters, "abbreviated_units")
        }

    }

    constructor(units: Int, type: String){
        if(type == "cm")
        {
            this.centimeters = units.toDouble()
            this.inches = convertToInches(units).toInt()
            this.displayFeet = displayCentimetersInFeet(units, "abbreviated_units")
        }
        if(type == "in"){
            this.inches = units
            this.centimeters = convertToCentimeters(units)
            this.displayFeet = displayCentimetersInFeet(centimeters, "abbreviated_units")
        }
    }


    fun convertToCentimeters(inches: Double):  Double{
        return round(inches * 2.54)
    }

    fun convertToCentimeters(inches: Int):  Double{
        return round(inches.toDouble() * 2.54)
    }
    fun convertToInches(centimeters: Double):  Double{
        return round(centimeters / 2.54)
    }

    fun convertToInches(centimeters: Int):  Double{
        return round(centimeters.toDouble() / 2.54)
    }

    fun displayCentimetersInFeet(centimeters: Int, format: String = "abbreviated_units"):  String{
        var totalInches = convertToInches(centimeters)
        var feet = round(totalInches / 12).toInt()
        var inches = totalInches - (feet * 12)
        var displayFeet: String = ""
        if(format == "abbreviated") {
            var displayFeet = "${feet}\' ${inches}\'"
        }
        if(format == "abbreviated_units") {
            var displayFeet = "${feet}ft ${inches}in"
        }
        if(format == "full") {
            var displayFeet = "${feet} feet  ${inches} inches"
        }

        return displayFeet
    }

    fun displayCentimetersInFeet(centimeters: Double, format: String = "abbreviated_units"):  String{
        var totalInches = convertToInches(centimeters)
        var feet = round(totalInches / 12).toInt()
        var inches = totalInches - (feet * 12)
        var displayFeet: String = ""
        if(format == "abbreviated") {
            var displayFeet = "${feet}\' ${inches}\'"
        }
        if(format == "abbreviated_units") {
            var displayFeet = "${feet}ft ${inches}in"
        }
        if(format == "full") {
            var displayFeet = "${feet} feet  ${inches} inches"
        }

        return displayFeet
    }

    fun displayInchesInFeet(inches: Int, format: String = "abbreviated_units"):  String{
        var totalInches = inches.toDouble()
        var feet = round(totalInches / 12).toInt()
        var inches = totalInches - (feet * 12)
        var displayFeet: String = ""
        if(format == "abbreviated") {
            var displayFeet = "${feet}\' ${inches}\'"
        }
        if(format == "abbreviated_units") {
            var displayFeet = "${feet}ft ${inches}in"
        }
        if(format == "full") {
            var displayFeet = "${feet} feet  ${inches} inches"
        }

        return displayFeet
    }

    fun displayInchesInFeet(inches: Double, format: String = "abbreviated_units"):  String{
        var totalInches = inches.toDouble()
        var feet = round(totalInches / 12).toInt()
        var inches = totalInches - (feet * 12)
        var displayFeet: String = ""
        if(format == "abbreviated") {
            var displayFeet = "${feet}\' ${inches}\'"
        }
        if(format == "abbreviated_units") {
            var displayFeet = "${feet}ft ${inches}in"
        }
        if(format == "full") {
            var displayFeet = "${feet} feet  ${inches} inches"
        }

        return displayFeet
    }

}
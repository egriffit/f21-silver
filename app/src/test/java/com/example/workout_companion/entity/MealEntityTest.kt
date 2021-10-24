package com.example.workout_companion.entity

import org.junit.Assert.*

import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate

class MealEntityTest{
    @Test
    fun constructorTest() {
        val id = 1
        val type = "breakfast"
        val calories = 500.0
        val protein = 20.1
        val carbohydrate = 40.0
        val fat = 15.0
        val date = LocalDate.now()
        val meal =
            MealEntity(id, type, calories, carbohydrate, protein, fat, date)

        Assert.assertEquals(meal.id, id)
        Assert.assertEquals(meal.type, type)
        Assert.assertEquals(meal.calories, calories, .0000001)
        Assert.assertEquals(meal.calories, calories,.0000001)
        Assert.assertEquals(meal.carbohydrates, carbohydrate, .0000001)
        Assert.assertEquals(meal.protein, protein, .0000001)
        Assert.assertEquals(meal.fat, fat, .0000001)
        Assert.assertEquals(meal.date, date)
    }
}
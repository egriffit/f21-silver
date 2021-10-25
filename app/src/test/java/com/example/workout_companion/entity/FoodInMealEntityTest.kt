package com.example.workout_companion.entity

import org.junit.Assert.*

import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate

class FoodInMealEntityTest {
    @Test
    fun constructorTest() {
        val meal_id = 1
        val food_id = 25
        val servings = 1.5

        val foodInMeal =
            FoodInMealEntity(meal_id, food_id, servings)

        Assert.assertEquals(foodInMeal.meal_id, meal_id)
        Assert.assertEquals(foodInMeal.food_id, food_id)
        Assert.assertEquals(foodInMeal.servings, servings, .0000001)
    }
}
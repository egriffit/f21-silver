package com.example.workout_companion.entity

import org.junit.Assert.*

import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

class FoodTypeEntityTest{
    @Test
    fun constructorTest() {
        val id = 1
        val name = "carrot"
        val edamamId = "food_ai215e5b85pdh5ajd4aafa3w2zm8"
        val calories = 80.0
        val servingSize = 100.0
        val protein = 1.3
        val carbohydrate = 98.0
        val fat = 0.7

        val food =
            FoodTypeEntity(id, name, edamamId, servingSize, calories, carbohydrate, protein, fat)

        Assert.assertEquals(food.id, id)
        Assert.assertEquals(food.name, name)
        Assert.assertEquals(food.edamam_id, edamamId)
        Assert.assertEquals(food.serving_size, servingSize, .0000001)
        Assert.assertEquals(food.calories, calories,.0000001)
        Assert.assertEquals(food.carbohydrates, carbohydrate, .0000001)
        Assert.assertEquals(food.protein, protein, .0000001)
        Assert.assertEquals(food.fat, fat, .0000001)
    }
}
package com.example.workout_companion.entity

import org.junit.Assert
import org.junit.Test

class GoalAndNutritionPlanTypeEntityTest {
    @Test
    fun constructorTest(){
        val id = 1
        val goalId = 1
        val calorie = 2500.0
        val carbohydrate = 0.40
        val protein = 0.35
        val fat =  0.25

        val nutritionPlanType = NutritionPlanTypeEntity(id, goalId, calorie, carbohydrate, protein, fat)

        Assert.assertEquals(nutritionPlanType.id, id)
        Assert.assertEquals(nutritionPlanType.goal_id, goalId)
        Assert.assertEquals(nutritionPlanType.calorie, calorie, .0001)
        Assert.assertEquals(nutritionPlanType.carbohydrate, carbohydrate, .0001)
        Assert.assertEquals(nutritionPlanType.protein, protein, .0001)
        Assert.assertEquals(nutritionPlanType.fat, fat, .0001)

    }
}
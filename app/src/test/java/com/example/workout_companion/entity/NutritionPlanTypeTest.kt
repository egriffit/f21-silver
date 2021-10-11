package com.example.workout_companion.entity

import org.junit.Assert
import org.junit.Test


class NutritionPlanTypeTest {
    @Test
    fun constructorTest() {
        val id = 1
        val goalId = 1242
        val calorie = 2500.0
        val carbohydrate = 0.40
        val protein = 0.35
        val fat =  0.25
        val name = "Test Goal"
        val cal = 500
        val goal = GoalTypeEntity(goalId, name, cal)
        val nutritionPlanType = NutritionPlanTypeEntity(id, goalId, calorie, carbohydrate, protein, fat)
        val goalAndNutritionPlanType = GoalAndNutritionPlanTypeEntity(nutritionPlanType, goal)
        Assert.assertEquals(goalAndNutritionPlanType.nutritionPlanType.id, id)
        Assert.assertEquals(goalAndNutritionPlanType.nutritionPlanType.goal_id, goalId)
        Assert.assertEquals(goalAndNutritionPlanType.nutritionPlanType.calorie, calorie, .0001)
        Assert.assertEquals(goalAndNutritionPlanType.nutritionPlanType.carbohydrate, carbohydrate, .0001)
        Assert.assertEquals(goalAndNutritionPlanType.nutritionPlanType.protein, protein, .0001)
        Assert.assertEquals(goalAndNutritionPlanType.nutritionPlanType.fat, fat, .0001)
        Assert.assertEquals(goalAndNutritionPlanType.goalType.id, goalId)
        Assert.assertEquals(goalAndNutritionPlanType.goalType.goal, name)
        Assert.assertEquals(goalAndNutritionPlanType.goalType.caloric_addition, cal)
    }
}
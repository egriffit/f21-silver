package com.example.workout_companion.entity

import org.junit.Assert
import org.junit.Test

class CurrentNutritionPlanAndFrameworkEntityTest {
    @Test
    fun constructorTest() {
        val id = 1
        val nutritionPlanTypeID = 1
        val frameworkTypeId = 1


        val calorie = 2500.0
        val carbohydrate = 0.40
        val protein = 0.35
        val fat =  0.25
        val goal = "Gain Strength"
        val name = "Framework_1"
        val workoutsPerWeek = 1
        val goalId = 1

        val frameworkWithGoal =
            FrameworkWithGoalEntity(frameworkTypeId, name, workoutsPerWeek, goalId, goal)
        val currentUserGoal =
            CurrentUserGoalEntity(id, nutritionPlanTypeID, frameworkTypeId)
        val nutritionPlanType = NutritionPlanTypeEntity(nutritionPlanTypeID, goalId, calorie, carbohydrate, protein, fat)
        val currentNutritionPlanAndFramework = CurrentNutritionPlanAndFrameworkEntity(currentUserGoal, nutritionPlanType, frameworkWithGoal)

        Assert.assertEquals(currentNutritionPlanAndFramework.FrameWorkWIthGoalEntity.id, frameworkTypeId)
        Assert.assertEquals(currentNutritionPlanAndFramework.FrameWorkWIthGoalEntity.name, name)
        Assert.assertEquals(currentNutritionPlanAndFramework.FrameWorkWIthGoalEntity.workouts_per_week, workoutsPerWeek)
        Assert.assertEquals(currentNutritionPlanAndFramework.FrameWorkWIthGoalEntity.goal_id, goalId)
        Assert.assertEquals(currentNutritionPlanAndFramework.FrameWorkWIthGoalEntity.goal, goal)
        Assert.assertEquals(currentNutritionPlanAndFramework.nutritionPlanType.id, nutritionPlanTypeID)
        Assert.assertEquals(currentNutritionPlanAndFramework.nutritionPlanType.goal_id, goalId)
        Assert.assertEquals(currentNutritionPlanAndFramework.nutritionPlanType.calorie, calorie, .0001)
        Assert.assertEquals(currentNutritionPlanAndFramework.nutritionPlanType.carbohydrate, carbohydrate, .0001)
        Assert.assertEquals(currentNutritionPlanAndFramework.nutritionPlanType.protein, protein, .0001)
        Assert.assertEquals(currentNutritionPlanAndFramework.nutritionPlanType.fat, fat, .0001)
        Assert.assertEquals(currentNutritionPlanAndFramework.currentUserGoalEntity.framework_type_id, frameworkTypeId)
        Assert.assertEquals(currentNutritionPlanAndFramework.currentUserGoalEntity.nutrition_plan_type_id, nutritionPlanTypeID)
        Assert.assertEquals(currentNutritionPlanAndFramework.currentUserGoalEntity.id, id)
    }
}
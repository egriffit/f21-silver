package com.example.workout_companion.entity

import org.junit.Assert
import org.junit.Test

class CurrentUserGoalEntityTest {
    @Test
    fun constructorTest() {
        val id = 1
        val nutritionPlanTypeID = 1
        val frameworkTypeId = 1

        val currentUserGoal =
            CurrentUserGoalEntity(id, nutritionPlanTypeID, frameworkTypeId)

        Assert.assertEquals(currentUserGoal.id, id)
        Assert.assertEquals(currentUserGoal.framework_type_id, frameworkTypeId)
        Assert.assertEquals(currentUserGoal.nutrition_plan_type_id, nutritionPlanTypeID)
    }
}
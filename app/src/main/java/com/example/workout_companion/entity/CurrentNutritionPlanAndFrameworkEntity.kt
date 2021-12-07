package com.example.workout_companion.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * An entity joining the current_user_goal, nutrition_plan_type, and frameworkWithGoal view in the database
 *
 * @property currentUserGoalEntity      Entity for the current_user_goal table
 * @property nutritionPlanType          Entity for the nutrition_plan_type table
 * @property FrameWorkWIthGoalEntity    Entity for the FrameworkWithGoalEntity view
 */
data class CurrentNutritionPlanAndFrameworkEntity (
    @Embedded val currentUserGoalEntity: CurrentUserGoalEntity,
    @Relation(
        parentColumn = "nutrition_plan_type_id",
        entityColumn = "id"
    )
    val nutritionPlanType: NutritionPlanTypeEntity,
    @Relation(
        parentColumn = "framework_type_id",
        entityColumn = "id"
    )
    val FrameWorkWIthGoalEntity: FrameworkWithGoalEntity
)
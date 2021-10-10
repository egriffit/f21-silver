package com.example.workout_companion.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

/**
 * A relation joining the nutrition_plan_type and goal_type tables
 *
 * @property nutritionPlanType   Entity for the nutrition_plan_type table
 * @property goalType            Entity for the goal_type table
 */
@Entity
data class GoalAndNutritionPlanTypeEntity (

    @Embedded val nutritionPlanType: NutritionPlanTypeEntity,
    @Relation(
        parentColumn = "goal_id",
        entityColumn = "id"
    )
    val goalType: GoalTypeEntity
)
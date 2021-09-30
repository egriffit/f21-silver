package com.example.workout_companion.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class GoalAndNutritionPlanTypeEntity (

    @Embedded val nutritionPlanType: NutritionPlanTypeEntity,
    @Relation(
        parentColumn = "goal_id",
        entityColumn = "id"
    )
    val goalType: GoalTypeEntity
)
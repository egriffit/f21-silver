package com.example.workout_companion.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.workout_companion.mock.entity.GoalTypeEntity

@Entity
data class GoalAndNutritionPlanTypeEntity (

    @Embedded val nutritionPlanType: NutritionPlanTypeEntity,
    @Relation(
        parentColumn = "goal_id",
        entityColumn = "id"
    )
    val goalType: GoalTypeEntity
)
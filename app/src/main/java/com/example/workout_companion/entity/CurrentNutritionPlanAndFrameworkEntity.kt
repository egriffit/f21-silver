package com.example.workout_companion.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.workout_companion.mock.entity.FrameworkWithGoalEntity


data class CurrentNutritionPlanAndFrameworkEntity (
    @Embedded val currentUserGoalEntity: CurrentUserGoalEntity,
    @Relation(
        parentColumn = "nutrition_plan_type_id",
        entityColumn = "id"
    )
    val nutritionplanType: NutritionPlanTypeEntity,
    @Relation(
        parentColumn = "framework_type_id",
        entityColumn = "id"
    )
    val FrameWorkWIthGoalEntity: FrameworkWithGoalEntity
    )
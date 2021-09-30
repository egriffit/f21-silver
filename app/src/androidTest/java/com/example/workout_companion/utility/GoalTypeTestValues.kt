package com.example.workout_companion.utility

import com.example.workout_companion.entity.GoalTypeEntity

fun getSingleGoalTestValue(goalId: Int) : GoalTypeEntity {
    return GoalTypeEntity(goalId, "Goal $goalId")
}

fun getGoalTestValues(numGoals: Int) : List<GoalTypeEntity> {
    var goalTypes: MutableList<GoalTypeEntity> = mutableListOf<GoalTypeEntity>()

    for (i in 1..numGoals) {
        goalTypes.add(GoalTypeEntity(i, "Goal $i"))
    }

    return goalTypes
}

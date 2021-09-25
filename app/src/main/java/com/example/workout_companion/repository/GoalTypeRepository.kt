package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.GoalTypeDao
import com.example.workout_companion.entity.GoalTypeEntity

class GoalTypeRepository(private val goalTypeDao: GoalTypeDao) {

    val allGoals: LiveData<List<GoalTypeEntity>> = goalTypeDao.getAllGoals()

    suspend fun addGoal(goalType: GoalTypeEntity) {
        goalTypeDao.addGoal(goalType)
    }
}
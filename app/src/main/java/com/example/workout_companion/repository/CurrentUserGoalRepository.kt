package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.CurrentUserGoalDao
import com.example.workout_companion.entity.CurrentNutritionPlanAndFrameworkEntity

/**
 * CurrentUserGoal Repository class that abstracts the methods in the Current User Goal DAO
 *
 */
class CurrentUserGoalRepository (private val currentUserGoalDao: CurrentUserGoalDao) {

    /**
     * Retrieves a the current user goals from the current_user_goal table
     *
     * @return CurrentNutritionPlanAndFrameworkEntity
     */
    fun getCurrentUserGoals(): LiveData<CurrentNutritionPlanAndFrameworkEntity>{
        return currentUserGoalDao.getCurrentGoals()
    }
}
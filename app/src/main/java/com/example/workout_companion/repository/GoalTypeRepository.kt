package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import androidx.room.Transaction
import com.example.workout_companion.dao.GoalTypeDao
import com.example.workout_companion.entity.GoalTypeEntity

/**
 * A repository for accessing the GoalTypeEntity table
 *
 * @see GoalTypeEntity
 *
 * @property goalTypeDao    the Database Access Object for the GoalTypeEntity.
 */
class GoalTypeRepository(private val goalTypeDao: GoalTypeDao) {

    /**
     * The LiveData object containing all goals in the database.
     */
    val allGoals: LiveData<List<GoalTypeEntity>> = goalTypeDao.getAllGoals()

    /**
     * Adds a goal to the database.
     *
     * @property goalType   the goal to add to the database.
     */
    @Transaction
    suspend fun addGoal(goalType: GoalTypeEntity) {
        if(goalTypeDao.getCountByGoal(goalType.goal) > 0){
            goalTypeDao.updateGoal(goalType)
        }else {
            goalTypeDao.addGoal(goalType)
        }
    }

    /**
     * Adds a goal to the database.
     *
     * @property goalType   the goal to add to the database.
     */
    @Transaction
    suspend fun addGoal(goals: List<GoalTypeEntity>) {
        goals.forEach{ goal ->
            if(goalTypeDao.getCountByGoal(goal.goal) > 0){
                goalTypeDao.updateGoal(goal)
            }else {
                goalTypeDao.addGoal(goal)
            }
        }
    }
}
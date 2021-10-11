package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.UserWeightHistoryDao
import com.example.workout_companion.entity.UserWeightHistoryEntity

/**
 * UserWeightHistory Repository class that abstracts the methods in the UserWeightHistory DAO
 *
 */
class UserWeightHistoryRepository (private val userWeightHistoryDao: UserWeightHistoryDao) {


    /**
     * Retrieves a the user weight history from the user_weight_history table
     *
     * @return UserWeightHistoryEntity
     */
    val getUserWeightHistory: LiveData<UserWeightHistoryEntity> = userWeightHistoryDao.getUserWeightHistory()

}
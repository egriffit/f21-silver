package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.UserWithGoal
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.repository.UserWithGoalRepository

/**
 * ViewModel for getting a user and their attached goal
 *
 * @property application The current application
 */
class UserWithGoalViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * The value of the user and their goal
     */
    val userWithGoal: LiveData<UserWithGoal>

    init {
        val dao = WCDatabase.getInstance(application).userWithGoalDao()
        val repository = UserWithGoalRepository(dao)
        userWithGoal = repository.userWithGoal
    }
}
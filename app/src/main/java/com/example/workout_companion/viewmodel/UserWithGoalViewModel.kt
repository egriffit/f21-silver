package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.UserWithGoal
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.repository.UserRepository
import com.example.workout_companion.repository.UserWithGoalRepository

class UserWithGoalViewModel(application: Application) : AndroidViewModel(application) {

    val userWithGoal: LiveData<UserWithGoal>

    private val repository: UserWithGoalRepository

    init {
        val dao = WCDatabase.getInstance(application).userWithGoalDao()
        repository = UserWithGoalRepository(dao)
        userWithGoal = repository.userWithGoal
    }
}
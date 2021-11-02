package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.GoalTypeEntity
import com.example.workout_companion.repository.GoalTypeRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 * A View Model for the GoalTypeEntity table
 *
 * @see GoalTypeEntity
 * @see ViewModel
 *
 * @property repository the repository that handles access to the database.
 */
class GoalTypeViewModel(application: Application): AndroidViewModel(application) {

    /**
     * A LiveData object containing all goals in the database.
     */
    val allGoals: LiveData<List<GoalTypeEntity>>

    private val repository: GoalTypeRepository

    init {
        val goalDao = WCDatabase.getInstance(application).goalTypeDao()
        repository = GoalTypeRepository(goalDao)
        allGoals = repository.allGoals
    }

    /**
     * Adds a goal to the database.
     *
     * @property goal   the goal to be added to the database.
     */
    fun addGoal(goal: GoalTypeEntity) = viewModelScope.launch {
        repository.addGoal(goal)
    }
}
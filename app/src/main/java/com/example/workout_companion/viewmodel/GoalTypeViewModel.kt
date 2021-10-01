package com.example.workout_companion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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
class GoalTypeViewModel(private val repository: GoalTypeRepository) : ViewModel() {

    /**
     * A LiveData object containing all goals in the database.
     */
    val allGoals: LiveData<List<GoalTypeEntity>> = repository.allGoals

    /**
     * Adds a goal to the database.
     *
     * @property goal   the goal to be added to the database.
     */
    fun addGoal(goal: GoalTypeEntity) = viewModelScope.launch {
        repository.addGoal(goal)
    }
}

/**
 * A factory that creates GoalTypeViewModels
 *
 * @see GoalTypeViewModel
 * @see ViewModelProvider.Factory
 *
 * @property repository the repository that controls access to the database
 */
class GoalTypeViewModelFactory(private val repository: GoalTypeRepository) : ViewModelProvider.Factory  {

    /**
     * Creates a GoalTypeViewModel
     *
     * @param T the ViewModel type to create.
     * @property modelClass the ViewModel class to convert.
     *
     * @return a GoalTypeViewModel instantiated with [repository]
     *
     * @throws IllegalArgumentException when [modelClass] cannot be converted to [T]
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoalTypeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoalTypeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
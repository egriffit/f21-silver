package com.example.workout_companion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.workout_companion.entity.GoalTypeEntity
import com.example.workout_companion.repository.GoalTypeRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class GoalTypeViewModel(private val repository: GoalTypeRepository) : ViewModel() {

    val allGoals: LiveData<List<GoalTypeEntity>> = repository.allGoals

    fun addGoal(goal: GoalTypeEntity) = viewModelScope.launch {
        repository.addGoal(goal)
    }
}

class GoalTypeViewModelFactory(private val repository: GoalTypeRepository) : ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoalTypeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoalTypeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
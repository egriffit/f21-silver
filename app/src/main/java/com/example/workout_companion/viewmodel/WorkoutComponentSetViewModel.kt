package com.example.workout_companion.viewmodel

import com.example.workout_companion.entity.WorkoutComponentSetEntity
import com.example.workout_companion.repository.WorkoutComponentSetRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.WorkoutComponentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * View model for the workout_component table in the database
 *
 * @param application The application.
 */
class WorkoutComponentSetViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * Variable to store workout components for day
     */
    val workoutComponentSets = MutableLiveData<List<WorkoutComponentSetEntity>>()

    /**
     * The repository for accessing the database
     */
    private val repository: WorkoutComponentSetRepository

    init {
        val dao = WCDatabase.getInstance(application.applicationContext).workoutComponentSetDao()
        repository = WorkoutComponentSetRepository(dao)
    }

    /**
     * Store a component set in the database
     *
     * @param set: WorkoutComponentSet
     *
     */
    suspend fun getSetsForComponent(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            workoutComponentSets.postValue(repository.getSetsForComponent(id))
        }
    }

    /**
     * Store a component set in the database
     *
     * @param set: WorkoutComponentSet
     *
     */
    suspend fun addSet(set: WorkoutComponentSetEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addSet(set)
        }
    }

    /**
     * Updates a framework component set in the database.
     *
     * @param set The set to update in the database
     */
    suspend fun updateWorkoutComponent(set: WorkoutComponentSetEntity) {
        repository.updateSet(set)
    }

    /**
     * Remove a framework component set from the database.
     *
     * @param set The set to update in the database
     */
    suspend fun deleteWorkoutComponent(set: WorkoutComponentSetEntity) {
        repository.removeSet(set)
    }
}
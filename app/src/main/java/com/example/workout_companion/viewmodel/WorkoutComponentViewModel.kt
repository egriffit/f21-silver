package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.WorkoutComponentEntity
import com.example.workout_companion.repository.WorkoutComponentRepository
import java.time.LocalDate

/**
 * View model for the workout_component table in the database
 *
 * @param application The application.
 */
class WorkoutComponentViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * The repository for accessing the database
     */
    private val repository: WorkoutComponentRepository

    init {
        val dao = WCDatabase.getInstance(application.applicationContext).frameworkComponentSetDao()
        repository = WorkoutComponentRepository(dao)
    }

    /**
     * Gets all workout components for the given date
     *
     * @param date The date to search with.
     *
     * @return A list of all workout components.
     */
    suspend fun getWorkoutComponentsForDate(date: LocalDate): List<WorkoutComponentEntity> {
        return repository.getWorkoutComponentsForDate(date)
    }

    /**
     * Adds a framework component set to the database
     *
     * @param component The component to add to the database.
     */
    suspend fun addWorkoutComponent(component: WorkoutComponentEntity) {
        repository.addWorkoutComponent(component)
    }

    /**
     * Updates a framework component set in the database.
     *
     * @param component The component to update in the database.
     */
    suspend fun updateWorkoutComponent(component: WorkoutComponentEntity) {
        repository.updateWorkoutComponent(component)
    }

    /**
     * Delete a framework component set from the database.
     *
     * @param component The component to remove from the database.
     */
    suspend fun deleteWorkoutComponent(component: WorkoutComponentEntity) {
        repository.deleteWorkoutComponent(component)
    }
}
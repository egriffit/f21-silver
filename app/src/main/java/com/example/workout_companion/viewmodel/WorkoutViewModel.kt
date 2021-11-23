package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.WorkoutEntity
import com.example.workout_companion.repository.WorkoutRepository
import java.time.LocalDate

/**
 * ViewModel for the workout table in the database
 *
 * @property application The application
 */
class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * The workouts in the database
     */
    val workouts: LiveData<List<WorkoutEntity>>

    /**
     * The repository for database access
     */
    private val repository: WorkoutRepository

    init {
        val db = WCDatabase.getInstance(application)
        repository = WorkoutRepository(db.workoutDao(), db.workoutComponentDao(), db.workoutComponentSetDao())
        workouts = repository.workouts
    }

    /**
     * Get a workout from the database on a specific date
     *
     * @property date The date of the workout
     *
     * @return The workout from the specific date
     */
    fun getWorkoutOnDate(date: LocalDate): LiveData<WorkoutEntity> {
        return repository.getWorkoutOnDate(date)
    }

    /**
     * Add a workout to the database
     *
     * @property workout The workout to add
     */
    /*suspend fun addWorkout(workout: WorkoutEntity) {
        repository.createWorkout(workout)
    }*/

    /**
     * Update a workout in the database
     *
     * @property workout The workout to update
     */
    suspend fun updateWorkout(workout: WorkoutEntity) {
        repository.updateWorkout(workout)
    }

    /**
     * Delete a workout from the database
     *
     * @property workout The workout to delete
     */
    suspend fun deleteWorkout(workout: WorkoutEntity) {
        repository.deleteWorkout(workout)
    }
}
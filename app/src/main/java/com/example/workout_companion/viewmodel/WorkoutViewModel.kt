package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.workout_companion.dao.FrameworkDayWithComponents
import com.example.workout_companion.dao.WorkoutWithComponents
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.WorkoutEntity
import com.example.workout_companion.repository.WorkoutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
     * @param date The date of the workout
     *
     * @return The workout from the specific date
     */
    fun getWorkoutOnDate(date: LocalDate): LiveData<WorkoutEntity> {
        return repository.getWorkoutOnDate(date)
    }

    fun getTodaysWorkoutWithComponents(): LiveData<WorkoutWithComponents> {
        return repository.getWorkoutWithComponents(LocalDate.now())
    }

    /**
     * Add a workout, its components, and its sets to the database, using the framework day as
     * a template for its construction
     *
     * @param frameworkDayWithComponents The framework day that serves as a template for the workout
     */
    fun createWorkout(frameworkDayWithComponents: FrameworkDayWithComponents) = viewModelScope.launch(Dispatchers.IO) {
        repository.createWorkout(frameworkDayWithComponents)
    }

    /**
     * Update a workout in the database
     *
     * @param workout The workout to update
     */
    suspend fun updateWorkout(workout: WorkoutEntity) {
        repository.updateWorkout(workout)
    }

    /**
     * Delete a workout from the database
     *
     * @param workout The workout to delete
     */
    suspend fun deleteWorkout(workout: WorkoutEntity) {
        repository.deleteWorkout(workout)
    }
}
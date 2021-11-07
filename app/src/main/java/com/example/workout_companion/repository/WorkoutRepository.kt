package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.WorkoutDao
import com.example.workout_companion.entity.WorkoutEntity
import java.time.LocalDate

/**
 * Repository for access to workouts
 *
 * @property workoutDao Dao for accessing the workout tables in the database
 */
class WorkoutRepository(private val workoutDao: WorkoutDao) {

    /**
     * All workouts in the database
     */
    val workouts = workoutDao.getAllWorkouts()

    /**
     * Gets the workout on a specific date
     *
     * @property date The date for the workout
     *
     * @return The workout
     */
    fun getWorkoutOnDate(date: LocalDate): LiveData<WorkoutEntity> {
        return workoutDao.getWorkoutOnDate(date)
    }

    /**
     * Adds a workout to the database
     *
     * @property workout The workout to add
     */
    suspend fun addWorkout(workout: WorkoutEntity) {
        workoutDao.addWorkout(workout)
    }

    /**
     * Updates a workout in the database
     *
     * @property workout The workout to update
     */
    suspend fun updateWorkout(workout: WorkoutEntity) {
        workoutDao.updateWorkout(workout)
    }

    /**
     * Deletes a workout from the database
     *
     * @property workout The workout to delete
     */
    suspend fun deleteWorkout(workout: WorkoutEntity) {
        workoutDao.deleteWorkout(workout)
    }
}
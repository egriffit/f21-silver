package com.example.workout_companion.repository

import com.example.workout_companion.dao.WorkoutComponentDao
import com.example.workout_companion.entity.WorkoutComponentEntity
import java.time.LocalDate

/**
 * Repository for accessing the workout_component table in the database
 *
 * @property dao The database access object.
 */
class WorkoutComponentRepository(private val dao: WorkoutComponentDao) {

    /**
     * Gets all workout components for the given date in the database
     *
     * @param date The date to search with.
     *
     * @return A list of the framework component sets.
     */
    suspend fun getWorkoutComponentsForDate(date: LocalDate): List<WorkoutComponentEntity> {
        return dao.getWorkoutComponentsForDate(date)
    }

    /**
     * Adds a workout component to the database
     *
     * @param set The set to add to the database.
     */
    suspend fun addWorkoutComponent(set: WorkoutComponentEntity) {
        dao.addWorkoutComponent(set)
    }

    /**
     * Updates a workout component in the database.
     *
     * @param set The set to update in the database.
     */
    suspend fun updateWorkoutComponent(set: WorkoutComponentEntity) {
        dao.updateWorkoutComponent(set)
    }

    /**
     * Deletes a workout component from the database.
     *
     * @param set The set to remove from the database.
     */
    suspend fun deleteWorkoutComponent(set: WorkoutComponentEntity) {
        dao.deleteWorkoutComponent(set)
    }
}
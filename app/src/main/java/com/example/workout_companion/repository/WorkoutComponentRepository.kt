package com.example.workout_companion.repository

import androidx.room.Transaction
import com.example.workout_companion.dao.WorkoutComponentDao
import com.example.workout_companion.entity.FrameworkComponentEntity
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
     * Gets all workout components for the given date in the database
     *
     * @param date The date to search with.
     *
     * @return A list of the framework component sets.
     */
    suspend fun count(workoutComponent: WorkoutComponentEntity): Int{
        return dao.count(workoutComponent.workout_date, workoutComponent.component_id)
    }

    /**
     * Adds a workout component to the database
     *
     * @param set The set to add to the database.
     */
    @Transaction
    suspend fun addWorkoutComponent(set: WorkoutComponentEntity) {
        if(count(set) > 0){
            dao.updateWorkoutComponent(set)
        }else{
            dao.addWorkoutComponent(set)
        }
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
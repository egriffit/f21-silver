package com.example.workout_companion.dao

import androidx.room.*
import com.example.workout_companion.entity.WorkoutComponentEntity
import java.time.LocalDate

/**
 * The Database Access Object for the framework_component_set table
 */
@Dao
interface WorkoutComponentDao {

    /**
     * Gets all workout components for the given date
     *
     * @param date The date to search with.
     *
     * @return A list of framework component sets
     */
    @Query("SELECT * FROM workout_component WHERE workout_date=:date")
    suspend fun getWorkoutComponentsForDate(date: LocalDate): List<WorkoutComponentEntity>

    /**
     * Adds a workout component to the database
     *
     * @param workoutComponent The set to add.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorkoutComponent(workoutComponent: WorkoutComponentEntity)

    /**
     * Updates a workout component in the database
     *
     * @param workoutComponent The set to update.
     */
    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateWorkoutComponent(workoutComponent: WorkoutComponentEntity)

    /**
     * Deletes a workout component from the database
     *
     * @param workoutComponent The set to delete.
     */
    @Delete
    suspend fun deleteWorkoutComponent(workoutComponent: WorkoutComponentEntity)
}
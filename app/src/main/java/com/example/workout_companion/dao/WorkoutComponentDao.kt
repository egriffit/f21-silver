package com.example.workout_companion.dao

import androidx.room.*
import com.example.workout_companion.entity.WorkoutComponentEntity
import java.time.LocalDate

/**
 * The Database Access Object for the workout_component table
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
     * Gets the component given the id
     *
     * @param id The id of the component
     *
     * @return The component, or null if it does not exist
     */
    @Query("SELECT * FROM workout_component WHERE id=:id")
    fun getWorkoutComponentById(id: Int): WorkoutComponentEntity?

    /**
     * Adds a workout component to the database
     *
     * @param workoutComponent The component to add.
     *
     * @return The primary key of the new component
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorkoutComponent(workoutComponent: WorkoutComponentEntity): Long

    /**
     * Updates a workout component in the database
     *
     * @param workoutComponent The component to update.
     */
    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateWorkoutComponent(workoutComponent: WorkoutComponentEntity)

    /**
     * Deletes a workout component from the database
     *
     * @param workoutComponent The component to delete.
     */
    @Delete
    suspend fun deleteWorkoutComponent(workoutComponent: WorkoutComponentEntity)
}
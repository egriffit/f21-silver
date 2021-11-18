package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.WorkoutEntity
import java.time.LocalDate

/**
 * The Database Access Object of the workout table.
 */
@Dao
interface WorkoutDao {

    /**
     * Gets all workouts in the database
     *
     * @return A LiveData list of the workouts
     */
    @Query("SELECT * from workout")
    fun getAllWorkouts(): LiveData<List<WorkoutEntity>>

    /**
     * Gets the workout on a specific date
     *
     * @property date The date for the workout
     *
     * @return A LiveData container of the workout
     */
    @Query("SELECT * FROM workout WHERE date=:date")
    fun getWorkoutOnDate(date: LocalDate): LiveData<WorkoutEntity>

    /**
     * Adds a workout to the database
     *
     * @property workout The workout to add
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorkout(workout: WorkoutEntity)

    /**
     * Updates a workout in the database
     *
     * @property workout The workout to update
     */
    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)

    /**
     * Delete a workout from the database
     *
     * @property workout The workout to delete
     */
    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity)
}
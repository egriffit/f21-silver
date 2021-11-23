package com.example.workout_companion.dao

import androidx.room.*
import com.example.workout_companion.entity.WorkoutComponentSetEntity

/**
 * Database Access Object for workout component sets. The primary use case for this interface is
 * to create, update, and remove sets. Accessing sets should be done through a [WorkoutDao].
 */
@Dao
interface WorkoutComponentSetDao {

    /**
     * Gets all sets in the database
     *
     * @return A list of all sets in the database.
     */
    @Query("SELECT * FROM workout_component_set")
    fun getAllSets(): List<WorkoutComponentSetEntity>

    /**
     * Adds a set to the database
     *
     * @param set The set to add
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSet(set: WorkoutComponentSetEntity)

    /**
     * Updates a set in the database
     *
     * @param set The set to update
     */
    @Update
    suspend fun updateSet(set: WorkoutComponentSetEntity)

    /**
     * Removes a set from the database
     *
     * @param set The set to remove
     */
    @Delete
    suspend fun removeSet(set: WorkoutComponentSetEntity)
}
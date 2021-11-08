package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.FrameworkComponentSetEntity
import java.time.LocalDate

/**
 * The Database Access Object for the framework_component_set table
 */
@Dao
interface FrameworkComponentSetDao {

    /**
     * Gets all framework component sets for the given date
     *
     * @param date The date to search with.
     *
     * @return A list of framework component sets
     */
    @Query("SELECT * FROM framework_component_set WHERE workout_date=:date")
    suspend fun getFrameworkComponentSetsForDate(date: LocalDate): List<FrameworkComponentSetEntity>

    /**
     * Adds a framework component set to the database
     *
     * @param frameworkComponentSet The set to add.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFrameworkComponentSet(frameworkComponentSet: FrameworkComponentSetEntity)

    /**
     * Updates a framework component set in the database
     *
     * @param frameworkComponentSet The set to update.
     */
    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateFrameworkComponentSet(frameworkComponentSet: FrameworkComponentSetEntity)

    /**
     * Deletes a framework component set from the database
     *
     * @param frameworkComponentSet The set to delete.
     */
    @Delete
    suspend fun deleteFrameworkComponentSet(frameworkComponentSet: FrameworkComponentSetEntity)
}
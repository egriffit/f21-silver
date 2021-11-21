package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.FrameworkComponentEntity
import com.example.workout_companion.enumeration.MuscleGroup

/**
 * A DAO for accessing the [FrameworkComponentEntity]
 */
@Dao
interface FrameworkComponentDao {

    /**
     * Get all components of a specific workout framework day
     *
     * @property day_id the primary key of the framework day.
     *
     * @return a LiveData List of all components of a day.
     */
    @Query("SELECT * FROM framework_component WHERE framework_day_id=:day_id")
    fun getAllComponentsOfDay(day_id: Int) : LiveData<List<FrameworkComponentEntity>>

    /**
     * Get the number of components where the framework_day_id,
     * muscle_group, target_sets, and target_reps are equal to the integer
     * and muscle group enum provided
     */
    @Query("""
        SELECT COUNT(*) FROM framework_component
        WHERE framework_day_id = :framework_day_id
        AND muscle_group = :muscle_group
        AND target_sets = :target_sets
        AND target_reps = :target_reps
    """)
    suspend fun count(framework_day_id: Int, muscle_group: MuscleGroup,
                      target_sets: Int, target_reps: Int): Int
    /**
     * Add a component to a framework day
     *
     * @property component  the component to add.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFrameworkComponent(component: FrameworkComponentEntity)

    /**
     * Add multiple components to framework days
     *
     * @property components a [Collection] of framework components to add.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFrameworkComponents(components: Collection<FrameworkComponentEntity>)

    /**
     * Add multiple components to framework days
     *
     * @property components a comma-separated list of components to add.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFrameworkComponents(vararg components: FrameworkComponentEntity)

    /**
     * Update an existing framework component
     *
     * @property component  the component to update
     */
    @Update
    suspend fun updateFrameworkComponent(component: FrameworkComponentEntity)

    /**
     * Delete a component from a day
     *
     * @property component  the component to remove.
     */
    @Delete
    suspend fun deleteFrameworkComponent(component: FrameworkComponentEntity)

    /**
     * Delete multiple components from days
     *
     * @property components a [Collection] of components to remove.
     */
    @Delete
    suspend fun deleteFrameworkComponents(components: Collection<FrameworkComponentEntity>)

    /**
     * Delete multiple components from days
     *
     * @property components a comma-separated list of components to remove.
     */
    @Delete
    suspend fun deleteFrameworkComponents(vararg components: FrameworkComponentEntity)
}
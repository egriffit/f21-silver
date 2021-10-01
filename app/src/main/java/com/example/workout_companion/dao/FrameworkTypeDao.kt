package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.FrameworkTypeEntity

/**
 * A Database Access Object for the FrameworkTypeEntity
 *
 * @see FrameworkTypeEntity
 */
@Dao
interface FrameworkTypeDao {

    /**
     * Get all frameworks within the FrameworkTypeEntity table
     *
     * @return a LiveData List of all frameworks.
     */
    @Query("SELECT * FROM framework_type")
    fun getAllFrameworks(): LiveData<List<FrameworkTypeEntity>>

    /**
     * Get a framework by its primary key, id
     *
     * @property framework_id   the primary key of the framework to find
     *
     * @return the framework with a matching id, or null
     */
    @Query("SELECT * FROM framework_type WHERE id=:framework_id")
    fun getFrameworkById(framework_id: Int): FrameworkTypeEntity?

    /**
     * Get all frameworks whose number of workouts per week is less than or equal to [maxNumWorkouts]
     *
     * @property maxNumWorkouts the maximum number of workouts for the frameworks
     *
     * @return a LiveData List of all frameworks matching the criteria
     */
    @Query("SELECT * FROM framework_type WHERE workouts_per_week<:maxNumWorkouts")
    fun getFrameworksWithinMaxWorkouts(maxNumWorkouts: Int): LiveData<List<FrameworkTypeEntity>>

    /**
     * Add a framework to the database
     *
     * @property framework  the framework to be added to the database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFramework(framework: FrameworkTypeEntity)

    /**
     * Delete the framework by its id
     *
     * @property framework  the framework to be deleted.
     */
    @Delete
    suspend fun deleteFramework(framework: FrameworkTypeEntity)
}
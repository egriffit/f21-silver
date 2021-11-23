package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.FrameworkComponentEntity
import com.example.workout_companion.entity.FrameworkDayEntity
import com.example.workout_companion.entity.FrameworkTypeEntity

/**
 * Container class that facilitates collecting a framework day and its components with a single query
 *
 * @property day The framework day
 * @property components The list of components for the given day
 */
data class FrameworkDayWithComponents(
    @Embedded
    val day: FrameworkDayEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "framework_day_id",
        entity = FrameworkComponentEntity::class,
    )
    val components: List<FrameworkComponentEntity>
)

@Dao
interface FrameworkDayDao {

    /**
     * Gets all days in a given workout framework
     *
     * @property framework_id   the primary key of the framework used for the query.
     */
    @Query("SELECT * FROM framework_day WHERE framework_type_id = :framework_id")
    fun getAllFrameworkDays(framework_id: Int) : LiveData<List<FrameworkDayEntity>>

    /**
     * Gets all days in a given framework along with their components
     */
    @Query("SELECT * FROM framework_day WHERE framework_type_id = :frameworkId")
    fun getFrameworkDaysWithComponents(frameworkId: Int): LiveData<List<FrameworkDayWithComponents>>

    /**
     * Get count for frameworks with the name and id
     * equal to the string and integer provided
     *
     * @param name
     * @param framework_id
     * @return int, number of records found
     */
    @Query("""
        SELECT COUNT(*) FROM framework_day
        WHERE name = :name 
        AND framework_type_id = :framework_id
    """)
    suspend fun count(name:String, framework_id: Int): Int


    /**
     * Add a new day to a workout framework
     *
     * @property frameworkDay   the day to add to the database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFrameworkDay(frameworkDay: FrameworkDayEntity)

    /**
     * Adds all days within the collection to a workout framework in the database
     *
     * @property frameworkDays  a [Collection] of days to to add.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFrameworkDays(frameworkDays: Collection<FrameworkDayEntity>)

    /**
     * Adds all days to a workout framework within the database
     *
     * @property frameworkDays  a comma-separated list of days to add.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFrameworkDays(vararg frameworkDays: FrameworkDayEntity)

    /**
     * Update an existing day in a workout framework
     *
     * @property frameworkDay   the updated framework day.
     */
    @Update
    suspend fun updateFrameworkDay(frameworkDay: FrameworkDayEntity)

    /**
     * Delete an existing day from a workout framework
     *
     * @property frameworkDay   the day to delete.
     */
    @Delete
    suspend fun deleteFrameworkDay(frameworkDay: FrameworkDayEntity)

    /**
     * Deletes all existing days from their frameworks in the database
     *
     * @property frameworkDays  a [Collection] of days to delete.
     */
    @Delete
    suspend fun deleteFrameworkDays(frameworkDays: Collection<FrameworkDayEntity>)

    /**
     * Deletes all existing days from their frameworks in the database
     *
     * @property frameworkDays  a comma-separated list of days to delete.
     */
    @Delete
    suspend fun deleteFrameworkDays(vararg frameworkDays: FrameworkDayEntity)
}
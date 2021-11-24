package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.FrameworkComponentEntity
import com.example.workout_companion.entity.FrameworkDayEntity
import com.example.workout_companion.entity.FrameworkTypeEntity

/**
 * Wrapper class containing a framework and its days. By setting up this class, SQL can
 * return all of the relevant data in one query.
 *
 * @property framework The framework
 * @property days The days of the framework
 *
*/
data class FrameworkWithDays(
    @Embedded
    val framework: FrameworkTypeEntity,

    @Relation(parentColumn = "id", entityColumn = "framework_type_id", entity = FrameworkDayEntity::class)
    val days: List<FrameworkDayWithComponents>
)

/**
 * Wrapper class containing a framework day and its components. By setting up this class, SQL can
 * return all of the relevant data in one query.
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

/**
 * Data Access Object for read-only access to a framework, its days,
 * and each day's components as one object.
 */
@Dao
interface CompleteFrameworkDao {

    /**
     * Gets all frameworks
     *
     * @return a LiveData list of all frameworks
     */
    @Transaction
    @Query("SELECT * FROM framework_type")
    fun getAllFrameworksWithDays(): LiveData<List<FrameworkWithDays>>

    /**
     * Gets a single framework by id
     *
     * @param frameworkId the primary key of the framework
     *
     * @return LiveData of the framework
     */
    @Transaction
    @Query("SELECT * FROM framework_type WHERE id=:frameworkId")
    fun getFrameworkWithDaysById(frameworkId: Int): LiveData<FrameworkWithDays>
}
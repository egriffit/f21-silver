package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.FrameworkDayEntity
import com.example.workout_companion.entity.FrameworkTypeEntity
import com.example.workout_companion.entity.FrameworkWithGoalEntity

data class FrameworkWithDays(
    @Embedded
    val framework: FrameworkTypeEntity,

    @Relation(parentColumn = "id", entityColumn = "framework_type_id", entity = FrameworkDayEntity::class)
    val days: List<FrameworkDayWithComponents>
)

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

    @Query("SELECT * FROM framework_type")
    fun getAllFrameworksWithDays(): LiveData<List<FrameworkWithDays>>

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
    @Query("SELECT * FROM framework_type WHERE workouts_per_week<=:maxNumWorkouts")
    fun getFrameworksWithinMaxWorkouts(maxNumWorkouts: Int): LiveData<List<FrameworkTypeEntity>>

    /**
     * Get all frameworks that link to the given goal
     *
     * @property goal_id    the primary key for the selected goal.
     *
     * @return a LiveData List of all frameworks that have the given goal
     */
    @Query("SELECT * FROM framework_type WHERE goal_id=:goal_id")
    fun getFrameworksWithGoal(goal_id: Int) : LiveData<List<FrameworkTypeEntity>>

    /**
     * Get all frameworks that link to the given goal by the name
     *
     * @property goal    the primary key for the selected goal.
     *
     * @return a LiveData List of all frameworks that have the given goal
     */
    @Query("SELECT * FROM frameworkwithgoalentity WHERE goal=:goal")
    fun getFrameworksWithGoalName(goal: String) : LiveData<List<FrameworkWithGoalEntity>>

    /**
     * Get all frameworks that link to the given goal by the name
     *
     * @property goal    the primary key for the selected goal.
     *
     * @return a LiveData List of all frameworks that have the given goal
     */
    @Query("SELECT id FROM frameworkwithgoalentity WHERE name = :name AND goal=:goal")
    fun getFrameworkIdByName(name: String, goal: String) : Int

    /**
     * Get all frameworks that link to the given goal by the name
     *
     * @property goal    the name of the selected goal.
     * @property workoutsPerWeek    the number of workouts_per_week
     * @return a LiveData List of all frameworks that have the given goal and number of workouts
     */
    @Query("SELECT * FROM frameworkwithgoalentity WHERE goal=:goal AND workouts_per_week<=:workoutsPerWeek")
    fun getFrameworksWithGoalNameWithinMaxWorkouts(goal: String, workoutsPerWeek: Int) : LiveData<List<FrameworkWithGoalEntity>>

    /**
     * Get count for frameworks with the name, goal_id and workouts_per_week
     * equal to the string, and ints provided
     *
     * @param name, name of framework
     * @param goal, id of the goal
     * @parma workouts_per_week, number of workouts a user can do per week
     *
     */
    @Query("""
        SELECT COUNT(*) FROM framework_type 
        WHERE name=:name 
        AND goal_id=:goal 
        AND workouts_per_week=:workouts_per_week""" )
    fun getCount(name: String, goal: Int, workouts_per_week: Int): Int

    /**
     * Add a framework to the database
     *
     * @property framework  the framework to be added to the database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFramework(framework: FrameworkTypeEntity)

    /**
     * Add a collection of frameworks to the database
     *
     * @property frameworks the frameworks to add to the database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFrameworks(frameworks: Collection<FrameworkTypeEntity>)

    /**
     * Adds all frameworks to the database
     *
     * @property framework  the framework to add
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFrameworks(vararg framework: FrameworkTypeEntity)

    /**
     * Update a framework with the framework provided
     *
     * @property framework the framework to update
     */
    @Update
    suspend fun updateFramework(framework: FrameworkTypeEntity)

    /**
     * Delete the framework by its id
     *
     * @property framework  the framework to be deleted.
     */
    @Delete
    suspend fun deleteFramework(framework: FrameworkTypeEntity)

    /**
     * Delete all frameworks within the collection from the database
     *
     * @property frameworks the frameworks to remove from the database.
     */
    @Delete
    suspend fun deleteFrameworks(frameworks: Collection<FrameworkTypeEntity>)

    /**
     * Delete all frameworks from the database
     *
     * @property framework  the frameworks to remove from the database.
     */
    @Delete
    suspend fun deleteFrameworks(vararg framework: FrameworkTypeEntity)
}
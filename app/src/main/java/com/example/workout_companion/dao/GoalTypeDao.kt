package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.GoalTypeEntity

/**
 * A Database Access Object for the GoalTypeEntity table
 *
 * @see GoalTypeEntity
 */
@Dao
interface GoalTypeDao {

    /**
     * Gets all goals in the GoalTypeEntity table
     *
     * @return a LiveData variable containing all GoalTypeEntity values in the table.
     */
    @Query("SELECT * FROM goal_type")
    fun getAllGoals(): LiveData<List<GoalTypeEntity>>

    /**
     * Gets a goal by its primary key
     *
     * @property goalId the primary key of the goal.
     *
     * @return the goal if it exists, otherwise null.
     */
    @Query("SELECT * FROM goal_type WHERE id=:goalId")
    fun getGoalById(goalId: Int): GoalTypeEntity?

    /**
     * Gets a goal by its name
     *
     * @property goal the name of the goal.
     *
     * @return the goal if it exists, otherwise null.
     */
    @Query("SELECT * FROM goal_type WHERE id=:goal")
    fun getGoalByName(goal: String): GoalTypeEntity?

    /**
     * Gets a goal count by name
     *
     * @property goal the name of the goal
     *
     * @return integer
     */
    @Query("SELECT COUNT(*) FROM goal_type where goal=:goal")
    fun getCountByGoal(goal: String): Int

    /**
     * Insert a goal into the GoalTypeEntity table, ignoring any insertions where a goal already exists
     *
     * @param goal  the goal to add to the table.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGoal(goal: GoalTypeEntity)

    /**
     * Insert a goal into the GoalTypeEntity table, ignoring any insertions where a goal already exists
     *
     * @param goal  the goal to add to the table.
     */
    @Update
    suspend fun updateGoal(goal: GoalTypeEntity)
}
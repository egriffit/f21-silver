package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.GoalAndNutritionPlanTypeEntity
import com.example.workout_companion.entity.NutritionPlanTypeEntity
import java.util.*

/**
 * The Data Abstraction Interface for the UserEntity
 *
 * Provides methods for SQL queries using the UserEntity
 */
@Dao
interface NutritionPlanTypeDao {

    /**
     * Retrieves a List of NutritionPlanTypeEntity objects from the nutrition_plan_type table
     *
     * @return LiveData<List<NutritionPlanTypeEntity> a list of NutritionPlanTypeEntity objects
     */
    @Query("SELECT * FROM nutrition_plan_type")
    fun getAll(): LiveData<List<NutritionPlanTypeEntity>>


    /**
     * Retrieves a NutritionPlanTypeEntity object from nutrition_plan_type table where
     * the id column matches the provided Integer.
     *
     * @param Id, a Integer equal to the id of the nutrition plan
     * @return  UserEntity object
     */
    @Query("SELECT * FROM nutrition_plan_type WHERE id = :id")
    suspend fun getById(id: Int): NutritionPlanTypeEntity

    /**
     * Retrieves the row count for the total of records in the nutrition_plan_type table
     *
     * @return  Int total number of rows found
     */
    @Query("SELECT COUNT(*) FROM nutrition_plan_type")
    fun getCount(): Int

    /**
     * Insert a NutritionPlanTypeEntity object into the nutrition_plan_type table
     *
     * @param item, a NutritionPlanTypeEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlan(item: NutritionPlanTypeEntity)

    /**
     * Insert a list of NutritionPlanTypeEntity objects into the nutrition_plan_type table
     *
     * @param item, a List<NutritionPlanTypeEntity>
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlan(item: List<NutritionPlanTypeEntity>)

    /**
     * Update a the nutrition_plan_type record with the values in the provided NutritionPlanTypeEntity object
     *
     * @param item, a NutritionPlanTypeEntity
     * @return void
     */
    @Update
    suspend fun update(item: NutritionPlanTypeEntity)

    /**
     * Delete the nutrition_plan_type record with that matches the values in the provided NutritionPlanTypeEntity object
     *
     * @param item, a NutritionPlanTypeEntity
     * @return void
     */
    @Delete
    suspend fun delete(item: NutritionPlanTypeEntity)

    /**
     * Delete all nutrition_plan_type records in the nutrition_plan_type table
     *
     * @return void
     */
    @Query("DELETE FROM nutrition_plan_type")
    suspend fun deleteAll()

    /**
     * Retrieve all nutrition plans with an inner join with the goal_type table
     * to get the goal name
     *
     * @return List<GoalAndNutritionPlanTypeEntity>
     */
    @Transaction
    @Query("SELECT * FROM nutrition_plan_type")
    fun getNutritionPlansWithGoals(): List<GoalAndNutritionPlanTypeEntity>

    /**
     * Retrieve all nutrition plans where goal_name is equal to the string
     * provided.
     *@parm String name of goal
     * @return List<GoalAndNutritionPlanTypeEntity>
     */
    @Query("SELECT * FROM nutrition_plan_type INNER JOIN goal_type ON nutrition_plan_type.goal_id = goal_type.id WHERE goal_type.goal = :goal")
    fun getNutritionPlansByGoal(goal: String): List<GoalAndNutritionPlanTypeEntity>

    /**
     * Retrieve the number of rows with nutrition plans where goal_name is equal to the string
     * provided.
     *
     * @param String name of goal
     * @return Int total of rows
     */
    @Query("SELECT COUNT(*) as INTEGER FROM nutrition_plan_type INNER JOIN goal_type ON nutrition_plan_type.goal_id = goal_type.id WHERE goal_type.goal = :goal")
    fun getNutritionPlanCountByGoal(goal: String): Int
}
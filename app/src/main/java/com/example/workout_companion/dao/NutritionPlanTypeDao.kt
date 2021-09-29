package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
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
    suspend fun insert(item: NutritionPlanTypeEntity)

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
}
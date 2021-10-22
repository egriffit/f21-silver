package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.FoodTypeEntity

/**
 * The Data Abstraction Object for the FoodTypeEntity
 *
 * Provides methods for SQL queries using the FoodTypeEntity
 */
@Dao
interface FoodTypeDao {
    /**
     * Retrieves a List of FoodTypeEntity objects from the food_type table
     *
     * @return LiveData<List<FoodTypeEntity> a list of FoodTypeEntity objects
     */
    @Query("SELECT * FROM food_type")
    fun getAll(): LiveData<List<FoodTypeEntity>>

    /**
     * Retrieves a FoodTypeEntity object from food_type table where
     * the name column matches the provided string.
     *
     * @param name, a string equal to the name of the food
     * @return  FoodTypeEntity object
     */
    @Query("SELECT * FROM food_type WHERE name = :name")
    suspend fun getByName(name: String): FoodTypeEntity

    /**
     * Retrieves the row count for the total of records in the food_type table
     *
     * @return  Int total number of rows found
     */
    @Query("SELECT COUNT(*) FROM user")
    fun getCount(): Int

    /**
     * Retrieves the row count for the number of records with
     * the name equal to the string provided in the food_type table
     *
     * @param name, a string equal to the name of the food
     * @return  Int total number of rows found
     */
    @Query("SELECT COUNT(*) FROM food_type WHERE name = :name")
    suspend fun getCountWithName(name: String): Int

    /**
     * Insert a FoodTypeEntity object into the food_type table
     *
     * @param item, a FoodTypeEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FoodTypeEntity)

    /**
     * Insert a list of FoodTypeEntity objects into the food_type table
     *
     * @param item, a list of FoodTypeEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<FoodTypeEntity>)

    /**
     * Update a the user record with the values in the provided FoodTypeEntity object
     *
     * @param item, a FoodTypeEntity
     * @return void
     */
    @Update
    suspend fun update(item: FoodTypeEntity)

    /**
     * Delete the food_type record with that matches the values in the provided FoodTypeEntity object
     *
     * @param item, a FoodTypeEntity
     * @return void
     */
    @Delete
    suspend fun delete(item: FoodTypeEntity)

    /**
     * Delete all user records in the food_type table
     *
     * @return void
     */
    @Query("DELETE FROM food_type")
    suspend fun deleteAll()

}
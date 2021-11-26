package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.entity.MealEntity
import java.time.LocalDate

/**
 * The Data Abstraction Object for the MealEntity
 *
 * Provides methods for SQL queries using the MealEntity
 */
@Dao
interface MealDao {
    /**
     * Retrieves a List of MealEntity objects from the meal table
     * for a give date
     *
     * @return LiveData<List<MealEntity> a list of MealEntity objects
     */
    @Query("SELECT * FROM meal WHERE date = strftime('%Y-%m-%d', DATE('now', 'localtime'))")
    fun getByDate(): LiveData<List<MealEntity>>


    /**
     * Retrieves a List of MealEntity objects from the meal table
     * for a give date
     *
     * @return LiveData<List<MealEntity> a list of MealEntity objects
     */
    @Query("SELECT * FROM meal where date = :date")
    fun getByDate(date: LocalDate): List<MealEntity>

    /**
     * Retrieves a Meal object from meal table where
     * the type column matches the provided string
     * and is equal to the current date
     *
     * @param type, a string equal to the type of meal
     * @return  FoodTypeEntity object
     */
    @Query("""
       SELECT * FROM meal 
        WHERE type = :type 
        AND date = strftime('%Y-%m-%d', DATE('now', 'localtime'))
    """)
    fun getByName(type: String): List<MealEntity>

    /**
     * Retrieves the ide of a meal based on the name of the meal
     * and date
     *
     * @param type, a string equal to the type of meal
     * @param date, LocalDate
     * @return  id, Int
     */
    @Query("""
       SELECT id FROM meal 
        WHERE type = :type 
        AND date = :date
        LIMIT 1
    """)
    fun getMealId(type: String, date: LocalDate): Int

    /**
     * Retrieves the id of a meal based on the name of the meal
     * where the date is today
     *
     * @param type, a string equal to the type of meal
     * @return  id, Int
     */
    @Query("""
       SELECT id FROM meal 
        WHERE type = :type 
        AND date = strftime('%Y-%m-%d', DATE('now', 'localtime'))
        LIMIT 1
    """)
    fun getMealId(type: String): Int

    /**
     * Retrieves the row count for the total of records in the meal table
     * for a current date
     *
     * @return  Int total number of rows found
     */
    @Query("""
        SELECT COUNT(*) FROM meal
        WHERE date = strftime('%Y-%m-%d', DATE('now', 'localtime'))
        """)
    fun getCount(): Int

    /**
     * Retrieves the row count for the total of records in the meal table
     * for a current date and name of meal
     *
     * @return  Int total number of rows found
     */
    @Query("""
        SELECT COUNT(*) FROM meal 
        WHERE type = :name
        AND date = strftime('%Y-%m-%d', DATE('now', 'localtime'))
        """)
    fun getCount(name: String): Int

    /**
     * Insert a MealEntity object into the meal table
     *
     * @param item, a MealEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MealEntity)

    /**
     * Insert a list of MealEntity objects into the meal table
     *
     * @param item, a list of MealEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<MealEntity>)

    /**
     * Update a the meal record with the values in the provided MealEntity object
     *
     * @param item, a MealEntity
     * @return void
     */
    @Update
    suspend fun update(item: MealEntity)


    /**
     * Delete the meal record  that matches the values in the provided MealEntity object
     *
     * @param item, a MealEntity
     * @return void
     */
    @Delete
    suspend fun delete(item: MealEntity)

    /**
     * Delete all records in the meal table
     * for the current date
     * @return void
     */
    @Query("DELETE FROM meal WHERE date = strftime('%Y-%m-%d', DATE('now', 'localtime'))")
    suspend fun deleteAll()

    /**
     * Delete all records in the meal table
     * for the provided date
     * @return void
     */
    @Query("DELETE FROM meal WHERE date = :date")
    suspend fun deleteAll(date: LocalDate)

}
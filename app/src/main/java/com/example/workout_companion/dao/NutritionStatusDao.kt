package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.entity.NutritionStatusEntity
import com.example.workout_companion.entity.WorkoutEntity
import com.example.workout_companion.enumeration.NutritionStatusEnum
import java.time.LocalDate

/**
 * A Database Access Object for the NutritionStatus table
 *
 * @see NutritionStatusEntity
 */
@Dao
interface NutritionStatusDao {
    /**
     * Gets all statuses in the NutritionStatus table
     *
     * @return a LiveData variable containing all NutritionStatusEntity values in the table.
     */
    @Query("SELECT * FROM nutrition_status")
    fun getAllStatuses(): LiveData<List<NutritionStatusEntity>>


    @Query("SELECT * FROM nutrition_status WHERE date = strftime('%Y-%m-%d', DATE('now', 'localtime')) LIMIT 1")
    suspend fun getTodaysStatus(): NutritionStatusEntity

    /**
     * Retrieves a NutritionStatusEntity object from the nutrition status table
     * for a given date
     *
     * @return LiveData<NutritionStatusEntity> a NutritionStatusEntity object
     */
    @Query("SELECT * FROM nutrition_status where date = :date")
    fun getStatusByDate(date: LocalDate): LiveData<NutritionStatusEntity>

    /**
     * Retrieves a NutritionStatusEntity object from the nutrition status table
     * for a given id
     *
     * @return LiveData<NutritionStatusEntity> a NutritionStatusEntity object
     */
    @Query("SELECT * FROM nutrition_status WHERE id = :id")
    fun getCurrentStatus(id: Int): LiveData<NutritionStatusEntity>

    /**
     * Update a nutrition status record with the values in the provided NutritionStatusEntity object
     *
     * @param item, a NutritionStatusEntity
     * @return void
     */
    @Update
    suspend fun update(item: NutritionStatusEntity)

    @Query("UPDATE nutrition_status SET status = :status WHERE date = :date")
    suspend fun update2(status: NutritionStatusEnum, date: LocalDate)
    /**
     * Insert a NutritionStatusEntity object into the nutrition status table
     *
     * @param item, a NutritionStatusEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: NutritionStatusEntity)

    /**
     * Retrieves the row count for the total of records in the nutrition status table
     * for a current date and status
     *
     * @return  Int total number of rows found
     */
    @Query("SELECT COUNT(*) FROM nutrition_status WHERE date = :date")
    fun getCount(date: LocalDate): Int
}
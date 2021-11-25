package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.NutritionStatusEntity

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
}
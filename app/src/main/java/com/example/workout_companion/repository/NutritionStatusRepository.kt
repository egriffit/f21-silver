package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.NutritionStatusDao
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.entity.NutritionStatusEntity
import com.example.workout_companion.entity.WorkoutEntity
import java.time.LocalDate

/**
 * NutritionStatusRepository class that abstracts the methods in the NutritionStatusDao
 *
 */
class NutritionStatusRepository(private val nutritionStatusDao: NutritionStatusDao) {
    /**
     * Retrieves all nutrition status from nutrition status table
     *
     * @return LiveData<List<NutritionStatusEntity>>
     */
    val getAllStatuses: LiveData<List<NutritionStatusEntity>> = nutritionStatusDao.getAllStatuses()

    fun getCurrentStatus(id: Int): LiveData<NutritionStatusEntity> {
        return nutritionStatusDao.getCurrentStatus(id)
    }

    /**
     * Retrieves the total number of rows in the nutrition status table
     *
     * @return total number of rows
     */
    fun getCount(status: String, date: LocalDate): Int {
        return nutritionStatusDao.getCount(status, date)
    }

    /**
     * Retrieves a nutrition status object from nutrition status table for given date
     *
     * @return LiveData<NutritionStatusEntity>
     */
    fun getStatusByDate(date: LocalDate): LiveData<NutritionStatusEntity> {
        return nutritionStatusDao.getStatusByDate(date)
    }
}
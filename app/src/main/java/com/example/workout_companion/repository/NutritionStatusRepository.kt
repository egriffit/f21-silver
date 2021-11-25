package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.GoalTypeDao
import com.example.workout_companion.entity.NutritionStatusEntity

/**
 * NutritionStatusRepository class that abstracts the methods in the NutritionStatusDao
 *
 */
class NutritionStatusRepository(private val nutritionStatusDao: GoalTypeDao) {
    /**
     * Retrieves all meals from meal table for current date
     *
     * @return LiveData<List<MealEntity>>
     */
    val getAllStatuses: LiveData<List<NutritionStatusEntity>> = nutritionStatusDao.getAllStatuses()
}
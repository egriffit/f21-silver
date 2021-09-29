package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.NutritionPlanTypeDao
import com.example.workout_companion.entity.NutritionPlanTypeEntity

/**
 * User Repository class that abstracts the methods in the User DAO
 *
 */
class NutritionPlanTypeRepository (private val nutritionPlanTypeDao: NutritionPlanTypeDao) {
    /**
     * Retrieves a list of all nutrition plan types in the nutrition_plan_type table
     *
     * @return LiveData<List<NutritionPlanTypeEntity>> List of Nutrition Plan Types
     */
    val getAll: LiveData<List<NutritionPlanTypeEntity>> = nutritionPlanTypeDao.getAll()

    /**
     * Retrieves a NutritionPlanTypeEntity from the nutrition_plan_type table where
     * the id is equal to the Integer provided
     *
     * @param Integer Id
     * @return NutritionPlanTypeEntity
     */
    suspend fun getById(Id: Int): NutritionPlanTypeEntity{
        return nutritionPlanTypeDao.getById(Id)
    }

    /**
     * Retrieves the total number of rows in the nutrition_plan_type table
     *
     * @return Int
     */
    suspend fun getCount(): Int{
        return nutritionPlanTypeDao.getCount()
    }

    /**
     * Adds a nutrition plan to the nutrition_plan_type table
     *
     * @param item NutritionPlanTypeEntity
     * @return void
     */
    suspend fun addPlan(item: NutritionPlanTypeEntity){
        nutritionPlanTypeDao.insert(item)
    }

    /**
     * Update a nutrition plan in the nutrition_plan_type table with the
     * values in the provided NutritionPlanTypeEntity object
     *
     * @param item a NutritionPlanTypeEntity
     * @return void
     */
    suspend fun updatePlan(item: NutritionPlanTypeEntity){
        nutritionPlanTypeDao.update(item)
    }

    /**
     * Delete a nutrition plan in the nutrition_plan_type table with the
     * values in the provided NutritionPlanTypeEntity  object
     *
     * @param item a NutritionPlanTypeEntity
     * @return void
     */
    suspend fun deletePlan(item: NutritionPlanTypeEntity){
        nutritionPlanTypeDao.delete(item)
    }

    /**
     * Deletes all nutrition plans in the nutrition_plan_type table
     *
     * @return void
     */
    suspend fun deletaAll(){
        return nutritionPlanTypeDao.deleteAll()
    }

}
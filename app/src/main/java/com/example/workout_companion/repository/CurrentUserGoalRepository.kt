package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.CurrentUserGoalDao
import com.example.workout_companion.entity.CurrentNutritionPlanAndFrameworkEntity
import com.example.workout_companion.entity.CurrentUserGoalEntity

/**
 * CurrentUserGoal Repository class that abstracts the methods in the Current User Goal DAO
 *
 */
class CurrentUserGoalRepository (private val currentUserGoalDao: CurrentUserGoalDao) {
    /**
     * Retrieves the current user nutrition_plan_type_id and framework_type_id
     *
     * @return LiveData<CurrentUserGoalEntity>
     */
    val getCurrentGoalIds: LiveData<CurrentUserGoalEntity> = currentUserGoalDao.getCurrentGoalIds()

    /**
     * Retrieves a the current user goals from the current_user_goal table
     *
     * @return CurrentNutritionPlanAndFrameworkEntity
     */
    val getCurrentUserGoals: LiveData<CurrentNutritionPlanAndFrameworkEntity> = currentUserGoalDao.getCurrentGoals()

    /**
     * Check if there is a record in the current_user_goals table with an id of 1
     *
     * @return Boolean
     */
    val currentGoalExists: LiveData<Boolean> = currentUserGoalDao.currentGoalExists()

    /**
     * Adds a CurrentUserGoalEntity to the current_user_goal table
     * if one does not exist
     *
     * @param item, a CurrentUserGoalEntity
     * @return void
     */
    suspend fun addCurrentUserGoals(item: CurrentUserGoalEntity){
        val exists: Boolean = currentUserGoalDao.currentGoalExists().value == true
        if(!exists){
            currentUserGoalDao.addCurrentUserGoal(item)
        }else{
            currentUserGoalDao.update(item)
        }
    }

    /**
     * Update a the current_user_goal record with the name of the nutrition_plan_type
     *
     * @param calorie, a double
     * @param protein, a double
     * @param fat, a double
     * @param carbohydrate, a double
     * @return void
     */
    suspend fun updateNutritionPlan(calorie: Double, carbohydrate: Double, protein: Double, fat: Double)
    {
        currentUserGoalDao.updateNutritionPlan(calorie, carbohydrate, protein, fat)
    }

    /**
     * Update a the current_user_goal record with the nutrition_plan_type_id
     *
     * @param nutrition_plan_type_id, a int
     * @return void
     */
    suspend fun updateNutritionPlanID(nutrition_plan_type_id: Int ){
        currentUserGoalDao.updateNutritionPlanID(nutrition_plan_type_id)
    }
    /**
     * Update a the current_user_goal record with the name of the framework_type
     *
     * @param framework_type, a String
     * @return void
     */
    suspend fun updateFrameworkType(framework_type: String){
        currentUserGoalDao.updateFrameworkType(framework_type)
    }

    /**
     * Update a the current_user_goal record with the id of the framework_type
     *
     * @param framework_type_id, a int
     * @return void
     */
    suspend fun updateFrameworkTypeID(framework_type_id: Int ){
        currentUserGoalDao.updateFrameworkTypeID(framework_type_id)
    }

    /**
     * Update a the current_user_goal record with the name of the framework_type, calorie, carbohydrate, fat, and protein
     *
     * @param framework_type, a String
     * @param calorie, a double
     * @param protein, a double
     * @param fat, a double
     * @param carbohydrate, a double
     * @return void
     */
    suspend fun updateNutritionPlanAndFramework(framework_type: String, calorie: Double, carbohydrate: Double, protein: Double, fat: Double){
        currentUserGoalDao.updateNutritionPlanAndFramework(framework_type, calorie, carbohydrate, protein, fat)
    }

    /**
     * Update a the current_user_goal record with the provided nutrition_plan_type and framework_type ids
     *
     * @param framework_type_id, a int
     * @param nutrition_plan_type_id, a int
     * @return void
     */
    suspend fun updateNutritionPlanAndFrameworkID(framework_type_id: Int, nutrition_plan_type_id: Int)
    {
        currentUserGoalDao.updateNutritionPlanAndFrameworkID(framework_type_id, nutrition_plan_type_id)
    }



}
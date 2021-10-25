package com.example.workout_companion.repository

import com.example.workout_companion.dao.FoodTypeDao
import com.example.workout_companion.entity.FoodTypeEntity

import androidx.lifecycle.LiveData

/**
 * FoodTypeRepository class that abstracts the methods in the FoodType DAO
 *
 */
class FoodTypeRepository (private val foodTypeDao: FoodTypeDao) {
    /**
     * Retrieves all foods from food_type table
     *
     * @return LiveData<List<FoodTypeEntity>>
     */
    val getAllFoods: LiveData<List<FoodTypeEntity>> = foodTypeDao.getAll()

    /**
     * Retrieves all foods from food_type table where the name is the same as the
     * string provided
     *
     * @return LiveData<List<FoodTypeEntity>>
     */
    suspend fun getFoodByName(name: String): LiveData<List<FoodTypeEntity>>{
        return foodTypeDao.getByName(name)
    }

    /**
     * Retrieves number of foods in food_type table
     *
     * @return Int
     */
    suspend fun getCount(): Int{
        return foodTypeDao.getCount()
    }

    /**
     * Retrieves number of foods in food_type table with the name
     * equal to the string provided
     *
     * @return Int
     */
    suspend fun getCount(name: String): Int{
        return foodTypeDao.getCountWithName(name)
    }

    /**
     * Add a list of foods to the food_type table
     *
     *@param foods List<FoodTypeEntity>
     * @return void
     */
    suspend fun insert(foods: List<FoodTypeEntity>){
        return foodTypeDao.insert(foods)
    }


    /**
     * Add a food to the food_type table
     *
     *@param foods FoodTypeEntity
     * @return void
     */
    suspend fun insert(food: FoodTypeEntity){
        return foodTypeDao.insert(food)
    }


    /**
     * Update a food in the food_type table
     *
     *@param foods FoodTypeEntity
     * @return void
     */
    suspend fun update(food: FoodTypeEntity){
        return foodTypeDao.update(food)
    }


    /**
     * Delete a food in the food_type table
     *
     *@param foods FoodTypeEntity
     * @return void
     */
    suspend fun delete(food: FoodTypeEntity){
        return foodTypeDao.delete(food)
    }

    /**
     * Delete all foods in the food_type table
     *
     * @return void
     */
    suspend fun deleteAll(){
        return foodTypeDao.deleteAll()
    }
}
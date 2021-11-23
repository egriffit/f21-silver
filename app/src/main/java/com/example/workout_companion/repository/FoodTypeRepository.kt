package com.example.workout_companion.repository

import com.example.workout_companion.dao.FoodTypeDao
import com.example.workout_companion.entity.FoodTypeEntity

import androidx.lifecycle.LiveData
import androidx.room.Transaction

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
    fun getFoodByName(name: String): List<FoodTypeEntity>{
        return foodTypeDao.getByName(name)
    }

    /**
     * Retrieves the row count for the number of records with
     * the fields equal to the fields in the foodEntity object provided
     *
     * @param item, FoodTypeEntity
     */
    suspend fun getId(item: FoodTypeEntity): Int{
        return foodTypeDao.getId(item.name, item.calories, item.serving_size,
            item.carbohydrates, item.protein, item.fat)
    }

    /**
     * Retrieves number of foods in food_type table
     *
     * @return Int
     */
     fun getCount(): Int{
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
     * Retrieves the row count for the number of records with
     * the fields equal to the fields in the foodEntity object provided
     *
     * @param name, String
     * @param calories, Double
     * @param serving_size, Double
     * @param carbohydrates, Double
     * @param protein, Double
     * @param fat, Double
     * @return  Int total number of rows found
     */
    suspend fun getCount(name: String, calories: Double, serving_size: Double,
                         carbohydrates: Double, protein: Double, fat: Double): Int{
        return foodTypeDao.getCount(name, calories, serving_size, carbohydrates, protein, fat)
    }

    /**
     * Checks if a food entity provided is already in the table
     *
     * @param item foodEntity
     * @return  Boolean
     */
    suspend fun checkIfExists(item: FoodTypeEntity): Boolean{
        val found = foodTypeDao.getCount(item.name, item.calories, item.serving_size, item.carbohydrates,
                                        item.protein, item.fat)
        if(found > 0){
            return true
        }
        return false
    }

    /**
     * Add a list of foods to the food_type table. Add if not found,
     * update if found
     *
     *@param foods List<FoodTypeEntity>
     * @return void
     */
    @Transaction
    suspend fun insert(foods: List<FoodTypeEntity>){

          for(food in foods) {
              val found = checkIfExists(food)
              if (!found) {
                  foodTypeDao.insert(food)
              } else {
                  foodTypeDao.update(food)
              }
          }
    }


    /**
     * Add a food to the food_type table
     *
     *@param food FoodTypeEntity
     * @return void
     */
    @Transaction
    suspend fun insert(food: FoodTypeEntity){
        val found = checkIfExists(food)
        if(!found){
            foodTypeDao.insert(food)
        }else{
            foodTypeDao.update(food)
        }
    }

    /**
     * Update a food in the food_type table
     *
     *@param food FoodTypeEntity
     * @return void
     */
    suspend fun update(food: FoodTypeEntity){
        return foodTypeDao.update(food)
    }


    /**
     * Delete a food in the food_type table
     *
     *@param food FoodTypeEntity
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
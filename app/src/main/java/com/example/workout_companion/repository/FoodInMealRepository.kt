package com.example.workout_companion.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.workout_companion.dao.FoodInMealDao
import com.example.workout_companion.entity.FoodInMealEntity
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.entity.MealWithFoodsEntity
import java.time.LocalDate

class FoodInMealRepository (private val foodInMealDao: FoodInMealDao) {
    /**
     * Retrieves a List of Foods for a meal with the meal_id
     * equal to the integer provided
     *
     * @return LiveData<List<MealWithFoodsEntity> a list of MealWithFoodsEntity objects
     */
    fun getFoodInMeal(meal_id: Int): LiveData<List<MealWithFoodsEntity>> {
        return foodInMealDao.getFoodInMeal(meal_id)
    }


    /**
     * Retrieves the number of foods in a meal
     * with the name and date equal to the string and local date provided
     *
     * @param type, name of meal
     * @param date, date of meal
     * @return  Int total number of rows found
     */
    fun getFoodInMeal(type: String, date: LocalDate): LiveData<List<MealWithFoodsEntity>> {
        return foodInMealDao.getFoodInMeal(type, date)
    }

    /**
     * Function to initialize a coroutine to retrieve a total number of foods with the meal_id
     * equal to the integer provided
     * @param meal_id, Int
     * @return List of FoodTypeEntity objects
     */
    fun getCount(meal_id: Int): Int{
        return foodInMealDao.getCount(meal_id)
    }

    /**
     * Function to initialize a coroutine to retrieve a total number of foods with the name
     * equal to the string provided
     * @param type, name of meal
     * @return List of FoodTypeEntity objects
     */
    fun getCount(type: String): Int{
        val date = LocalDate.now()
        return foodInMealDao.getCount(type, date)
    }

    /**
     * Function to initialize a coroutine to retrieve a total number of foods with the name and date
     * equal to the string and LocalDate provided
     * @param type, name of meal
     * @param date, date of meal
     * @return List of FoodTypeEntity objects
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCount(type: String, date: LocalDate): Int{
        return foodInMealDao.getCount(type, date)
    }

    /**
     * Insert a FoodInMealEntity object into the food_in_meal table
     *
     * @param item, a FoodInMealEntity
     * @return void
     */
    suspend fun insert(item: FoodInMealEntity){
        foodInMealDao.insert(item)
    }

    /**
     * Insert a list of FoodInMealEntity objects into the food_in_meal table
     *
     * @param item, a list of FoodInMealEntity
     * @return void
     */
    suspend fun insert(item: List<FoodInMealEntity>){
        foodInMealDao.insert(item)
    }

    /**
     * Update a the food_in_meal record with the values in the provided FoodInMealEntity object
     *
     * @param item, a FoodInMealEntity
     * @return void
     */
    suspend fun update(item: FoodInMealEntity){
        foodInMealDao.update(item)
    }


    /**
     * Delete all foods in  Food_in_meal record  that matches the meal_id
     * for the integer provided
     *
     * @param meal_id, Int
     * @return void
     */
    suspend fun delete(meal_id: Int){
        foodInMealDao.delete(meal_id)
    }

    /**
     * Delete all foods in  Food_in_meal record  that matches the
     * FoodInMealEntity provided
     *
     * @param item, FoodInMealEntity
     * @return void
     */
    suspend fun delete(item: FoodInMealEntity){
        foodInMealDao.delete(item)
    }

    /**
     * Delete all records in the food_in_meal table
     *
     * @return void
     */
    suspend fun deleteAll(){
        foodInMealDao.deleteAll()
    }

}
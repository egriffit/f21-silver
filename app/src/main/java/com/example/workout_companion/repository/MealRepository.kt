package com.example.workout_companion.repository

import com.example.workout_companion.dao.MealDao
import com.example.workout_companion.entity.MealEntity
import java.time.LocalDate
import androidx.lifecycle.LiveData
import androidx.room.Transaction
import com.example.workout_companion.api.edamam.entities.ServingSize
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.sampleData.emptyMealEntity

/**
 * FoodTypeRepository class that abstracts the methods in the Meal DAO
 *
 */
class MealRepository (private val mealDao: MealDao) {
    /**
     * Retrieves all meals from meal table for current date
     *
     * @return LiveData<List<MealEntity>>
     */
    val getTodaysMeals: LiveData<List<MealEntity>> = mealDao.getByDate()


    /**
     * Retrieves all meals from meal table where the date is the same as the
     * LocalDate provided
     *
     * @param date, LocalDate
     * @return LiveData<List<MealEntity>>
     */
    fun getMealsByDate(date: LocalDate): List<MealEntity>{
        return mealDao.getByDate(date)
    }

    /**
     * Retrieves all meals from meal table where the name is the same as the
     * string provided
     *
     * @param name, String
     * @return List<MealEntity>
     */
    fun getMealByName(name: String): List<MealEntity>{
        return mealDao.getByName(name)
    }

    /**
     * Retrieves the id of a meal based on the name of the meal
     * where the date is today
     *
     * @param type, a string equal to the type of meal
     * @return  id, Int
     */
    fun getMealId(type: String): Int{
        return mealDao.getMealId(type)
    }

    /**
     * Retrieves the id of a meal based on meal name and date
     * which is equal to string and localDate provided
     *
     * @param type, a string equal to the type of meal
     * @param date, LocalDate
     * @return  id, Int
     */
    fun getMealId(type: String, date: LocalDate): Int{
        return mealDao.getMealId(type, date)
    }

    /**
     * Retrieves all meals from meal table where the name is the same as the
     * string provided
     *
     * @param name, String
     * @return LiveData<List<MealEntity>>
     */
    fun checkIfMealExists(name: String): Boolean {
        val foundRecords = mealDao.getCount(name)
        var found = false
        if (foundRecords > 0)
        {
            found = true
        }
        return found
    }

    /**
     * Retrieves number of meals in the meal table for the current date
     *
     * @return Int
     */
    fun getCount(): Int{
        return mealDao.getCount()
    }


    /**
     * Add a meal to the meal table
     *
     *@param meal MealEntity
     * @return void
     */
    suspend fun insert(meal: MealEntity){
        return mealDao.insert(meal)
    }

    /**
     * Add a meal to the meal table
     *
     *@param meals List of MealEntity Objects
     * @return void
     */
    suspend fun insert(meals: List<MealEntity>){
        return mealDao.insert(meals)
    }

    /**
     * Add a meal to the meal table
     *
     *@param name name of the meal
     * @return void
     */
    suspend fun insert(name: String){
        val date = LocalDate.now()
        val meal = MealEntity(0, name, 0.0, 0.0, 0.0, 0.0,  date)
        return mealDao.insert(meal)
    }


    /**
     * update a meal in the meal table
     *
     * @param meal MealEntity
     * @return void
     */
    suspend fun update(meal: MealEntity){
        return mealDao.update(meal)
    }

    /**
     * update a meal in the meal table
     *
     * @param meal MealEntity
     * @return void
     */
    suspend fun emptyMeal(meal: MealEntity){
        val newMeal = emptyMealEntity
        newMeal.id = meal.id
        return mealDao.update(newMeal)
    }


    /**
     * Update the calories, carbs, protein, fat totals in a meal
     * in the meal table
     *
     * @param meal, MealEntity
     * @param food, FoodTypeEntity to be added
     * @param servings, number of servings
     * @return void
     */
    @Transaction
    suspend fun addToMeal(meal: MealEntity, food: FoodTypeEntity, servings: Double){
        var newMeal = emptyMealEntity
        newMeal.id = meal.id
        newMeal.type = meal.type
        newMeal.calories += food.calories
        newMeal.carbohydrates += food.carbohydrates
        newMeal.protein += food.protein
        newMeal.fat += food.fat
        newMeal.date = meal.date
        mealDao.update(newMeal)
    }

    /**
     * Subtract the calories, carbs, protein, fat totals in a meal
     * in the meal table
     *
     * @param meal, MealEntity
     * @param food, FoodTypeEntity to be added
     * @param servings, number of servings
     * @return void
     */
    @Transaction
    suspend fun subtractFromMeal(meal: MealEntity, food: FoodTypeEntity, servings: Double){
        var newMeal = emptyMealEntity
        newMeal.id = meal.id
        newMeal.type = meal.type
        newMeal.calories -= food.calories
        newMeal.carbohydrates -= food.carbohydrates
        newMeal.protein -= food.protein
        newMeal.fat -= food.fat
        newMeal.date = meal.date
        mealDao.update(newMeal)
    }



    /**
     * Delete a meal in the meal table
     *
     *@param meal, Meal Entity
     * @return void
     */
    suspend fun delete(meal: MealEntity){
        return mealDao.delete(meal)
    }

    /**
     * Delete all meals in the meal table for the current date
     *
     * @return void
     */
    suspend fun deleteAll(){
        mealDao.deleteAll()
    }

    /**
     * Delete all meals in the meal table for the provided date
     *
     * @return void
     */
    suspend fun deleteAll(date: LocalDate){
        mealDao.deleteAll(date)
    }
}
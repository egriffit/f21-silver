package com.example.workout_companion.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.workout_companion.dao.MealDao
import com.example.workout_companion.entity.MealEntity
import java.time.LocalDate
import androidx.lifecycle.LiveData

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
    val getAllMeals: LiveData<List<MealEntity>> = mealDao.getByDate()

    /**
     * Retrieves all meals from meal table where the date is the same as the
     * LocalDate provided
     *
     * @param date, LocalDate
     * @return LiveData<List<MealEntity>>
     */
    suspend fun getMealsByDate(date: LocalDate): LiveData<List<MealEntity>>{
        return mealDao.getByDate(date)
    }

    /**
     * Retrieves all meals from meal table where the name is the same as the
     * string provided
     *
     * @param name, String
     * @return LiveData<List<MealEntity>>
     */
    suspend fun getMealByName(name: String): LiveData<List<MealEntity>>{
        return mealDao.getByName(name)
    }

    /**
     * Retrieves the id of a meal based on the name of the meal
     * where the date is today
     *
     * @param type, a string equal to the type of meal
     * @return  id, Int
     */
    suspend fun getMealId(type: String): Int{
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
    suspend fun getMealId(type: String, date: LocalDate): Int{
        return mealDao.getMealId(type, date)
    }

    /**
     * Retrieves all meals from meal table where the name is the same as the
     * string provided
     *
     * @param name, String
     * @return LiveData<List<MealEntity>>
     */
    suspend fun checkIfMealExists(name: String): Boolean {
        var foundRecords = mealDao.getCount(name)
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
    suspend fun getCount(): Int{
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
     *@param meal MealEntity
     * @return void
     */
    suspend fun insert(meals: List<MealEntity>){
        return mealDao.insert(meals)
    }

    /**
     * Add a meal to the meal table
     *
     *@param meal MealEntity
     * @return void
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun insert(name: String){
        val date = LocalDate.now()
        val meal: MealEntity = MealEntity(0, name, 0.0, 0.0, 0.0, 0.0,  date)
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
     * Update the calories, carbs, protein, fat totals in a meal
     * in the meal table
     *
     * @param name, String
     * @param calories, in grams
     * @param carbohydrates, in grams
     * @param protein, in grams
     * @param fat, in grams
     * @return void
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addToMeal(name: String, calories: Double, carbohydrates: Double,
                       protein: Double, fat: Double){
        val date = LocalDate.now()
        var found: List<MealEntity>? = mealDao.getByName(name).value
        var meal: MealEntity = MealEntity(0, "", 0.0, 0.0, 0.0, 0.0, date)
        if(found != null){
            meal = found.elementAt(0)
            meal.calories += calories
            meal.carbohydrates += carbohydrates
            meal.protein += protein
            meal.fat += fat
        }
        return mealDao.update(meal)
    }

    /**
     * Subtract the calories, carbs, protein, fat totals in a meal
     * in the meal table
     *
     * @param name, String
     * @param calories, in grams
     * @param carbohydrates, in grams
     * @param protein, in grams
     * @param fat, in grams
     * @return void
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun subtractFromMeal(name: String, calories: Double, carbohydrates: Double,
                          protein: Double, fat: Double){
        val date = LocalDate.now()
        var found: List<MealEntity>? = mealDao.getByName(name).value
        var meal: MealEntity = MealEntity(0, "", 0.0, 0.0, 0.0, 0.0, date)
        if(found != null){
            meal = found.elementAt(0)
            meal.calories -= calories
            meal.carbohydrates -= carbohydrates
            meal.protein -= protein
            meal.fat -= fat
        }
        return mealDao.update(meal)
    }



    /**
     * Delete a meal in the meal table
     *
     *@param foods FoodTypeEntity
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
        return mealDao.deleteAll()
    }

    /**
     * Delete all meals in the meal table for the provided date
     *
     * @return void
     */
    suspend fun deleteAll(date: LocalDate){
        return mealDao.deleteAll(date)
    }
}
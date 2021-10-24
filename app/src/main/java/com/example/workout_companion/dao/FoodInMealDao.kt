package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.FoodInMealEntity
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.entity.MealWithFoodsEntity
import java.time.LocalDate

/**
 * The Data Abstraction Object for the FoodInMealEntity
 *
 * Provides methods for SQL queries using the FoodInMealEntity
 */
@Dao
interface FoodInMealDao {
    /**
     * Retrieves a List of Foods for a meal with the meal_id
     * equal to the integer provided
     *
     * @return LiveData<List<MealWithFoodsEntity> a list of MealWithFoodsEntity objects
     */
    @Query("""
        SELECT *
        FROM food_in_meal
        INNER JOIN food_type
        ON food_in_meal.food_id = food_type.id
        WHERE food_in_meal.meal_id = :meal_id
    """)
    fun getFoodInMeal(meal_id: Int): LiveData<List<MealWithFoodsEntity>>

    /**
     * Retrieves a List of Foods for a meal with the meal_id
     * equal to the integer provided
     *
     * @return LiveData<List<MealWithFoodsEntity> a list of MealWithFoodsEntity objects
     */
    @Query("""
        SELECT *
        FROM food_type
        INNER JOIN (SELECT *
            FROM food_in_meal
            INNER JOIN meal
            ON food_in_meal.meal_id = meal.id ) a
        ON a.food_id = food_type.id
        WHERE a.type = :type
        AND a.date = :date
    """)
    fun getFoodInMeal(type: String, date: LocalDate): LiveData<List<MealWithFoodsEntity>>


    /**
     * Retrieves the number of foods in a meal
     * with the name and date equal to the string and local date provided
     *
     * @param type, name of meal
     * @param date, date of meal
     * @return  Int total number of rows found
     */
    @Query("""
        SELECT *
        FROM food_in_meal
        INNER JOIN food_type
        ON food_in_meal.food_id = food_type.id
        WHERE food_in_meal.meal_id = :meal_id
    """)
    fun getCount(meal_id: Int): Int

    /**
     * Retrieves the number of foods in a meal
     * with the name and date equal to the string and local date provided
     *
     * @param type, name of meal
     * @param date, date of meal
     * @return  Int total number of rows found
     */
    @Query("""
        SELECT count(*)
        FROM food_type
        INNER JOIN (SELECT *
            FROM food_in_meal
            INNER JOIN meal
            ON food_in_meal.meal_id = meal.id) a
        ON a.food_id = food_type.id
        WHERE a.type = :type
        ANd a.date = :date
    """)
    fun getCount(type: String, date: LocalDate): Int

    /**
     * Insert a FoodInMealEntity object into the food_in_meal table
     *
     * @param item, a FoodInMealEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FoodInMealEntity)



    /**
     * Insert a list of FoodInMealEntity objects into the food_in_meal table
     *
     * @param item, a list of FoodInMealEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<FoodInMealEntity>)

    /**
     * Update a the food_in_meal record with the values in the provided FoodInMealEntity object
     *
     * @param item, a FoodInMealEntity
     * @return void
     */
    @Update
    suspend fun update(item: FoodInMealEntity)


    /**
     * Delete all foods in  Food_in_meal record  that matches the meal_id
     * for the integer provided
     *
     * @param meal_id, Int
     * @return void
     */
    @Query("""DELETE FROM food_in_meal WHERE meal_id = :meal_id""")
    suspend fun delete(meal_id: Int)

    /**
     * Delete the Food_in_meal record  that matches the values in the provided FoodInMealEntity object
     *
     * @param item, a FoodInMealEntity
     * @return void
     */
    @Delete
    suspend fun delete(item: FoodInMealEntity)


    /**
     * Delete all records in the food_in_meal table
     *
     * @return void
     */
    @Query("DELETE FROM food_in_meal")
    suspend fun deleteAll()

}
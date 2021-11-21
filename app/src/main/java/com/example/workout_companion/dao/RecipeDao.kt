package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.RecipeEntity

/**
 * The Data Abstraction Object for the RecipeEntity
 *
 * Provides methods for SQL queries using the MealEntity
 */
@Dao
interface RecipeDao {
    /**
     * Retrieves a List of recipeEntity objects from the recipe table
     * for a give date
     *
     * @return LiveData<List<MealEntity> a list of MealEntity objects
     */
    @Transaction
    @Query("SELECT * FROM recipes")
    fun getRecipes(): LiveData<List<RecipeEntity>>

    /**
     * Retrieves a List of recipeEntity objects from the recipe table
     * for a given name
     * @param name, String
     * @return LiveData<List<MealEntity> a list of MealEntity objects
     */
    @Query("SELECT * FROM recipes WHERE name = :name")
    fun getRecipe(name: String): LiveData<List<RecipeEntity>>

    /**
     * Retrieves a List of recipeEntity objects from the recipe table
     * for a given name
     * @param name, String
     * @return LiveData<List<MealEntity> a list of MealEntity objects
     */
    @Query("SELECT id FROM recipes WHERE name = :name")
    suspend fun getRecipeId(name: String): Int

    /**
     * Retrieves the row count for the total of records in the recipe table
     *
     * @return  Int total number of rows found
     */
    @Query("""
        SELECT COUNT(*) FROM recipes
        """)
    suspend fun getCount(): Int

    /**
     * Retrieves the row count for the total of records in the recipe table
     * for a provided name
     *
     * @param name, String
     * @return  Int total number of rows found
     */
    @Query("""
        SELECT COUNT(*) FROM recipes 
        WHERE name = :name
        """)
    suspend fun getCountByName(name: String): Int


    /**
     * Insert a RecipeEntity object into the recipe table
     *
     * @param item, a RecipeEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RecipeEntity)

    /**
     * Insert a list of RecipeEntity objects into the recipe table
     *
     * @param item, a list of RecipeEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<RecipeEntity>)

    /**
     * Update the recipe record with the values in the provided RecipeEntity object
     *
     * @param item, a RecipeEntity
     * @return void
     */
    @Update
    suspend fun update(item: RecipeEntity)


    /**
     * Delete the recipe record  that matches the values in the provided RecipeEntity object
     *
     * @param item, a RecipeEntity
     * @return void
     */
    @Delete
    suspend fun delete(item: RecipeEntity)

    /**
     * Delete all records in the meal table
     * with the provided name
     * @param name
     * @return void
     */
    @Query("DELETE FROM recipes WHERE name= :name")
    suspend fun delete(name: String)

    /**
     * Delete all records in the recipes table
     *
     * @return void
     */
    @Query("DELETE FROM recipes ")
    suspend fun deleteAll()

}
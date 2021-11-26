package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.*
import java.time.LocalDate

/**
 * The Data Abstraction Object for the FoodInMealEntity
 *
 * Provides methods for SQL queries using the FoodInMealEntity
 */
@Dao
interface FoodInRecipeDao {
    /**
     * Retrieves a List of Foods for a recipe with the recipe_id
     * equal to the integer provided
     *
     * @return LiveData<List<RecipeWithFoodsEntity> a list of RecipeWithFoodsEntity objects
     */
    @Transaction
    @Query("""
        SELECT *
        FROM food_type
        INNER JOIN (SELECT *
            FROM food_in_recipe
            INNER JOIN recipes
            ON food_in_recipe.recipe_id = recipes.id ) a
        ON a.food_id = food_type.id
        WHERE a.recipe_id = :recipe_id
    """)
    fun getFoodInRecipe(recipe_id: Int): LiveData<List<RecipeWithFoodsEntity>>

    /**
     * Retrieves a List of Foods for a recipe with the recipe_id
     * equal to the integer provided
     *
     * @return LiveData<List<RecipeWithFoodsEntity> a list of RecipeWithFoodsEntity objects
     */
    @Transaction
    @Query("""
        SELECT *
        FROM food_in_recipe
        WHERE recipe_id = :recipe_id
    """)
    fun getFoods(recipe_id: Int): LiveData<List<RecipeWithFoodsEntity>>

    /**
     * Retrieves a List of Foods for a recipe with the name
     * equal to the string provided
     *
     * @param name, string
     * @return LiveData<List<RecipeWithFoodsEntity> a list of RecipeWithFoodsEntity objects
     */
    @Transaction
    @Query("""
        SELECT *
        FROM food_type
        INNER JOIN (SELECT *
            FROM food_in_recipe
            INNER JOIN recipes
            ON food_in_recipe.recipe_id = recipes.id ) a
        ON a.food_id = food_type.id
        WHERE a.name = :name
    """)
    fun getFoodInRecipes(name: String): List<RecipeWithFoodsEntity>


    /**
     * Retrieves the number of foods in a meal
     * with a recipe id equal to the integer provided
     *
     * @param recipe_id, id of meal
     * @return  Int total number of rows found
     */
    @Query("""
        SELECT *
        FROM food_in_recipe
        INNER JOIN food_type
        ON food_in_recipe.food_id = food_type.id
        WHERE food_in_recipe.recipe_id = :recipe_id
    """)
    fun getCount(recipe_id: Int): Int

    /**
     * Retrieves the number of foods in a recipe
     * with the name equal to the string provided
     *
     * @param name, name of recipe
     * @return  Int total number of rows found
     */
    @Query("""
        SELECT count(*)
        FROM food_type
        INNER JOIN (SELECT *
            FROM food_in_recipe
            INNER JOIN recipes
            ON food_in_recipe.recipe_id = recipes.id) a
        ON a.food_id = food_type.id
        WHERE a.name = :name
    """)
    fun getCount(name: String): Int

    /**
     * Insert a FoodInRecipeEntity object into the food_in_recipe table
     *
     * @param item, a FoodInRecipeEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FoodInRecipeEntity)



    /**
     * Insert a list of FoodInRecipeEntity objects into the food_in_recipe table
     *
     * @param item, a list of FoodInRecipeEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<FoodInRecipeEntity>)

    /**
     * Update a the food_in_recipe record with the values in the provided FoodInRecipeEntity object
     *
     * @param item, a FoodInRecipeEntity
     * @return void
     */
    @Update
    suspend fun update(item: FoodInRecipeEntity)


    /**
     * Delete all foods in  food_in_recipe record  that matches the recipe_id
     * for the integer provided
     *
     * @param recipe_id, Int
     * @return void
     */
    @Query("""DELETE FROM food_in_recipe WHERE recipe_id = :recipe_id""")
    suspend fun delete(recipe_id: Int)

    /**
     * Delete the food_in_recipe record  that matches the values in the provided FoodInRecipeEntity object
     *
     * @param item, a FoodInRecipeEntity
     * @return void
     */
    @Delete
    suspend fun delete(item: FoodInRecipeEntity)


    /**
     * Delete all records in the food_in_recipe table
     *
     * @return void
     */
    @Query("DELETE FROM food_in_recipe")
    suspend fun deleteAll()


}
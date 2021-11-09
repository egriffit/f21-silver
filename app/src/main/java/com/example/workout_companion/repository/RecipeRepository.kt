package com.example.workout_companion.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.workout_companion.dao.MealDao
import com.example.workout_companion.entity.MealEntity
import java.time.LocalDate
import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.RecipeDao
import com.example.workout_companion.entity.RecipeEntity

/**
 * FoodTypeRepository class that abstracts the methods in the Meal DAO
 *
 */
class RecipeRepository (private val recipeDao: RecipeDao) {
    /**
     * Retrieves all recipes from recipe table
     *
     * @return LiveData<List<MealEntity>>
     */
    val getAllRecipes: LiveData<List<RecipeEntity>> = recipeDao.getRecipes()

    /**
     * Retrieves a List of recipeEntity objects from the recipe table
     * for a given name
     *
     * @param string, LocalDate
     * @return LiveData<List<RecipeEntity>>
     */
    suspend fun getRecipeByName(name: String): LiveData<List<RecipeEntity>>{
        return recipeDao.getRecipe(name)
    }

    /**
     * Retrieves all meals from meal table where the name is the same as the
     * string provided
     *
     * @param name, String
     * @return LiveData<List<RecipeEntity>>
     */
    suspend fun getRecipeId(name: String): Int{
        return recipeDao.getRecipeId(name)
    }

    /**
     * Retrieves number of meals in the meal table for the current date
     *
     * @return Int
     */
    suspend fun getCount(): Int{
        return recipeDao.getCount()
    }

    /**
     * Retrieves the row count for the total of records in the recipe table
     * for a provided name
     *
     * @param name, String
     * @return Int
     */
    suspend fun getCount(name: String): Int{
        return recipeDao.getCountByName(name)
    }

    /**
     * Insert a RecipeEntity object into the recipe table
     *
     * @param recipe, a RecipeEntity
     * @return void
     */
    suspend fun insert(recipe: RecipeEntity){
        if(recipeDao.getCountByName(recipe.name) > 0)
        {
            recipeDao.insert(recipe)
        }
    }

    /**
     * Insert a RecipeEntity object into the recipe table
     *
     * @param recipe, a RecipeEntity
     * @return void
     */
    suspend fun insert(name: String){
        val recipe: RecipeEntity = RecipeEntity(0, name)
        if(recipeDao.getCountByName(recipe.name) > 0) {
            recipeDao.insert(recipe)
        }
    }

    /**
     * Insert a list of RecipeEntity objects into the recipe table
     *
     * @param recipes, a list of RecipeEntity
     * @return void
     */
    suspend fun insert(recipes: List<RecipeEntity>){
        recipes.forEach{ recipe ->
            if(recipeDao.getCountByName(recipe.name) > 0)
            {
                recipeDao.insert(recipe)
            }
        }
    }

    /**
     * Update the recipe record with the values in the provided RecipeEntity object
     *
     * @param item, a RecipeEntity
     * @return void
     */
    suspend fun update(recipe: RecipeEntity){
        return recipeDao.update(recipe)
    }

    /**
     * Delete the recipe record  that matches the values in the provided RecipeEntity object
     *
     * @param item, a RecipeEntity
     * @return void
     */
    suspend fun delete(item: RecipeEntity){
        return recipeDao.delete(item)
    }

    /**
     * Delete all records in the meal table
     * with the provided name
     * @param name
     * @return void
     */
    suspend fun delete(name: String){
        return recipeDao.delete(name)
    }

    /**
     * Delete all records in the recipes table
     *
     * @return void
     */
    suspend fun deleteAll(){
        return recipeDao.deleteAll()
    }
}
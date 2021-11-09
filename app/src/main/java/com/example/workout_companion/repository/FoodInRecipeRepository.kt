package com.example.workout_companion.repository


import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.FoodInRecipeDao
import com.example.workout_companion.entity.*

class FoodInRecipeRepository (private val foodInRecipeDao: FoodInRecipeDao) {
    /**
     * Retrieves a List of Foods for a recipe with the recipe_id
     * equal to the integer provided
     *
     * @return LiveData<List<RecipeWithFoodsEntity> a list of RecipeWithFoodsEntity objects
     */
    fun getFoodInRecipe(recipe_id: Int): LiveData<List<RecipeWithFoodsEntity>> {
        return foodInRecipeDao.getFoodInRecipe(recipe_id)
    }

    /**
     * Retrieves the list of foods in a recipe
     * with the name equal to the string provided
     *
     * @param name, name of recipe
     * @return  Int total number of rows found
     */
    fun getFoodInRecipe(name: String): LiveData<List<RecipeWithFoodsEntity>> {
        return foodInRecipeDao.getFoodInRecipes(name)
    }

    /**
     * Retrieve a total number of foods with the recipe_id
     * equal to the integer provided
     * @param recipe_id, Int
     * @return Int
     */
    fun getCount(recipe_id: Int): Int{
        return foodInRecipeDao.getCount(recipe_id)
    }

    /**
     * Function to retrieve a total number of foods with the recipe name
     * equal to the string provided
     * @param name, name of recipe
     * @return Int
     */
    fun getCount(name: String): Int{
        return foodInRecipeDao.getCount(name)
    }


    /**
     * Insert a FoodInRecipeEntity object into the food_in_recipe table
     *
     * @param item, a FoodInRecipeEntity
     * @return void
     */
    suspend fun insert(item: FoodInRecipeEntity){
        foodInRecipeDao.insert(item)
    }

    /**
     * Insert a list of FoodInRecipeEntity objects into the food_in_recipe table
     *
     * @param item, a list of FoodInMealEntity
     * @return void
     */
    suspend fun insert(item: List<FoodInRecipeEntity>){
        foodInRecipeDao.insert(item)
    }

    /**
     * Update a the food_in_recipe record with the values in the provided FoodInRecipeEntity object
     *
     * @param item, a FoodInRecipeEntity
     * @return void
     */
    suspend fun update(item: FoodInRecipeEntity){
        foodInRecipeDao.update(item)
    }


    /**
     * Delete all foods in  food_in_recipe record  that matches the recipe_id
     * for the integer provided
     *
     * @param recipe_id, Int
     * @return void
     */
    suspend fun delete(recipe_id: Int){
        foodInRecipeDao.delete(recipe_id)
    }

    /**
     * Delete all foods in  food_in_recipe record  that matches the FoodInRecipeEntity
     * provided
     *
     * @param item, FoodInRecipeEntity
     * @return void
     */
    suspend fun delete(item: FoodInRecipeEntity){
        foodInRecipeDao.delete(item)
    }

    /**
     * Delete all records in the food_in_recipe table
     *
     * @return void
     */
    suspend fun deleteAll(){
        foodInRecipeDao.deleteAll()
    }

}
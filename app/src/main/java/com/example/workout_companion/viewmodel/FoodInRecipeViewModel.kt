package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.*
import com.example.workout_companion.repository.FoodInRecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class FoodInRecipeViewModel(application: Application) : AndroidViewModel(application) {



    /**
     * FoodType Repoository Object
     */
    private val repository: FoodInRecipeRepository

    /**
     * Function to initialize the view.
     * Initializes the WCDatabase, repository and the list of all FoodType entities
     */
    init {
        val foodInRecipeDao = WCDatabase.getInstance(application).foodInRecipeDao()
        repository = FoodInRecipeRepository(foodInRecipeDao)
    }

    /**
     * Retrieves a List of Foods for a meal with the meal_id
     * equal to the integer provided
     *
     * @param recipe_id, int
     * @return LiveData<List<RecipeWithFoodsEntity> a list of RecipeWithFoodsEntity objects
     */
    fun getFoodInMeal(recipe_id: Int): LiveData<List<RecipeWithFoodsEntity>>{
        var recipeFoods= MutableLiveData<List<RecipeWithFoodsEntity>>()
        viewModelScope.launch(Dispatchers.IO){
            recipeFoods.postValue(repository.getFoodInRecipe(recipe_id).value)
        }
        return recipeFoods
    }

    /**
     * Retrieves a List of Foods for a recipe with the recipe_id
     * equal to the integer provided
     *
     * @parm recipe_id, Int
     * @return List<FoodTypeEntity> a list of FoodTypeEntity objects
     */
    fun getFoodsInMeal(recipe_id: Int): List<FoodTypeEntity> {
        var recipeFoods: List<RecipeWithFoodsEntity>?
        val foundFoods: MutableList<FoodTypeEntity> = mutableListOf()

        viewModelScope.launch(Dispatchers.IO) {
            recipeFoods = repository.getFoodInRecipe(recipe_id).value
            if (recipeFoods != null) {
                for (m in recipeFoods!!) {
                    foundFoods.add(m.foods.elementAt(0))
                }
            }
        }
        return foundFoods
    }

    /**
     * Retrieves a List of Foods for a meal with the name and date
     * equal to the string and LocalDate provided
     *
     * @param name, String
     * @return List<FoodTypeEntity> a list of FoodTypeEntity objects
     */
    fun getFoodsInMeal(name: String): LiveData<List<FoodTypeEntity>> {
        var recipeFoods: List<RecipeWithFoodsEntity>?
        val foundFoods: MutableList<FoodTypeEntity> = mutableListOf()
        var found= MutableLiveData<List<FoodTypeEntity>>()

        viewModelScope.launch(Dispatchers.IO) {
            recipeFoods = repository.getFoodInRecipe(name).value
            if (recipeFoods != null) {
                for (m in recipeFoods!!) {
                    foundFoods.add(m.foods.elementAt(0))
                }
            }
        }
        found.postValue(foundFoods)
        return found
    }


    /**
     * Retrieves a List of Foods for a meal for today with the name provided
     * equal to the string
     *
     * @param name, string
     * @return LiveData<List<MealWithFoodsEntity> a list of MealWithFoodsEntity objects
     */
    fun getFoodInRecipe(name: String): List<RecipeWithFoodsEntity>?{
        var mealFoods: List<RecipeWithFoodsEntity>? = listOf<RecipeWithFoodsEntity>()
        viewModelScope.launch(Dispatchers.IO){
            mealFoods = repository.getFoodInRecipe(name).value
        }
        return mealFoods
    }

    /**
     * Function to initialize a coroutine to retrieve a total number of foods in a recipe with the name
     * and date equal to the string provided
     * @param name, String
     * @return Int
     */
    fun getCount(name: String): Int{
        var count = 0
        viewModelScope.launch(Dispatchers.IO){
            count = repository.getCount(name)
        }
        return count
    }

    /**
     * Function to initialize a coroutine to add a FoodInRecipeEntity object to the database
     * @param item, a FoodInRecipeEntity
     */
    fun insert(item: FoodInRecipeEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(item)
        }
    }

    /**
     * Function to initialize a coroutine to add a list of FoodInRecipeEntity objects to the database
     * @param item, a List<FoodInRecipeEntity>
     */
    fun insert(item: List<FoodInRecipeEntity>){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(item)
        }
    }


    /**
     * Function to initialize a coroutine to update a the food_in_recipe record with the
     * FoodInMealEntity
     * @param item, a FoodInRecipeEntity
     * @return void
     */
    fun update(item: FoodInRecipeEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(item)
        }
    }

    /**
     * Function to initialize a coroutine to delete the food_in_recipe record with the
     * recipe_id equal to the integer provided
     * @param recipe_id, an Integer
     * @return void
     */
    fun delete(recipe_id: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(recipe_id)
        }
    }

    /**
     * Function to initialize a coroutine to delete the food_in_recipe record equal to the FoodInRecipeEntity
     * object provided
     * @param item, an FoodInRecipeEntity
     * @return void
     */
    fun delete(item: FoodInRecipeEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(item)
        }
    }


    /**
     * Function to initialize a coroutine to delete all food_in_recipe records
     * @return void
     */
    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }
}

package com.example.workout_companion.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.RecipeEntity
import com.example.workout_companion.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * Retrieves a list of all meals in the meal table for the current date
     */
    val getAllRecipes: LiveData<List<RecipeEntity>>
    var recipeID = MutableLiveData<Int>()
    var foundRecipes = MutableLiveData<List<RecipeEntity>>()
    /**
     * Meal repository Object
     */
    private val repository: RecipeRepository

    /**
     * Function to initialize the view.
     * Initializes the WCDatabase, repository and the list of all Meal entities
     */
    init {
        val recipeDao = WCDatabase.getInstance(application).recipeDao()
        repository = RecipeRepository(recipeDao)
        getAllRecipes = repository.getAllRecipes
    }

    /**
     * Function to initialize a coroutine to retrieves all recipes
     * from the recipes table where the name is the same as ths string provided
     *
     * @param name, String
     * @return LiveData<List<RecipeEntity>>
     */
    fun getRecipe(name: String){
        viewModelScope.launch(Dispatchers.IO){
            foundRecipes.postValue(repository.getRecipeByName(name).value)
        }
    }


    /**
     * Function to initialize a coroutine to retrieve the id of a recipe based on the name of the recipe
     *
     * @param name, a string equal to the type of meal
     * @return  id, Int
     */
    fun getRecipeID(name: String) {
        viewModelScope.launch(Dispatchers.IO){
            recipeID.postValue(repository.getRecipeId(name))
        }
    }


    /**
     * Function to initialize a coroutine to add a RecipeEntity object to the database
     * @param item, a RecipeEntity
     */
    fun addMeal(item: RecipeEntity){
        viewModelScope.launch(Dispatchers.IO){
                repository.insert(item)
        }
    }

    /**
     * Function to initialize a coroutine to add a meal to the database
     * with the name provided
     * @param name, string
     */
    @SuppressLint("NewApi")
    fun insert(name: String){
        viewModelScope.launch(Dispatchers.IO){
                repository.insert(name)
        }
    }

    /**
     * Function to initialize a coroutine to update a the recipe record with the
     * recipe
     * @param item, a RecipeEntity
     * @return void
     */
    fun update(item: RecipeEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(item)
        }
    }

    /**
     * Function to initialize a coroutine to delete the recipe record with the
     * name equal to the string provided
     * @param name, a String
     * @return void
     */
    fun delete(name: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(name)
        }
    }

    /**
     * Function to initialize a coroutine to delete the recipe record with the
     * recipe
     * @param item, a RecipeEntity
     * @return void
     */
    fun delete(item: RecipeEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(item)
        }
    }


    /**
     * Function to initialize a coroutine to delete all recipes in the recipe table
     * @return void
     */
    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }


}
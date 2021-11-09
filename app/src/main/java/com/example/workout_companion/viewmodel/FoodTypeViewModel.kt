package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.repository.FoodTypeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FoodTypeViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * Retrieves a list of all foods in the food_type table
     */
    val getAllFoods: LiveData<List<FoodTypeEntity>>
    var foodID: Int

    /**
     * FoodType Repoository Object
     */
    private val repository: FoodTypeRepository

    /**
     * Function to initialize the view.
     * Initializes the WCDatabase, repository and the list of all FoodType entities
     */
    init {
        val foodTypeDao = WCDatabase.getInstance(application).foodTypeDao()
        repository = FoodTypeRepository(foodTypeDao)
        getAllFoods = repository.getAllFoods
        foodID = 0
    }

    /**
     * Function to initialize a coroutine to retrieve a list of foods with the name
     * equal to the string provided
     * @param name, String
     * @return List of FoodTypeEntity objects
     */
     fun getFood(name: String): List<FoodTypeEntity>?{
        var food: List<FoodTypeEntity>? = listOf<FoodTypeEntity>()
        viewModelScope.launch(Dispatchers.IO){
            food = repository.getFoodByName(name).value
        }
        return food
    }

    /**
     * Function to initialize a coroutine to retrieve the id for a food
     * @param  item, FoodTypeEntity
     * @return  Int total number of rows found
     */
     fun getId(item: FoodTypeEntity){
        viewModelScope.launch(Dispatchers.IO){
            foodID = repository.getId(item)
        }
    }

    /**
     * Function to initialize a coroutine to retrieve a total number of foods with the name
     * equal to the string provided
     * @param name, String
     * @return List of FoodTypeEntity objects
     */
     fun getCount(name: String): Int{
        var count = 0
        viewModelScope.launch(Dispatchers.IO){
            count = repository.getCount(name)
        }
        return count
    }

    /**
     * Function to initialize a coroutine to retrieve a total number of foods in the food_type table
     * @return Int, number of rows
     */
     fun getCount(): Int{
        var count = 0
        viewModelScope.launch(Dispatchers.IO){
            count = repository.getCount()
        }
        return count
    }

    /**
     * Function to initialize a coroutine to add a FoodTypeEntity object to the database
     * @param item, a FoodTypeEntity
     */
    fun addFoodType(item: FoodTypeEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(item)
        }
    }

    /**
     * Function to initialize a coroutine to add a list of FoodTypeEntity objects to the database
     * @param item, List<FoodTypeEntity>
     */
     fun addFoodType(item: List<FoodTypeEntity>){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(item)
        }
    }

    /**
     * Function to initialize a coroutine to update a the food_type record with the
     * FoodTypeEntity
     * @param item, a FoodTypeEntity
     * @return void
     */
    fun update(item: FoodTypeEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(item)
        }
    }

    /**
     * Function to initialize a coroutine to delete the food_type record with the
     * FoodTypeEntity
     * @param item, a FoodTypeEntity
     * @return void
     */
    fun delete(item: FoodTypeEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(item)
        }
    }

    /**
     * Function to initialize a coroutine to delete all food_type records
     * @return void
     */
    fun delete(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }
}
/**
 * FoodTypeViewModel Factory class that is used to initialize the FoodTypeViewModel
 * @param application context
 * @return ViewModelProvider.Factory
 */
class FoodTypeViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory{
    /**
     * Method to create an instance of the UserModelView
     */
    override fun <T: ViewModel?> create(modelClass: Class<T>): T{
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(FoodTypeViewModel::class.java)) {
            return FoodTypeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}
package com.example.workout_companion.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.*
import com.example.workout_companion.repository.CurrentUserGoalRepository
import com.example.workout_companion.repository.FoodInMealRepository
import com.example.workout_companion.repository.FoodTypeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.time.LocalDate

class FoodInMealViewModel(application: Application) : AndroidViewModel(application) {



    /**
     * FoodType Repoository Object
     */
    private val repository: FoodInMealRepository
    var foundFoods = MutableLiveData<List<MealWithFoodsEntity>>()

    /**
     * Function to initialize the view.
     * Initializes the WCDatabase, repository and the list of all FoodType entities
     */
    init {
        val foodInMealDao = WCDatabase.getInstance(application).foodInMealDao()
        repository = FoodInMealRepository(foodInMealDao)
    }


    /**
     * Retrieves a List of Foods for a meal with the meal_id
     * equal to the integer provided
     *
     * @return LiveData<List<MealWithFoodsEntity> a list of MealWithFoodsEntity objects
     */
    fun getFoodInMeal(meal_id: Int): LiveData<List<MealWithFoodsEntity>>{
        val mealFoods= MutableLiveData<List<MealWithFoodsEntity>>()
        viewModelScope.launch(Dispatchers.IO){
            mealFoods.postValue(repository.getFoodInMeal(meal_id).value)
        }
        return mealFoods
    }

    /**
     * Retrieves a List of Foods for a meal with the meal_id
     * equal to the integer provided
     *
     * @parm meal_id, Int
     * @return List<FoodTypeEntity> a list of FoodTypeEntity objects
     */
    fun getFoodsInMeal(meal_id: Int): List<FoodTypeEntity> {
        var mealFoods: List<MealWithFoodsEntity>?
        val foundFoods: MutableList<FoodTypeEntity> = mutableListOf()

        viewModelScope.launch(Dispatchers.IO) {
            mealFoods = repository.getFoodInMeal(meal_id).value
            if (mealFoods != null) {
                for (m in mealFoods!!) {
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
     * @param type, String
     * @param date, LocalDate
     * @return List<FoodTypeEntity> a list of FoodTypeEntity objects
     */
    fun getFoodsInMeal(type: String, date: LocalDate): LiveData<List<FoodTypeEntity>> {
        var mealFoods: List<MealWithFoodsEntity>?
        val foundFoods: MutableList<FoodTypeEntity> = mutableListOf()
        val found= MutableLiveData<List<FoodTypeEntity>>()

        viewModelScope.launch(Dispatchers.IO) {
            mealFoods = repository.getFoodInMeal(type, date).value
            if (mealFoods != null) {
                for (m in mealFoods!!) {
                    foundFoods.add(m.foods.elementAt(0))
                }
            }
        }
        found.postValue(foundFoods)
        return found
    }

    /**
     * Retrieves a List of Foods for a meal with the name and date
     * equal to the string and Local Date provided
     *
     * @param type, string
     * @param date, LocalDate
     * @return LiveData<List<MealWithFoodsEntity> a list of MealWithFoodsEntity objects
     */
    fun getFoodInMeal(type: String, date: LocalDate): List<MealWithFoodsEntity>?{
        var mealFoods: List<MealWithFoodsEntity>? = listOf<MealWithFoodsEntity>()
        viewModelScope.launch(Dispatchers.IO){
            mealFoods = repository.getFoodInMeal(type, date).value
        }
        return mealFoods
    }

    /**
     * Retrieves a List of Foods for a meal for today with the name provided
     * equal to the string
     *
     * @param type, string
     * @return LiveData<List<MealWithFoodsEntity> a list of MealWithFoodsEntity objects
     */
    fun getFoodInMeal(type: String){
        val today = LocalDate.now()
        viewModelScope.launch(Dispatchers.IO){
            foundFoods.postValue(repository.getFoodInMeal(type, today).value)
        }
    }

    /**
     * Function to initialize a coroutine to retrieve a total number of foods in today's meal with the name
     * equal to the string provided
     * @param name, String
     * @return Int
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCount(name: String): Int{
        var count = 0
        viewModelScope.launch(Dispatchers.IO){
            count = repository.getCount(name)
        }
        return count
    }

    /**
     * Function to initialize a coroutine to retrieve a total number of foods in today's meal with the name
     * and date equal to the string and LocalDate provided
     * @param type, String
     * @param date, LocalDate
     * @return Int
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCount(type: String, date: LocalDate): Int{
        var count = 0
        viewModelScope.launch(Dispatchers.IO){
            count = repository.getCount(type, date)
        }
        return count
    }

    /**
     * Function to initialize a coroutine to add a FoodInMealEntity object to the database
     * @param item, a FoodInMealEntity
     */
    fun insert(item: FoodInMealEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(item)
        }
    }

    /**
     * Function to initialize a coroutine to add a list of FoodInMealEntity objects to the database
     * @param item, a List<FoodInMealEntity>
     */
    fun insert(item: List<FoodInMealEntity>){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(item)
        }
    }


    /**
     * Function to initialize a coroutine to update a the food_in_meal record with the
     * FoodInMealEntity
     * @param item, a FoodInMealEntity
     * @return void
     */
    fun update(item: FoodInMealEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(item)
        }
    }

    /**
     * Function to initialize a coroutine to delete the food_in_meal record with the
     * meal_id equal to the integer provided
     * @param meal_id, an Integer
     * @return void
     */
    fun delete(meal_id: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(meal_id)
        }
    }

    /**
     * Function to initialize a coroutine to delete the food_in_meal record equal to the food_in_meal
     * object provided
     * @param item, an FoodInMealEntity
     * @return void
     */
    fun delete(item: FoodInMealEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(item)
        }
    }


    /**
     * Function to initialize a coroutine to delete all food_in_meal records
     * @return void
     */
    fun deleteAll(){
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
class FoodInMealViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory{
    /**
     * Method to create an instance of the UserModelView
     */
    override fun <T: ViewModel?> create(modelClass: Class<T>): T{
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(FoodInMealViewModel::class.java)) {
            return FoodInMealViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}
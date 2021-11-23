package com.example.workout_companion.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.time.LocalDate

class MealViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * Retrieves a list of all meals in the meal table for the current date
     */
    val getAllMeals: LiveData<List<MealEntity>>
    var mealId = MutableLiveData<Int>()
    /**
     * Meal repository Object
     */
    private val repository: MealRepository

    /**
     * Function to initialize the view.
     * Initializes the WCDatabase, repository and the list of all Meal entities
     */
    init {
        val mealDao = WCDatabase.getInstance(application).mealDao()
        repository = MealRepository(mealDao)
        getAllMeals = repository.getAllMeals
    }

    /**
     * Function to initialize a coroutine to retrieves all meals
     * from meal table where the date is the same as the
     * LocalDate provided
     *
     * @param date, LocalDate
     * @return LiveData<List<MealEntity>>
     */
    fun getMealsByDate(date: LocalDate): List<MealEntity>?{
        var meal: List<MealEntity>? = listOf<MealEntity>()
        viewModelScope.launch(Dispatchers.IO){
            meal = repository.getMealsByDate(date).value
        }
        return meal
    }

    /**
     * Function to initialize a coroutine to retrieves all meals
     * from meal table where the date is the same as the
     * LocalDate provided
     *
     * @param name, String
     * @return LiveData<List<MealEntity>>
     */
    fun getMealsByName(name: String): List<MealEntity>?{
        var meal: List<MealEntity>? = listOf<MealEntity>()
        viewModelScope.launch(Dispatchers.IO){
            meal = repository.getMealByName(name).value
        }
        return meal
    }

    /**
     * Function to initialize a coroutine to retrieve a total number of meals for the current date
     *
     * @return Int
     */
    fun getCount(): Int {
        var count: Int = 0
        viewModelScope.launch(Dispatchers.IO){
            count = repository.getCount()
        }
        return count
    }

    /**
     * Function to initialize a coroutine to retrieve the id of a meal based on the name of the meal
     * where the date is today
     *
     * @param name, a string equal to the type of meal
     * @return  id, Int
     */
    fun getMealId(name: String) {
        viewModelScope.launch(Dispatchers.IO){
           mealId.postValue(repository.getMealId(name))
        }
    }

    /**
     * Function to initialize a coroutine to retrieve the id of a meal based on the name of the meal
     * and the date equal to the string and LocalDate provided
     *
     * @param name, a string equal to the type of meal
     * @param date, LocalDate
     * @return  id, Int
     */
    fun getMealId(name: String, date: LocalDate): Int {
        var mealId: Int = 0
        viewModelScope.launch(Dispatchers.IO){
            mealId = repository.getMealId(name, date)
        }
        return mealId
    }



    /**
     * Function to initialize a coroutine to add a MealEntity object to the database
     * @param item, a MealEntity
     */
    fun addMeal(item: MealEntity){
        viewModelScope.launch(Dispatchers.IO){
            val found = repository.checkIfMealExists(item.type)
            if(!found){
                repository.insert(item)
            }
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
            val found = repository.checkIfMealExists(name)
            if(!found){
                repository.insert(name)
            }
        }
    }

    /**
     * Function to initialize a coroutine to update a the meal record with the
     * meal
     * @param item, a meal
     * @return void
     */
    fun update(item: MealEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(item)
        }
    }

    /**
     * Function to initialize a coroutine to add the calories, carbs, protein, fat totals in a meal
     * in the meal table
     *
     * @param name, String
     * @param calories, in grams
     * @param carbohydrates, in grams
     * @param protein, in grams
     * @param fat, in grams
     * @return void
     */
    @SuppressLint("NewApi")
    fun addToMeal(name: String, calories: Double, carbohydrates: Double,
                  protein: Double, fat: Double){
        viewModelScope.launch(Dispatchers.IO){
            repository.addToMeal(name, calories, carbohydrates, protein, fat)
        }
    }

    /**
     * Function to initialize a coroutine to subtract the calories, carbs, protein, fat totals in a meal
     * in the meal table
     *
     * @param name, String
     * @param calories, in grams
     * @param carbohydrates, in grams
     * @param protein, in grams
     * @param fat, in grams
     * @return void
     */
    @SuppressLint("NewApi")
    fun subtractFromMeal(name: String, calories: Double, carbohydrates: Double,
                         protein: Double, fat: Double){
        viewModelScope.launch(Dispatchers.IO){
            repository.subtractFromMeal(name, calories, carbohydrates, protein, fat)
        }
    }

    /**
     * Function to initialize a coroutine to delete the meal record with the
     * meal
     * @param item, a MealEntity
     * @return void
     */
    fun delete(item: MealEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(item)
        }
    }

    /**
     * Function to initialize a coroutine to delete all meals in the meal table for the current date
     * @return void
     */
    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }

    /**
     * Function to initialize a coroutine to delete all meals in the meal table for the provided
     * date
     * @return void
     */
    fun deleteAll(date: LocalDate){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll(date)
        }
    }
}
/**
 * FoodTypeViewModel Factory class that is used to initialize the FoodTypeViewModel
 * @param application context
 * @return ViewModelProvider.Factory
 */
class MealViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory{
    /**
     * Method to create an instance of the UserModelView
     */
    override fun <T: ViewModel?> create(modelClass: Class<T>): T{
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MealViewModel::class.java)) {
            return MealViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}
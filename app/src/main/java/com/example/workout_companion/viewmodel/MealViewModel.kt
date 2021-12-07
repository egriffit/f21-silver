package com.example.workout_companion.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.AllMealsInDay
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.entity.MealWithFoodsEntity
import com.example.workout_companion.repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.workout_companion.sampleData.emptyMealEntity
import com.example.workout_companion.repository.FoodInMealRepository
import kotlinx.coroutines.*
import java.time.LocalDate


class MealViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * Retrieves a list of all meals in the meal table for the current date
     */
    val getTodaysMeals: LiveData<List<MealEntity>>
    var mealId = MutableLiveData<Int>()
    val foundMeal = MutableLiveData<List<MealEntity>>()

    /**
     * Meal repository Object
     */
    private val repository: MealRepository

    /**
     * Food In Meal Repository Object
     */
    private val foodInMealRepository: FoodInMealRepository
    /**
     * Function to initialize the view.
     * Initializes the WCDatabase, repository and the list of all Meal entities
     */
    init {
        val mealDao = WCDatabase.getInstance(application).mealDao()
        repository = MealRepository(mealDao)
        getTodaysMeals = repository.getTodaysMeals
        val foodInMealDao = WCDatabase.getInstance(application).foodInMealDao()
        foodInMealRepository = FoodInMealRepository(foodInMealDao, mealDao)
    }

    /**
     * Function to initialize a coroutine to retrieves all meals
     * from meal table where the date is the same as the
     * LocalDate provided
     *
     * @param date, LocalDate
     * @return LiveData<List<MealEntity>>
     */
    fun getMealsByDate(date: LocalDate){
        viewModelScope.launch(Dispatchers.IO){
            foundMeal.postValue(repository.getMealsByDate(date))
        }
    }

    /**
     * Function to initialize a coroutine to retrieves all meals
     * from meal table where the date is the same as the
     * LocalDate provided
     *
     * @param name, String
     * @return LiveData<List<MealEntity>>
     */
    fun getMealsByName(name: String){
        viewModelScope.launch(Dispatchers.IO){
            foundMeal.postValue(repository.getMealByName(name))
        }
    }

    /**
     * Function to initialize a coroutine to retrieve a total number of meals for the current date
     *
     * @return Int
     */
    fun getCount(): Int {
        var count = 0
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
        var mealId = 0
        viewModelScope.launch(Dispatchers.IO){
            mealId = repository.getMealId(name, date)
        }
        return mealId
    }

    /**
     * Retrieve the daily calories and macronutrient totals
     *
     * @return  AllMealsInDay
     */
    fun calcDailyTotal(): AllMealsInDay{
            return repository.calcDailyTotal()
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

    fun emptyMeal(item: MealEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.emptyMeal(item)
        }
    }

    /**
     * Function to initialize a coroutine to add the calories, carbs, protein, fat totals in a meal
     * in the meal table
     *
     * @param meal, name of meal
     * @param food, FoodTypeEntity being added
     * @param servings, servings
     * @return void
     */
    fun addToMeal(meal: MealEntity, food: FoodTypeEntity, servings: Double){
        viewModelScope.launch(Dispatchers.IO){
            repository.addToMeal(meal,food, servings)
        }
    }

    /**
     * Function to initialize a coroutine to add the calories, carbs, protein, fat totals in a meal
     * in the meal table
     *
     * @param meal, name of meal
     * @param food, FoodTypeEntity being added
     * @param servings, servings
     * @return void
     */
    fun calculateMeal(meal: MealEntity){
        viewModelScope.launch(Dispatchers.IO){
            var foodsInMeals: List<MealWithFoodsEntity> = listOf()
            val job1: Job = launch(Dispatchers.IO){
                foodsInMeals = foodInMealRepository.getFoodInMeal(meal.type, LocalDate.now())
            }
            job1.join()
            val job2: Job = launch(Dispatchers.IO){
                repository.emptyMeal(meal)
            }
            job2.join()
            val job3: Job = launch(Dispatchers.IO){
                foodsInMeals.forEach{ food ->
                    val servings = food.food_in_meal.servings
                    repository.addToMeal(meal, food.foods.elementAt(0), servings)
                }
            }
            job3.join()
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
    fun subtractFromMeal(meal: MealEntity, food: FoodTypeEntity, servings: Double){
        viewModelScope.launch(Dispatchers.IO){
            repository.subtractFromMeal(meal, food, servings)
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
package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.workout_companion.api.edamam.EdamamAPI
import com.example.workout_companion.api.edamam.Properties
import com.example.workout_companion.api.edamam.edamamApi
import com.example.workout_companion.api.edamam.entities.EdamamFood
import com.example.workout_companion.api.edamam.entities.Food
import com.example.workout_companion.api.edamam.entities.FoodData
import com.example.workout_companion.api.edamam.repository.EdamamRepository
import com.example.workout_companion.api.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalArgumentException

/***
 * ViewModel class for the CurrentUserGoalEntity object
 * @param application, context
 * @return AndroidViewModel
 */
class EdamamViewModel(application: Application): AndroidViewModel(application) {
    private val api: EdamamAPI
    /**
     * UserRepository Object
     */
    private val repository: EdamamRepository

    /**
     * Function to initialize the view.
     * Initializes the Edamam API and repository
     */
    init {
        api = edamamApi(Properties.base_url)
        repository = EdamamRepository(api)
    }

    /**
     * Function to initialize a coroutine to retrieve the Edamam to the database
     * @param item, a CurrentUserGoalEntity
     */
    fun getFood(food: String): EdamamFood? {
        var found: EdamamFood? = null
        viewModelScope.launch(Dispatchers.IO){
            found = repository.getFood(food).data
        }
        return found
    }


}
/**
 * EdamamViewModel Factory class that is used to initialize the EdamamViewModel
 * @param application context
 * @return ViewModelProvider.Factory
 */
class EdamamViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory{
    /**
     * Method to create an instance of the EdamamViewModel
     */
    override fun <T: ViewModel?> create(modelClass: Class<T>): T{
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(EdamamViewModel::class.java)) {
            return EdamamViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}

fun getFoodsFromResponse(response: EdamamFood): List<FoodData>{
    var name: String = ""
    var calories: Int = 0
    var carbohydrates: Double = 0.0
    var protein: Double = 0.0
    var fat: Double = 0.0
    var foodId: String = ""
    //var servings = mutableMapOf<String, Double>()
    var foodList: MutableList<FoodData> = mutableListOf()
    response.hints.forEach{
        name = it.food.label
        calories = it.food.nutrients.ENERC_KCAL.toInt()
        carbohydrates = it.food.nutrients.CHOCDF
        protein = it.food.nutrients.PROCNT
        fat = it.food.nutrients.FAT
        foodId = it.food.foodId
        foodList.add(FoodData(name,foodId, calories, carbohydrates, protein, fat))
    }
    return foodList
}

fun parseNext(url: String): String{
    var session: String = ""
    var sessionRegex = "[?&]session=([^&]+).*$".toRegex()
    val matches = sessionRegex.findAll(url)
    matches.forEach{m->
        session = m.groupValues[1]
    }
    return session
}

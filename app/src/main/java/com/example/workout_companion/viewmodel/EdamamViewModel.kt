package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.workout_companion.api.edamam.EdamamAPI
import com.example.workout_companion.api.edamam.Properties
import com.example.workout_companion.api.edamam.repository.EdamamRepository
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
        api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Properties.base_url)
            .build()
            .create(EdamamAPI::class.java)
        repository = EdamamRepository(api)
    }

    /**
     * Function to initialize a coroutine to retrieve the Edamam to the database
     * @param item, a CurrentUserGoalEntity
     */
    fun getFood(food: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.getFood(food)
        }
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
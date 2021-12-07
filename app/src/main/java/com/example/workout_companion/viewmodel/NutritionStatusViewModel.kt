package com.example.workout_companion.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.*
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.entity.NutritionStatusEntity
import com.example.workout_companion.entity.WorkoutEntity
import com.example.workout_companion.enumeration.NutritionStatusEnum
import com.example.workout_companion.repository.NutritionStatusRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * A View Model for the NutritionStatusEntity table
 *
 * @see NutritionStatusEntity
 * @see ViewModel
 *
 * @property repository the repository that handles access to the database.
 */
class NutritionStatusViewModel (application: Application) : AndroidViewModel(application) {
    /**
     * A LiveData object containing all nutrition statuses in the database.
     */
    val allNutritionStatuses: LiveData<List<NutritionStatusEntity>>

    /**
     * A LiveData object containing a nutrition statuses from the database.
     */
    val currentStatus: LiveData<NutritionStatusEntity>

    private val repository: NutritionStatusRepository

    init {
        val nutritionStatusDao = WCDatabase.getInstance(application).nutritionStatusDao()
        repository = NutritionStatusRepository(nutritionStatusDao)
        allNutritionStatuses = repository.getAllStatuses
        currentStatus = repository.getCurrentStatus(1)
    }

    /**
     * Function to initialize a coroutine to retrieve nutrition status
     * from nutrition status table where the date is the same as the
     * LocalDate provided
     *
     * @param date, LocalDate
     * @return NutritionStatusEntity
     */
    fun getStatusByDate(date: LocalDate): LiveData<NutritionStatusEntity> {
        return repository.getStatusByDate(date)
    }

    /**
     * Function to initialize a coroutine to retrieve nutrition status
     * from nutrition status table where the id is the same as the
     * id provided
     *
     * @param id, Int
     * @return NutritionStatusEntity
     */
    fun getCurrentStatus(id: Int): LiveData<NutritionStatusEntity> {
        return repository.getCurrentStatus(id)
    }

    /**
     * Function to initialize a coroutine to add a nutrition status entity to the database
     * with the status provided
     * @param status, string
     */
    fun insert(status: String, date: LocalDate){
        viewModelScope.launch(Dispatchers.IO){
            // If status does not exist, insert. Otherwise, update.
            if(repository.getCount(status, date) > 0){
                /** TODO: Fix this line so that a new NutritionStatusEntity object is initialized. */
//                val statusUpdate: NutrititionStatusEntity(status, date)
//                repository.insert(statusUpdate)
            }else{
//                repository.update(statusUpdate)
            }
        }
    }

    /**
     * Function to initialize a coroutine to update a nutrition status entity record with the
     * nutrition status
     * @param item, a NutritionStatusEntity
     * @return void
     */
    fun update(item: NutritionStatusEntity) {
        viewModelScope.launch(Dispatchers.IO){
            repository.update(item)
        }
    }
}
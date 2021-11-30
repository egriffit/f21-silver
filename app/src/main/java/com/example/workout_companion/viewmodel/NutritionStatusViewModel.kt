package com.example.workout_companion.viewmodel

import androidx.lifecycle.*
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.NutritionStatusEntity
import com.example.workout_companion.entity.WorkoutEntity
import com.example.workout_companion.repository.NutritionStatusRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

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
     * Function to initialize a coroutine to retrieves nutrition status
     * from nutrition status table where the date is the same as the
     * LocalDate provided
     *
     * @param date, LocalDate
     * @return NutritionStatusEntity
     */
    fun getStatusByDate(date: LocalDate): LiveData<NutritionStatusEntity> {
        return repository.getStatusByDate(date)
    }

    fun insertStatus(status: String) {
        viewModelScope.launch(Dispatchers.IO){
            // If status does not exist, insert. Otherwise, update.
            if(repository.getCount(enumValues<LiveData<NutritionStatusEntity>()) > 0){
                val today: LocalDate.now()
                val status: NutrititionStatusEntity(status, today)
                repository.insert(status)
            }else{
                repository.update(status)
            }
        }
    }

    fun update(status: string){
        viewModelScope.launch(Dispatchers.IO){
            val today: LocalDate.now()
            val status: NutrititionStatusEntity(status, today)
            repository.update(status)
        }
    }

    fun getStatus(){
        viewModelScope.launch(Dispatchers.IO){
            currentStatus.postValue(repository.getStatus())
        }
    }
}
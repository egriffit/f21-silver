package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkDayEntity
import com.example.workout_companion.repository.FrameworkDayRepository
import com.example.workout_companion.repository.FrameworkTypeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FrameworkDayViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * The repository for accessing the database
     */
    private val repository: FrameworkDayRepository

    init {
        val dao = WCDatabase.getInstance(application).frameworkDayDao()
        repository = FrameworkDayRepository(dao)
    }

    /**
     * Get all days within a workout framework
     *
     * @property framework_type_id  the primary key of the workout framework.
     *
     * @return a LiveData List of all days within the framework
     */
    fun getAllFrameworkDays(framework_type_id: Int) : LiveData<List<FrameworkDayEntity>> {
        return repository.getAllFrameworkDays(framework_type_id)
    }

    /**
     * Add a day to a framework
     *
     * @property day    the day to add.
     */
    fun addFrameworkDay(day: FrameworkDayEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFrameworkDay(day)
        }
    }

    /**
     * Update a day in a framework
     *
     * @property day    the day to update.
     */
    fun updateFrameworkDay(day: FrameworkDayEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFrameworkDay(day)
        }
    }

    /**
     * Delete a day from a framework
     *
     * @property day    the day to remove.
     */
    fun deleteFrameworkDay(day: FrameworkDayEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFrameworkDay(day)
        }
    }
}

/**
 * Factory for creating a [FrameworkDayViewModel]
 *
 * @property application    the application.
 */
class FrameworkDayViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    /**
     * Create a [FrameworkDayViewModel]
     *
     * @return a [FrameworkDayViewModel]
     */
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(FrameworkDayViewModel::class.java)) {
            return FrameworkDayViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}
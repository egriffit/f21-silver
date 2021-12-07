package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workout_companion.database.FRAMEWORK_DAYS
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkDayEntity
import com.example.workout_companion.repository.FrameworkDayRepository
import com.example.workout_companion.repository.FrameworkTypeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FrameworkDayViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * A list of all framework days for a framework
     */
    val frameworkDays = MutableLiveData<List<FrameworkDayEntity>>()

    /**
     * The repository for accessing the database
     */
    private val repository: FrameworkDayRepository

    init {
        val dao = WCDatabase.getInstance(application).frameworkDayDao()
        repository = FrameworkDayRepository(dao)
    }

    /**
     * Get all days within a workout framework and update frameworkDays livedata list
     *
     * @property framework_type_id  the primary key of the workout framework.
     */
    fun getAllFrameworkDays(framework_type_id: Int){
        viewModelScope.launch(Dispatchers.IO){
            frameworkDays.postValue(repository.getAllFrameworkDays(framework_type_id))
        }
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

    /**
     * Load default frameworks
     */
    fun loadFrameworkDays() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFrameworkDay(FRAMEWORK_DAYS)
        }
    }
}
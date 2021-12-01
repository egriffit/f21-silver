package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.dao.FrameworkComponentDao
import com.example.workout_companion.database.FRAMEWORK_COMPONENTS
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkComponentEntity
import com.example.workout_companion.repository.FrameworkComponentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FrameworkComponentViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * value for found components for a day
     */
    val components = MutableLiveData<List<FrameworkComponentEntity>>()
    /**
     * The repository for framework components
     */
    private val repository: FrameworkComponentRepository

    init {
        val dao = WCDatabase.getInstance(application).frameworkComponentDao()
        repository = FrameworkComponentRepository(dao)
    }

    /**
     * Get all components in a framework day
     *
     * @property day_id the primary key of the framework day.
     *
     * @return a LiveData List of all components in a day.
     */
    fun getAllComponentsOfDay(day_id: Int) {
        components.postValue(repository.getAllComponentsOfDay(day_id))
    }

    /**
     * Add a component to a framework day
     *
     * @property component  the component to add.
     */
    fun addFrameworkComponent(component: FrameworkComponentEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFrameworkComponent(component)
        }
    }

    /**
     * Updates an existing component
     *
     * @property component  the component to update.
     */
    fun updateFrameworkComponent(component: FrameworkComponentEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFrameworkComponent(component)
        }
    }

    /**
     * Deletes a component from a day
     *
     * @property component  the component to remove.
     */
    fun deleteFrameworkComponent(component: FrameworkComponentEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFrameworkComponent(component)
        }
    }

    /**
     * Loads default framework components into database
     */
    fun loadFrameworkComponents(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFrameworkComponent(FRAMEWORK_COMPONENTS)
        }
    }
}
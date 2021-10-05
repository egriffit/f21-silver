package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.dao.FrameworkComponentDao
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkComponentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FrameworkComponentViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * The DAO for framework components
     */
    private val dao = WCDatabase.getInstance(application).frameworkComponentDao()

    /**
     * Get all components in a framework day
     *
     * @property day_id the primary key of the framework day.
     *
     * @return a LiveData List of all components in a day.
     */
    fun getAllComponentsOfDay(day_id: Int) : LiveData<List<FrameworkComponentEntity>> {
        return dao.getAllComponentsOfDay(day_id)
    }

    /**
     * Add a component to a framework day
     *
     * @property component  the component to add.
     */
    fun addFrameworkComponent(component: FrameworkComponentEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.addFrameworkComponent(component)
        }
    }

    /**
     * Updates an existing component
     *
     * @property component  the component to update.
     */
    fun updateFrameworkComponent(component: FrameworkComponentEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.updateFrameworkComponent(component)
        }
    }

    /**
     * Deletes a component from a day
     *
     * @property component  the component to remove.
     */
    fun deleteFrameworkComponent(component: FrameworkComponentEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteFrameworkComponent(component)
        }
    }
}

/**
 * Factory for creating a [FrameworkComponentViewModel]
 *
 * @property application    the main application.
 */
class FrameworkComponentViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    /**
     * Create a [FrameworkComponentViewModel]
     *
     * @param T the view model class.
     * @property modelClass the class to convert.
     *
     * @return  a view model originally as [FrameworkComponentViewModel]
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(FrameworkComponentViewModel::class.java)) {
            return FrameworkComponentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}
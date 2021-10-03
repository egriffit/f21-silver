package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkTypeEntity
import com.example.workout_companion.repository.FrameworkTypeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 * A ViewModel for the framework_type table
 *
 * @param application   the Application
 */
class FrameworkTypeViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * A list of all frameworks within the database
     */
    val readAll: LiveData<List<FrameworkTypeEntity>>

    /**
     * A repository for accessing the framework_type database
     */
    private val repository: FrameworkTypeRepository

    init {
        val dao = WCDatabase.getInstance(application).frameworkTypeDao()
        repository = FrameworkTypeRepository(dao)
        readAll = repository.allFrameworks
    }

    /**
     * Gets all frameworks with workouts_per_week <= [maxWorkouts]
     *
     * @property maxWorkouts    the maximum number of workouts per week to filter by.
     *
     * @return a LiveData List of all frameworks meeting the criteria
     */
    fun getFrameworksWithinMaxWorkouts(maxWorkouts: Int) : LiveData<List<FrameworkTypeEntity>> {
        return repository.getFrameworksWithinMaxWorkouts(maxWorkouts)
    }

    /**
     * Gets all frameworks with the given goal
     *
     * @property goal_id    the primary key of the goal to filter by.
     *
     * @return a LiveData List of all frameworks meeting the criteria
     */
    fun getFrameworksWithGoal(goal_id: Int) : LiveData<List<FrameworkTypeEntity>> {
        return repository.getFrameworksWithGoal(goal_id)
    }

    /**
     * Adds a framework to the database
     *
     * @property framework  the framework to add to the database.
     */
    fun addFramework(framework: FrameworkTypeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFramework(framework)
        }
    }

    /**
     * Deletes a framework from the database
     *
     * @property framework  the framework to delete from the database.
     */
    fun deleteFramework(framework: FrameworkTypeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFramework(framework)
        }
    }
}

/**
* FrameworkTypeViewModel Factory class that is used to initialize the FrameworkTypeViewModel
 *
* @param Application context
*/
class FrameworkTypeViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    /**
     * Method to create an instance of the FrameworkTypeViewModel
     */
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(FrameworkTypeViewModel::class.java)) {
            return FrameworkTypeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}
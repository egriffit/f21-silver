package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.database.FRAMEWORK_TYPES
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkTypeEntity
import com.example.workout_companion.entity.FrameworkWithGoalEntity
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
     * Framework id
     */
    val id =  MutableLiveData<Int>()
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

    fun getFrameworkIdByName(name: String, goal: String){
        viewModelScope.launch(Dispatchers.IO){
            id.postValue(repository.getFrameworkIdByName(name, goal))
        }
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
     * Gets all frameworks with the given goal name
     *
     * @property goal    the name of the goal
     *
     * @return a LiveData List of all FrameworkWithGoalEntity objects meeting the criteria
     */
    fun getFrameworkByGoalName(goal: String) : LiveData<List<FrameworkWithGoalEntity>> {
        return repository.getFrameworkByGoalName(goal)
    }

    /**
     * Gets all frameworks with the given goal and within the number
     * of workouts equal to or fewer than the maxWorkouts provided
     *
     * @property goal    the name of the goal
     * @Property maxWorkouts the max number of workouts a user will do
     *
     * @return a LiveData List of all FrameworkWithGoalEntity objects meeting the criteria
     */
    fun getFrameworksWithGoalNameWithinMaxWorkouts(goal: String, maxWorkouts: Int) : LiveData<List<FrameworkWithGoalEntity>> {
        return repository.getFrameworksWithGoalNameWithinMaxWorkouts(goal, maxWorkouts)
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

    /**
     * Function to load goals in the database
     *
     */
    fun loadFrameworks() {
        viewModelScope.launch(Dispatchers.IO){
            repository.addFramework(FRAMEWORK_TYPES)
        }
    }
}
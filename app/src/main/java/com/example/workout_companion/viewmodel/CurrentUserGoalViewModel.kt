package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.dao.CurrentUserGoalDao
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.CurrentNutritionPlanAndFrameworkEntity
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.repository.CurrentUserGoalRepository
import com.example.workout_companion.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import java.lang.IllegalArgumentException

/***
* ViewModel class for the CurrentUserGoalEntity object
* @parm Context, application context
* @return AndroidViewModel
*/
class CurrentUserGoalViewModel(application: Application): AndroidViewModel(application) {
    /**
     * Retrieves a list of all UserEntities in the users table
     */
    val getCurrentGoals: LiveData<CurrentNutritionPlanAndFrameworkEntity>

    /**
     * UserRepository Object
     */
    private val repository: CurrentUserGoalRepository

    /**
     * Function to initialize the view.
     * Initializes the WCDatabase, repository and the list of all user entities
     */
    init {
        val currentUserGoalDao = WCDatabase.getInstance(application).currentUserDao()
        repository = CurrentUserGoalRepository(currentUserGoalDao)
        getCurrentGoals = repository.getCurrentUserGoals()
    }

}
/**
 * UserViewModel Factory class that is used to initialize the UserViewModel
 * @param Application context
 * @return ViewModelProvider.Factory
 */
class CurrentUserGoalViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory{
    /**
     * Method to create an instance of the UserModelView
     */
    override fun <T: ViewModel?> create(modelClass: Class<T>): T{
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(CurrentUserGoalViewModel::class.java)) {
            return CurrentUserGoalViewModel(application) as T
        }
        throw IllegalArgumentException("Unkown View Model Class")
    }
}
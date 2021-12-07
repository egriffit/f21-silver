package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.CurrentNutritionPlanAndFrameworkEntity
import com.example.workout_companion.entity.CurrentUserGoalEntity
import com.example.workout_companion.repository.CurrentUserGoalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/***
* ViewModel class for the CurrentUserGoalEntity object
* @param application, context
* @return AndroidViewModel
*/
class CurrentUserGoalViewModel(application: Application): AndroidViewModel(application) {
    /**
     * Retrieves a list of all UserEntities in the users table
     */
    val getCurrentGoals: LiveData<CurrentNutritionPlanAndFrameworkEntity>
    val currentGoal: LiveData<CurrentUserGoalEntity>
    val getCurrentGoalIds: LiveData<CurrentUserGoalEntity>
    var currentGoalExists = MutableLiveData<Boolean>()

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
        getCurrentGoals = repository.getCurrentUserGoals
        getCurrentGoalIds = repository.getCurrentGoalIds
        currentGoal = repository.currentGoal
    }

    /**
     * Check if a user goal exists in the model
     */
    fun checkIfExists() = viewModelScope.launch(Dispatchers.IO) {
        currentGoalExists.postValue(repository.currentGoalExists())
    }

    /**
     * Function to initialize a coroutine to add a CurrentUserGoalEntity to the database
     * @param item, a CurrentUserGoalEntity
     */
    fun addCurrentUserGoal(item: CurrentUserGoalEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.addCurrentUserGoals(item = item)
    }

    /**
     * Function to initialize a coroutine to update a the current_user_goal record with the
     * nutrition_plan_type id that has the calories, protein, fat, and carbohydrates associated
     * with it.
     * @param calorie, a double
     * @param protein, a double
     * @param fat, a double
     * @param carbohydrate, a double
     * @return void
     */
    fun updateNutritionPlan(calorie: Double, carbohydrate: Double, protein: Double, fat: Double) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNutritionPlan(calorie, carbohydrate, protein, fat)
    }

    /**
     * Function to initialize a coroutine to update a the current_user_goal record with the
     * nutrition_plan_type_id provided.
     *
     * @param nutrition_plan_type_id, a String
     * @return void
     */
    fun updateNutritionPlanID(nutrition_plan_type_id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNutritionPlanID(nutrition_plan_type_id)
    }

    /**
     * Function to initialize a coroutine to update a the current_user_goal record with the
     * framework_type_id tied to the name of the framework in the framework_type table.
     *
     * @param framework_type, a String
     * @return void
     */
    fun updateFrameworkType(framework_type: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateFrameworkType(framework_type)
    }

    /**
     * Function to initialize a coroutine to update a the current_user_goal record with the
     * framework_type_id provided.
     *
     * @param framework_type_id, an Int
     * @return void
     */
    fun updateFrameworkTypeID(framework_type_id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateFrameworkTypeID(framework_type_id)
    }

    /**
     * Function to initialize a coroutine to update a the current_user_goal record with the
     * framework_type_id tied to the name of the framework in the framework_type table and the
     * nutrition_plan_type_id tied to the plan with the calorie, carbohydrates, protein, and fat
     * percentages provided.
     *
     * @param framework_type, a String
     * @param calorie, a double
     * @param protein, a double
     * @param fat, a double
     * @param carbohydrate, a double
     * @return void
     */
    fun updateNutritionPlanAndFramework(framework_type: String, calorie: Double, carbohydrate: Double, protein: Double, fat: Double) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNutritionPlanAndFramework(framework_type, calorie, carbohydrate, protein, fat)
    }

    /**
     * Function to initialize a coroutine to update a the current_user_goal record with the
     * framework_type_id and the nutrition_plan_type_id provided
     *
     * @param framework_type_id, a int
     * @param nutrition_plan_type_id, a int
     * @return void
     */
    fun updateNutritionPlanAndFrameworkID(framework_type_id: Int, nutrition_plan_type_id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNutritionPlanAndFrameworkID(framework_type_id, nutrition_plan_type_id)
    }
}

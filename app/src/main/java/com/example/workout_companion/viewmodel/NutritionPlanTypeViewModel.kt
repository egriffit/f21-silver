package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.GoalAndNutritionPlanTypeEntity
import com.example.workout_companion.entity.NutritionPlanTypeEntity
import com.example.workout_companion.repository.NutritionPlanTypeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/***
 * ViewModel class for the UserEntity object
 * @parm Context, application context
 * @return AndroidViewModel
 */
class NutritionPlanTypeViewModel(application: Application): AndroidViewModel(application) {
    /**
     * Retrieves a list of all NutritionPlanTypeEntities in the nutrition_plan_type table
     */
    private val readAll: LiveData<List<NutritionPlanTypeEntity>>

    /**
     * Found nutrition plan id
     */
    var id =  MutableLiveData<Int>()

    /**
     * NutritionPlanTypeRepository Object
     */
    private val repository: NutritionPlanTypeRepository

    /**
     * Function to initialize the view.
     * Initializes the WCDatabase, repository and the list of all NutritionPlanTypeEntities entities
     */
    init{
        val nutritionPlanTypeDao = WCDatabase.getInstance(application).nutritionPlanTypeDao()
        repository = NutritionPlanTypeRepository(nutritionPlanTypeDao = nutritionPlanTypeDao)
        readAll = repository.getAll
    }

    fun getPlanId(item: NutritionPlanTypeEntity) = viewModelScope.launch(Dispatchers.IO) {
        id.postValue(repository.findPlanId(item.calorie, item.carbohydrate, item.protein, item.fat))
    }
    /**
     * Function to initialize a coroutine to add a nutrition plan to the database
     * @param item, a NutritionPlanTypeEntity
     */
    fun addPlan(item: NutritionPlanTypeEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.addPlan(item = item)
    }

    /**
     * Function to initialize a coroutine to update a nutrition plan in the
     * nutrition_plan_type table with the values in the provided NutritionPlanTypeEntity
     * @param item, a UserEntity
     */
    fun updatePlan(item: NutritionPlanTypeEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.updatePlan(item = item)
    }

    /**
     * Function to initialize a coroutine to delete a nutrition plan in the
     * nutrition_plan_type table with the values in the provided NutritionPlanTypeEntity
     * @param item, a NutritionPlanTypeEntity
     */
    fun deletePlan(item: NutritionPlanTypeEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.deletePlan(item = item)
    }

    /**
     * Function to initialize a coroutine to delete a nutrition plan in the
     * nutrition_plan_type table with the values in the provided NutritionPlanTypeEntity
     * @param item, a NutritionPlanTypeEntity
     */
    fun getPlansByGoals(goal: String): List<GoalAndNutritionPlanTypeEntity>{
        var plansByGoal: List<GoalAndNutritionPlanTypeEntity> = listOf()
        viewModelScope.launch(Dispatchers.IO){
             plansByGoal = repository.getPlansByGoal(goal)
        }
        return plansByGoal
    }


}

/**
 * UserViewModel Factory class that is used to initialize the NutritionPlanView Model
 * @param Application context
 * @return ViewModelProvider.Factory
 */
class NutritionPlanTyperViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory{
    /**
     * Method to create an instance of the UserModelView
     */
    override fun <T: ViewModel?> create(modelClass: Class<T>): T{
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(NutritionPlanTypeViewModel::class.java)) {
            return NutritionPlanTypeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}
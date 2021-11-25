package com.example.workout_companion.viewmodel

import androidx.lifecycle.*
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.NutritionStatusEntity
import com.example.workout_companion.repository.NutritionStatusRepository

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

    private val repository: NutritionStatusRepository

    init {
        val nutritionStatusDao = WCDatabase.getInstance(application).goalTypeDao()
        repository = NutritionStatusRepository(nutritionStatusDao)
        allNutritionStatuses = repository.getAllStatuses
    }
}
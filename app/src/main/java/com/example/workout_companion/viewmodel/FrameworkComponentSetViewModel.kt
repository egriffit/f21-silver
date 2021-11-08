package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkComponentSetEntity
import com.example.workout_companion.repository.FrameworkComponentSetRepository
import java.time.LocalDate

/**
 * View model for the framework_component_set table in the database
 *
 * @param application The application.
 */
class FrameworkComponentSetViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * The repository for accessing the database
     */
    private val repository: FrameworkComponentSetRepository

    init {
        val dao = WCDatabase.getInstance(application.applicationContext).frameworkComponentSetDao()
        repository = FrameworkComponentSetRepository(dao)
    }

    /**
     * Gets all framework component sets for the given date
     *
     * @param date The date to search with.
     *
     * @return A list of all framework component sets.
     */
    suspend fun getFrameworkComponentSetsForDate(date: LocalDate): List<FrameworkComponentSetEntity> {
        return repository.getFrameworkComponentSetsForDate(date)
    }

    /**
     * Adds a framework component set to the database
     *
     * @param set The set to add to the database.
     */
    suspend fun addFrameworkComponentSet(set: FrameworkComponentSetEntity) {
        repository.addFrameworkComponentSet(set)
    }

    /**
     * Updates a framework component set in the database.
     *
     * @param set The set to update in the database.
     */
    suspend fun updateFrameworkComponentSet(set: FrameworkComponentSetEntity) {
        repository.updateFrameworkComponentSet(set)
    }

    /**
     * Delete a framework component set from the database.
     *
     * @param set The set to remove from the database.
     */
    suspend fun deleteFrameworkComponentSet(set: FrameworkComponentSetEntity) {
        repository.deleteFrameworkComponentSet(set)
    }
}
package com.example.workout_companion.repository

import com.example.workout_companion.dao.FrameworkComponentSetDao
import com.example.workout_companion.entity.FrameworkComponentSetEntity
import java.time.LocalDate

/**
 * Repository for accessing the framework_component_set in the database
 *
 * @property dao The database access object.
 */
class FrameworkComponentSetRepository(private val dao: FrameworkComponentSetDao) {

    /**
     * Gets all framework component sets for the given date in the database
     *
     * @param date The date to search with.
     *
     * @return A list of the framework component sets.
     */
    suspend fun getFrameworkComponentSetsForDate(date: LocalDate): List<FrameworkComponentSetEntity> {
        return dao.getFrameworkComponentSetsForDate(date)
    }

    /**
     * Adds a framework component set to the database
     *
     * @param set The set to add to the database.
     */
    suspend fun addFrameworkComponentSet(set: FrameworkComponentSetEntity) {
        dao.addFrameworkComponentSet(set)
    }

    /**
     * Updates a framework component set in the database.
     *
     * @param set The set to update in the database.
     */
    suspend fun updateFrameworkComponentSet(set: FrameworkComponentSetEntity) {
        dao.updateFrameworkComponentSet(set)
    }

    /**
     * Deletes a framework component set from the database.
     *
     * @param set The set to remove from the database.
     */
    suspend fun deleteFrameworkComponentSet(set: FrameworkComponentSetEntity) {
        dao.deleteFrameworkComponentSet(set)
    }
}
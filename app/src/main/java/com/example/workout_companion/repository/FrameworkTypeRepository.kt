package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.FrameworkTypeDao
import com.example.workout_companion.entity.FrameworkTypeEntity

class FrameworkTypeRepository(private val frameworkTypeDao: FrameworkTypeDao) {

    /**
     * A LiveData List of all frameworks within the database
     */
    val allFrameworks: LiveData<List<FrameworkTypeEntity>> = frameworkTypeDao.getAllFrameworks()

    /**
     * Get a framework by its primary key, [id]
     *
     * @property id the primary key of the framework
     *
     * @return the framework if found, otherwise null
     */
    fun getFrameworkById(id: Int) : FrameworkTypeEntity? {
        return frameworkTypeDao.getFrameworkById(id)
    }

    /**
     * Get all frameworks whose number of workouts per week is less than or equal to [maxWorkouts]
     *
     * @property maxWorkouts    the maximum number of workouts per week.
     *
     * @return the frameworks that have a number of workouts per week less than or equal to [maxWorkouts]
     */
    fun getFrameworksWithinMaxWorkouts(maxWorkouts: Int) : LiveData<List<FrameworkTypeEntity>> {
        return frameworkTypeDao.getFrameworksWithinMaxWorkouts(maxWorkouts)
    }

    /**
     * Add a single framework to the database
     *
     * @property framework  the framework to add to the database.
     */
    suspend fun addFramework(framework: FrameworkTypeEntity) {
        frameworkTypeDao.addFramework(framework)
    }

    /**
     * Add all frameworks to the database
     *
     * @property frameworks the frameworks to add to the database.
     */
    suspend fun addFrameworks(frameworks: Collection<FrameworkTypeEntity>) {
        frameworkTypeDao.addFrameworks(frameworks)
    }

    /**
     * Add all frameworks to the database
     *
     * @property frameworks the frameworks to add to the database.
     */
    suspend fun addFrameworks(vararg frameworks: FrameworkTypeEntity) {
        for (framework in frameworks) {
            frameworkTypeDao.addFramework(framework)
        }
    }

    /**
     * Delete the framework from the database
     *
     * @property framework  the framework to remove from the database.
     */
    suspend fun deleteFramework(framework: FrameworkTypeEntity) {
        frameworkTypeDao.deleteFramework(framework)
    }

    /**
     * Delete the frameworks from the database
     *
     * @property frameworks the frameworks to remove from the database.
     */
    suspend fun deleteFrameworks(frameworks: Collection<FrameworkTypeEntity>) {
        frameworkTypeDao.deleteFrameworks(frameworks)
    }

    /**
     * Delete the frameworks from the database
     *
     * @property frameworks the frameworks to remove from the database.
     */
    suspend fun deleteFrameworks(vararg frameworks: FrameworkTypeEntity) {
        for (framework in frameworks) {
            frameworkTypeDao.deleteFramework(framework)
        }
    }
}
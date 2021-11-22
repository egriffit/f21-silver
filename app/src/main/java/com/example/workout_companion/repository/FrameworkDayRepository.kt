package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import androidx.room.Transaction
import com.example.workout_companion.dao.FrameworkDayDao
import com.example.workout_companion.entity.FrameworkDayEntity
import com.example.workout_companion.entity.FrameworkTypeEntity

class FrameworkDayRepository(private val frameworkDayDao: FrameworkDayDao) {

    /**
     * Get all days within a workout framework
     *
     * @property framework_type_id  the primary key of the framework.
     *
     * @return a LiveData List of all days within the framework.
     */
    fun getAllFrameworkDays(framework_type_id: Int) : LiveData<List<FrameworkDayEntity>> {
        return frameworkDayDao.getAllFrameworkDays(framework_type_id)
    }

    /**
     * Add a day to a framework
     *
     * @property day    the day to add.
     */
    @Transaction
    suspend fun addFrameworkDay(day: FrameworkDayEntity) {
        if(frameworkDayDao.count(day.name, day.framework_type_id) > 0) {
            frameworkDayDao.updateFrameworkDay(day)
        }else{
            frameworkDayDao.addFrameworkDay(day)
        }
    }

    /**
     * Add a day to a framework
     *
     * @property days, list of days to add
     */
    @Transaction
    suspend fun addFrameworkDay(days: List<FrameworkDayEntity>) {
        days.forEach{ day ->
            if(frameworkDayDao.count(day.name, day.framework_type_id) > 0) {
                frameworkDayDao.updateFrameworkDay(day)
            }else{
                frameworkDayDao.addFrameworkDay(day)
            }
        }
    }

    /**
     * Add a collection of days to frameworks
     *
     * @property days   a [Collection] of days to add.
     */
    suspend fun addFrameworkDays(days: Collection<FrameworkDayEntity>) {
        frameworkDayDao.addFrameworkDays(days)
    }

    /**
     * Add multiple days to frameworks
     *
     * @property days   a comma-separated list of days to add
     */
    suspend fun addFrameworkDays(vararg days: FrameworkDayEntity) {
        for (day in days) {
            frameworkDayDao.addFrameworkDay(day)
        }
    }

    /**
     * Update a day in a framework
     *
     * @property day    the updated day to save
     */
    suspend fun updateFrameworkDay(day: FrameworkDayEntity) {
        frameworkDayDao.updateFrameworkDay(day)
    }

    /**
     * Delete a day from a framework
     *
     * @property day    the day to remove.
     */
    suspend fun deleteFrameworkDay(day: FrameworkDayEntity) {
        frameworkDayDao.deleteFrameworkDay(day)
    }

    /**
     * Delete a collection of days from frameworks
     *
     * @property days   a [Collection] of days to remove.
     */
    suspend fun deleteFrameworkDays(days: Collection<FrameworkDayEntity>) {
        frameworkDayDao.deleteFrameworkDays(days)
    }

    /**
     * Delete multiple days from frameworks
     *
     * @property days   a comma-separated list of days to remove.
     */
    suspend fun deleteFrameworkDays(vararg days: FrameworkDayEntity) {
        for(day in days) {
            frameworkDayDao.deleteFrameworkDay(day)
        }
    }
}
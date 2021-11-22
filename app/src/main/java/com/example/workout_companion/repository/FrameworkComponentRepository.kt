package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import androidx.room.Transaction
import com.example.workout_companion.dao.FrameworkComponentDao
import com.example.workout_companion.entity.FrameworkComponentEntity

class FrameworkComponentRepository(private val dao: FrameworkComponentDao) {

    /**
     * Get all components of a day of a framework
     *
     * @property day_id primary key of the day.
     *
     * @return a LiveData List of all components.
     */
    fun getAllComponentsOfDay(day_id: Int) : LiveData<List<FrameworkComponentEntity>> {
        return dao.getAllComponentsOfDay(day_id)
    }

    /**
     * Add a component to a day
     *
     * @property component  the component to add.
     */
    @Transaction
    suspend fun addFrameworkComponent(component: FrameworkComponentEntity) {
        if(dao.count(component.framework_day_id, component.muscle_group, component.target_reps,
                component.target_sets) > 0){
            dao.updateFrameworkComponent(component)
        }else{
            dao.addFrameworkComponent(component)
        }
    }

    /**
     * Add a component to a day
     *
     * @property components  list of components to add.
     */
    @Transaction
    suspend fun addFrameworkComponent(components: List<FrameworkComponentEntity>) {
        components.forEach{ component ->
            if(dao.count(component.framework_day_id, component.muscle_group, component.target_reps,
                    component.target_sets) > 0){
                dao.updateFrameworkComponent(component)
            }else{
                dao.addFrameworkComponent(component)
            }
        }
    }



    /**
     * Add multiple components to days
     *
     * @property components a [Collection] of components to add.
     */
    suspend fun addFrameworkComponents(components: Collection<FrameworkComponentEntity>) {
        dao.addFrameworkComponents(components)
    }

    /**
     * Add multiple components to days
     *
     * @property components a comma-separated list of components to add.
     */
    suspend fun addFrameworkComponents(vararg components: FrameworkComponentEntity) {
        for (component in components) {
            dao.addFrameworkComponent(component)
        }
    }

    /**
     * Updates a component from a day
     *
     * @property component  the component to update.
     */
    suspend fun updateFrameworkComponent(component: FrameworkComponentEntity) {
        dao.updateFrameworkComponent(component)
    }

    /**
     * Deletes a component from a day
     *
     * @property component  the component to remove.
     */
    suspend fun deleteFrameworkComponent(component: FrameworkComponentEntity) {
        dao.deleteFrameworkComponent(component)
    }

    /**
     * Deletes multiple components from days
     *
     * @property components a [Collection] of components to remove.
     */
    suspend fun deleteFrameworkComponents(components: Collection<FrameworkComponentEntity>) {
        dao.deleteFrameworkComponents(components)
    }

    /**
     * Deletes multiple components from days
     *
     * @property components a comma-separated list of components to remove.
     */
    suspend fun deleteFrameworkComponents(vararg components: FrameworkComponentEntity) {
        for (component in components) {
            dao.deleteFrameworkComponent(component)
        }
    }
}
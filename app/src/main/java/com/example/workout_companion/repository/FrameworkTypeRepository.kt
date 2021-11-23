package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import androidx.room.Transaction
import com.example.workout_companion.dao.FrameworkComponentDao
import com.example.workout_companion.dao.FrameworkTypeDao
import com.example.workout_companion.dao.FrameworkWithDays
import com.example.workout_companion.entity.FrameworkTypeEntity
import com.example.workout_companion.entity.FrameworkWithGoalEntity

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
     * Get a framework by goal name
     *
     * @property goal the name of the framework goal
     *
     * @return the FrameworkWithGoalEntity if found, otherwise null
     */
    fun getFrameworkByGoalName(goal: String) : LiveData<List<FrameworkWithGoalEntity>>  {
        return frameworkTypeDao.getFrameworksWithGoalName(goal)
    }

    /**
     * Get a framework by goal name
     *
     * @param name the name of the framework
     * @param goal the name of the framework goal
     *
     * @return the FrameworkWithGoalEntity if found, otherwise null
     */
    fun getFrameworkIdByName(name: String, goal: String) : Int  {
        return frameworkTypeDao.getFrameworkIdByName(name, goal)
    }

    /**
     * Get a framework by goal name and with the number of workouts
     * equal to or fewer than the number of workouts provided
     *
     * @property goal the name of the framework goal
     * @property goal the name of the framework goal
     *
     * @return the FrameworkWithGoalEntity if found, otherwise null
     */
    fun getFrameworksWithGoalNameWithinMaxWorkouts(goal: String, workoutFrequency: Int) : LiveData<List<FrameworkWithGoalEntity>>  {
        return frameworkTypeDao.getFrameworksWithGoalNameWithinMaxWorkouts(goal, workoutFrequency)
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
     * Get all frameworks that have the specified goal link
     *
     * @property goal_id    the primary key of the goal to search.
     *
     * @return the frameworks with the given goal
     */
    fun getFrameworksWithGoal(goal_id: Int) : LiveData<List<FrameworkTypeEntity>> {
        return frameworkTypeDao.getFrameworksWithGoal(goal_id)
    }

    /**
     * Add a single framework to the database
     *
     * @property framework  the framework to add to the database.
     */
    @Transaction
    suspend fun addFramework(framework: FrameworkTypeEntity) {
        if(frameworkTypeDao.getCount(framework.name, framework.goal_id, framework.workouts_per_week) > 0){
            frameworkTypeDao.updateFramework(framework)
        }else{
            frameworkTypeDao.addFramework(framework)
        }
    }

    /**
     * Add a single framework to the database
     *
     * @property framework  the framework to add to the database.
     */
    suspend fun addFramework(frameworks: List<FrameworkTypeEntity>) {
        frameworks.forEach{framework->
            if(frameworkTypeDao.getCount(framework.name, framework.goal_id, framework.workouts_per_week) > 0){
                frameworkTypeDao.updateFramework(framework)
            }else{
                frameworkTypeDao.addFramework(framework)
            }
            frameworkTypeDao.addFramework(framework)
        }
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
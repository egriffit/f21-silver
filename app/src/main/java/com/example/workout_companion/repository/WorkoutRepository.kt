package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.*
import com.example.workout_companion.entity.WorkoutComponentEntity
import com.example.workout_companion.entity.WorkoutComponentSetEntity
import com.example.workout_companion.entity.WorkoutEntity
import com.example.workout_companion.enumeration.Progress
import java.time.LocalDate
import kotlin.math.floor

/**
 * Repository for accessing, creating, and removing workouts
 *
 * @property workoutDao Dao for accessing/modifying the workout tables in the database
 * @property componentDao Dao for accessing/modifying the workout component tables in the database
 * @property setDao Dao for accessing/modifying the workout component set tables in the database
 */
class WorkoutRepository(private val workoutDao: WorkoutDao, private val componentDao: WorkoutComponentDao,
    private val setDao: WorkoutComponentSetDao) {

    /**
     * All workouts in the database
     */
    val workouts = workoutDao.getAllWorkouts()

    /**
     * Gets the workout on a specific date
     *
     * @param date The date for the workout
     *
     * @return The workout
     */
    fun getWorkoutOnDate(date: LocalDate): LiveData<WorkoutEntity> {
        return workoutDao.getWorkoutOnDate(date)
    }

    /**
     * Gets the workout on the specific date with all of its workout components
     *
     * @param date The date of the workout
     *
     * @return The workout along with its components
     */
    fun getWorkoutWithComponents(date: LocalDate): LiveData<WorkoutWithComponents> {
        return workoutDao.getWorkoutWithComponents(date)
    }

    /**
     * Create a workout, its components, and its sets in the database based off of the given
     * framework day.
     *
     * @param frameworkDayWithComponents The framework day that serves as a template for the workout
     */
    suspend fun createWorkout(frameworkDayWithComponents: FrameworkDayWithComponents) {
        if (!doesWorkoutExist(LocalDate.now()))
        {
            val newWorkout = WorkoutEntity(LocalDate.now(), Progress.IN_PROGRESS, frameworkDayWithComponents.day.id)
            workoutDao.addWorkout(newWorkout)


            for (frameworkComponent in frameworkDayWithComponents.components) {
                val newWorkoutComponent = WorkoutComponentEntity(newWorkout.date, frameworkComponent.id)
                val newWorkoutComponentId = componentDao.addWorkoutComponent(newWorkoutComponent)

                var leftoverReps: Int = frameworkComponent.target_reps % frameworkComponent.target_sets
                val repsPerSet = floor((frameworkComponent.target_reps / frameworkComponent.target_sets).toDouble()).toInt()

                for (i in 1..frameworkComponent.target_sets) {
                    val reps = repsPerSet + if (leftoverReps > 0) leftoverReps-- else 0

                    // We are leaving all other inputs as defaults
                    val newSet = WorkoutComponentSetEntity(workout_component_id = newWorkoutComponentId.toInt(), reps = reps)
                    setDao.addSet(newSet)
                }
            }
        }
    }

    /**
     * Updates a workout in the database
     *
     * @param workout The workout to update
     */
    suspend fun updateWorkout(workout: WorkoutEntity) {
        workoutDao.updateWorkout(workout)
    }

    /**
     * Updates a workout component in the database
     *
     * @param component The workout component to update
     */
    suspend fun updateWorkoutComponent(component: WorkoutComponentEntity) {
        componentDao.updateWorkoutComponent(component)
    }

    /**
     * Sets the exercise of the given workout component
     *
     * @param id The id of the workout component
     * @param exerciseId The id of the exercise
     */
    suspend fun setWorkoutComponentExercise(id: Int, exerciseId: Int, exerciseName: String) {
        val component = componentDao.getWorkoutComponentById(id)
        if (component != null) {
            component.wger_id = exerciseId
            component.wger_name = exerciseName
            componentDao.updateWorkoutComponent(component)
        }
    }

    /**
     * Update a set's status to complete
     *
     * @param set The set to update.
     */
    suspend fun completeWorkoutSet(set: WorkoutComponentSetEntity) {
        set.status = Progress.COMPLETE
        setDao.updateSet(set)
    }

    /**
     * Update a set's status back to in progress
     *
     * @param set The set to update.
     */
    suspend fun unlockWorkoutSet(set: WorkoutComponentSetEntity) {
        set.status = Progress.IN_PROGRESS
        setDao.updateSet(set)
    }

    /**
     * Update a set's status back to in progress
     *
     * @param set The set to update.
     */
    suspend fun updateWorkoutSet(set: WorkoutComponentSetEntity) {
        setDao.updateSet(set)
    }

    /**
     * Deletes a workout from the database
     *
     * @param workout The workout to delete
     */
    suspend fun deleteWorkout(workout: WorkoutEntity) {
        workoutDao.deleteWorkout(workout)
    }

    fun doesWorkoutExist(date: LocalDate) : Boolean {
        return workoutDao.getNumberOfWorkouts(date) != 0
    }
}
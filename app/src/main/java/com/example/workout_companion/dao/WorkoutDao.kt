package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.WorkoutComponentEntity
import com.example.workout_companion.entity.WorkoutComponentSetEntity
import com.example.workout_companion.entity.WorkoutEntity
import java.time.LocalDate

data class ComponentWithSets(
    @Embedded
    val component: WorkoutComponentEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "workout_component_id",
        entity = WorkoutComponentSetEntity::class,
    )
    val sets: List<WorkoutComponentSetEntity>
)
data class WorkoutWithComponents(
    @Embedded
    val workout: WorkoutEntity,

    @Relation(
        parentColumn = "date",
        entityColumn = "workout_date",
        entity = WorkoutComponentEntity::class
    )
    val components: List<ComponentWithSets>,
)
/**
 * The Database Access Object of the workout table.
 */
@Dao
interface WorkoutDao {

    /**
     * Returns a workout with its components
     *
     * @param date The date of the workout
     *
     * @return The workout and its components
     */
    @Query("SELECT * FROM workout WHERE workout.date = :date")
    fun getWorkoutWithComponents(date: LocalDate): LiveData<WorkoutWithComponents>

    /**
     * Gets all workouts in the database
     *
     * @return A LiveData list of the workouts
     */
    @Query("SELECT * from workout")
    fun getAllWorkouts(): LiveData<List<WorkoutEntity>>

    /**
     * Gets the workout on a specific date
     *
     * @property date The date for the workout
     *
     * @return A LiveData container of the workout
     */
    @Query("SELECT * FROM workout WHERE date=:date")
    fun getWorkoutOnDate(date: LocalDate): LiveData<WorkoutEntity>

    /**
     * Adds a workout to the database
     *
     * @property workout The workout to add
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorkout(workout: WorkoutEntity)

    /**
     * Updates a workout in the database
     *
     * @property workout The workout to update
     */
    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)

    /**
     * Delete a workout from the database
     *
     * @property workout The workout to delete
     */
    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity)
}
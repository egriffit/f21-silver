package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.WorkoutEntity
import java.time.LocalDate

@Dao
interface WorkoutDao {

    @Query("SELECT * from workout")
    fun getAllWorkouts(): LiveData<List<WorkoutEntity>>

    @Query("SELECT * FROM workout WHERE date=:date")
    fun getWorkoutOnDate(date: LocalDate): LiveData<WorkoutEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorkout(workout: WorkoutEntity)

    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)

    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity)
}
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
    fun addWorkout(workout: WorkoutEntity)

    @Update
    fun updateWorkout(workout: WorkoutEntity)

    @Delete
    fun deleteWorkout(workout: WorkoutEntity)
}
package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.WorkoutDao
import com.example.workout_companion.entity.WorkoutEntity
import java.time.LocalDate

class WorkoutRepository(private val workoutDao: WorkoutDao) {

    val workouts = workoutDao.getAllWorkouts()

    fun getWorkoutOnDate(date: LocalDate): LiveData<WorkoutEntity> {
        return workoutDao.getWorkoutOnDate(date)
    }

    suspend fun addWorkout(workout: WorkoutEntity) {
        workoutDao.addWorkout(workout)
    }

    suspend fun updateWorkout(workout: WorkoutEntity) {
        workoutDao.updateWorkout(workout)
    }

    suspend fun deleteWorkout(workout: WorkoutEntity) {
        workoutDao.deleteWorkout(workout)
    }
}
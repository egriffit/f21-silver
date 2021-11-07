package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.WorkoutEntity
import com.example.workout_companion.repository.WorkoutRepository
import java.time.LocalDate

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    val workouts: LiveData<List<WorkoutEntity>>

    private val repository: WorkoutRepository

    init {
        val dao = WCDatabase.getInstance(application).workoutDao()
        repository = WorkoutRepository(dao)
        workouts = repository.workouts
    }

    fun getWorkoutOnDate(date: LocalDate): LiveData<WorkoutEntity> {
        return repository.getWorkoutOnDate(date)
    }

    suspend fun addWorkout(workout: WorkoutEntity) {
        repository.addWorkout(workout)
    }

    suspend fun updateWorkout(workout: WorkoutEntity) {
        repository.updateWorkout(workout)
    }

    suspend fun deleteWorkout(workout: WorkoutEntity) {
        repository.deleteWorkout(workout)
    }
}
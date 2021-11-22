package com.example.workout_companion.repository

import com.example.workout_companion.dao.WorkoutComponentSetDao
import com.example.workout_companion.entity.WorkoutComponentSetEntity

class WorkoutComponentSetRepository(private val dao: WorkoutComponentSetDao) {

    suspend fun addSet(set: WorkoutComponentSetEntity) {
        dao.addSet(set)
    }

    suspend fun updateSet(set: WorkoutComponentSetEntity) {
        dao.updateSet(set)
    }

    suspend fun removeSet(set: WorkoutComponentSetEntity) {
        dao.removeSet(set)
    }
}
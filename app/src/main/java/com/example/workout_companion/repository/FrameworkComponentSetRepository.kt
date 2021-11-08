package com.example.workout_companion.repository

import com.example.workout_companion.dao.FrameworkComponentSetDao
import com.example.workout_companion.entity.FrameworkComponentSetEntity
import java.time.LocalDate

class FrameworkComponentSetRepository(private val dao: FrameworkComponentSetDao) {

    suspend fun getFrameworkComponentSetsForDate(date: LocalDate): List<FrameworkComponentSetEntity> {
        return dao.getFrameworkComponentSetsForDate(date)
    }

    suspend fun addFrameworkComponentSet(set: FrameworkComponentSetEntity) {
        dao.addFrameworkComponentSet(set)
    }

    suspend fun updateFrameworkComponentSet(set: FrameworkComponentSetEntity) {
        dao.updateFrameworkComponentSet(set)
    }

    suspend fun deleteFrameworkComponentSet(set: FrameworkComponentSetEntity) {
        dao.deleteFrameworkComponentSet(set)
    }
}
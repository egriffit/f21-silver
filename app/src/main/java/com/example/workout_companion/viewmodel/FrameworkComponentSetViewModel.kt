package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkComponentSetEntity
import com.example.workout_companion.repository.FrameworkComponentSetRepository
import java.time.LocalDate

class FrameworkComponentSetViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FrameworkComponentSetRepository

    init {
        val dao = WCDatabase.getInstance(application.applicationContext).frameworkComponentSetDao()
        repository = FrameworkComponentSetRepository(dao)
    }

    suspend fun getFrameworkComponentSetsForDate(date: LocalDate): List<FrameworkComponentSetEntity> {
        return repository.getFrameworkComponentSetsForDate(date)
    }

    suspend fun addFrameworkComponentSet(set: FrameworkComponentSetEntity) {
        repository.addFrameworkComponentSet(set)
    }

    suspend fun updateFrameworkComponentSet(set: FrameworkComponentSetEntity) {
        repository.updateFrameworkComponentSet(set)
    }

    suspend fun deleteFrameworkComponentSet(set: FrameworkComponentSetEntity) {
        repository.deleteFrameworkComponentSet(set)
    }
}
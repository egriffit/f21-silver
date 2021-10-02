package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.FrameworkTypeDao
import com.example.workout_companion.entity.FrameworkTypeEntity

class FrameworkTypeRepository(private val frameworkTypeDao: FrameworkTypeDao) {
    val allFrameworks: LiveData<List<FrameworkTypeEntity>> = frameworkTypeDao.getAllFrameworks()
}
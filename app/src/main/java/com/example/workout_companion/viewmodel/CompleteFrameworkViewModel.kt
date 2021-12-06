package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.workout_companion.dao.FrameworkWithDays
import com.example.workout_companion.database.WCDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompleteFrameworkViewModel(app: Application) : AndroidViewModel(app) {

    private val db = WCDatabase.getInstance(app)
    private val dao = db.completeFrameworkDao()

    fun getAllFrameworksWithDays(): LiveData<List<FrameworkWithDays>> {
        return dao.getAllFrameworksWithDays()
    }

    fun getFrameworkWithDaysById(id: Int): LiveData<FrameworkWithDays> {
        return dao.getFrameworkWithDaysById(id)
    }
}
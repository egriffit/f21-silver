package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.workout_companion.dao.FrameworkWithDays
import com.example.workout_companion.database.WCDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A view model for collecting a framework and all of its components from the database.
 *
 * @param app The application
 */
class CompleteFrameworkViewModel(app: Application) : AndroidViewModel(app) {

    /** The database */
    private val db = WCDatabase.getInstance(app)

    /** The DAO for frameworks */
    private val dao = db.completeFrameworkDao()

    /**
     * Get all frameworks in the database
     *
     * @return A LiveData object containing all frameworks
     */
    fun getAllFrameworksWithDays(): LiveData<List<FrameworkWithDays>> {
        return dao.getAllFrameworksWithDays()
    }

    /**
     * Get a specific framework from the database
     *
     * @param id The primary key, id, of the framework
     *
     * @return A LiveData object containing the framework
     */
    fun getFrameworkWithDaysById(id: Int): LiveData<FrameworkWithDays> {
        return dao.getFrameworkWithDaysById(id)
    }
}
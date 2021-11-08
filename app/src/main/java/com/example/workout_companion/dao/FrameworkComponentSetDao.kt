package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.FrameworkComponentSetEntity
import java.time.LocalDate

@Dao
interface FrameworkComponentSetDao {

    @Query("SELECT * FROM framework_component_set WHERE workout_date=:date")
    fun getFrameworkComponentSetsForDate(date: LocalDate): LiveData<List<FrameworkComponentSetEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFrameworkComponentSet(frameworkComponentSet: FrameworkComponentSetEntity)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateFrameworkComponentSet(frameworkComponentSet: FrameworkComponentSetEntity)
}
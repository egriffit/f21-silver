package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.FrameworkComponentSetEntity
import java.time.LocalDate

@Dao
interface FrameworkComponentSetDao {

    @Query("SELECT * FROM framework_component_set WHERE workout_date=:date")
    suspend fun getFrameworkComponentSetsForDate(date: LocalDate): List<FrameworkComponentSetEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFrameworkComponentSet(frameworkComponentSet: FrameworkComponentSetEntity)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateFrameworkComponentSet(frameworkComponentSet: FrameworkComponentSetEntity)

    @Delete
    suspend fun deleteFrameworkComponentSet(frameworkComponentSet: FrameworkComponentSetEntity)
}
package com.example.workout_companion.dao

import androidx.room.*
import com.example.workout_companion.entity.WorkoutComponentSetEntity

@Dao
interface WorkoutComponentSetDao {

    @Query("SELECT * FROM workout_component_set")
    fun getAllSets(): List<WorkoutComponentSetEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSet(set: WorkoutComponentSetEntity)

    @Update
    suspend fun updateSet(set: WorkoutComponentSetEntity)

    @Delete
    suspend fun removeSet(set: WorkoutComponentSetEntity)
}
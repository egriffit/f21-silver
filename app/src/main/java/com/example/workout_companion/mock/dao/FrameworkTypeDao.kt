package com.example.workout_companion.mock.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.workout_companion.mock.entity.FrameworkTypeEntity
import com.example.workout_companion.mock.entity.GoalTypeEntity

@Dao
interface FrameworkTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFramework(framework: FrameworkTypeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFramework(framework: List<FrameworkTypeEntity>)
}
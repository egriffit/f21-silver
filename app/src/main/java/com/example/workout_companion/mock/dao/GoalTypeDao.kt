package com.example.workout_companion.mock.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workout_companion.mock.entity.GoalTypeEntity

@Dao
interface GoalTypeDao {

    @Query("SELECT * FROM goal_type")
    fun getAllGoals(): LiveData<List<GoalTypeEntity>>

    @Query("SELECT * FROM goal_type WHERE id=:goalId")
    fun getGoalById(goalId: Int): GoalTypeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGoal(goal: GoalTypeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGoal(goal: List<GoalTypeEntity>)
}
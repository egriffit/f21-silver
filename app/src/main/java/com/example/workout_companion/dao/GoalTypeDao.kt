package com.example.workout_companion.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.workout_companion.entity.GoalTypeEntity

@Dao
interface GoalTypeDao {
    @Query("SELECT * FROM goal_type")
    fun getAllGoals(): List<GoalTypeEntity>

    @Query("SELECT * FROM goal_type WHERE goal=:name")
    fun getGoalByName(name: String): List<GoalTypeEntity>
}
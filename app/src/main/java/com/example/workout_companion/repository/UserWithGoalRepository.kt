package com.example.workout_companion.repository

import com.example.workout_companion.dao.UserWithGoalDao

class UserWithGoalRepository(private val dao: UserWithGoalDao) {
    val userWithGoal = dao.getUserWithGoal()
}
package com.example.workout_companion.repository

import com.example.workout_companion.dao.UserWithGoalDao

/**
 * Repository for accessing a user and their attached goal
 *
 * @property dao The Data Access Object for the underlying data
 */
class UserWithGoalRepository(private val dao: UserWithGoalDao) {
    /**
     * The user with goal value
     */
    val userWithGoal = dao.getUserWithGoal()
}
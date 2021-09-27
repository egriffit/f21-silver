package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.UserDao
import com.example.workout_companion.entity.UserEntity

/**
 * User Repository class that abstracts the methods in the User DAO
 *
 */
class UserRepository (private val userDao: UserDao) {
    /**
     * Retrieves a list of all users in the user table
     * @return LiveData<List<UserEntity>> List of Users
     */
    val readAll: LiveData<List<UserEntity>> = userDao.getAll()

    /**
     * Adds a user to the user table
     * @param item, a UserEntity
     * @return void
     */
    suspend fun addUser(item: UserEntity){
        userDao.insert(item)
    }

    /**
     * Update a user in the user table with the values in the provided UserEntity object
     * @param item, a UserEntity
     * @return void
     */
    suspend fun updateUser(item: UserEntity){
        userDao.update(item)
    }

    /**
     * Delete a user in the user table with the values in the provided UserEntity object
     * @param item, a UserEntity
     * @return void
     */
    suspend fun deleteUser(item: UserEntity){
        userDao.delete(item)
    }

    /**
     * Retrieves the age of the user with the provided name
     * @param name, String
     * @return Int, age
     */
    suspend fun getAge(name: String): Int{
       return userDao.getAge(name)
    }
}
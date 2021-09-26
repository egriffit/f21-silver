package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import com.example.workout_companion.dao.UserDao
import com.example.workout_companion.entity.UserEntity

class UserRepository (private val userDao: UserDao) {
    val readAll: LiveData<List<UserEntity>> = userDao.getAll()

    suspend fun addUser(item: UserEntity){
        userDao.insert(item)
    }

    suspend fun updateUser(item: UserEntity){
        userDao.update(item)
    }

    suspend fun deleteUser(item: UserEntity){
        userDao.delete(item)
    }
}
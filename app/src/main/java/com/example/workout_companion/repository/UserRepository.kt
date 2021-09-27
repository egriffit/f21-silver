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
     *
     * @return LiveData<List<UserEntity>> List of Users
     */
    val getAll: LiveData<List<UserEntity>> = userDao.getAll()

    /**
     * Retrieves a UserEntity from the users table where the name is equal to the string provided
     *
     * @param String name
     * @return UserEntity
     */
    suspend fun getByName(name: String): UserEntity{
        return userDao.getByName(name)
    }

    /**
     * Retrieves the total number of rows in the user table
     *
     * @param String nam
     * @return UserEntity
     */
    suspend fun getCount(): Int{
        return userDao.getCount()
    }

    /**
     * Retrieves the total number of rows in the user table
     * where the name is equal to the string provided
     *
     * @param String nam
     * @return UserEntity
     */
    suspend fun getCountWithName(name: String): Int{
        return userDao.getCountWithName(name)
    }

    /**
     * Checks if a record with the name equal to the string provided
     * exists in the table
     *
     * @param String name
     * @return Boolean
     */
    suspend fun checkIfUserExists(name: String): Boolean{
        val count: Int = userDao.getCountWithName(name)
        if(count > 0 ) {
            return true
        }
        return false
    }

    /**
     * Adds a user to the user table
     *
     * @param item UserEntity
     * @return void
     */
    suspend fun addUser(item: UserEntity){
        userDao.insert(item)
    }

    /**
     * Update a user in the user table with the values in the provided UserEntity object
     *
     * @param item a UserEntity
     * @return void
     */
    suspend fun updateUser(item: UserEntity){
        userDao.update(item)
    }

    /**
     * Delete a user in the user table with the values in the provided UserEntity object
     *
     * @param item a UserEntity
     * @return void
     */
    suspend fun deleteUser(item: UserEntity){
        userDao.delete(item)
    }

    /**
     * Deletes all users in the user table
     *
     * @return void
     */
    suspend fun deletaAll(){
        return userDao.deleteAll()
    }

    /**
     * Retrieves the age of the user with the provided name
     *
     * @param name String
     * @return Int, age
     */
    suspend fun getAge(name: String): Int{
       return userDao.getAge(name)
    }
}
package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.UserEntity
import java.time.LocalDate
import java.util.*

/**
 * The Data Abstraction Interface for the UserEntity
 *
 * Provides methods for SQL queries using the UserEntity
 */
@Dao
interface UserDao {

    /**
     * Retrieves a List of UserEntity objects from User's table
     *
     * @return LiveData<List<UserEntity> a list of UserEntity objects
     */
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<UserEntity>>

    /**
     * Retrieves a UserEntity object from user Table where
     * the name column matches the provided string.
     *
     * @param name, a string equal to the name of the user
     * @return  UserEntity object
     */
    @Query("SELECT * FROM user WHERE name = :name")
    suspend fun getByName(name: String): UserEntity

    /**
     * Insert a UserEntity object into the user table
     *
     * @param item, a userEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: UserEntity)

    /**
     * Update a the user record with the values in the provided UserEntity object
     *
     * @param item, a userEntity
     * @return void
     */
    @Update
    suspend fun update(item: UserEntity)

    /**
     * Delete the user record with that matches the values in the provided UserEntity object
     *
     * @param item, a userEntity
     * @return void
     */
    @Delete
    suspend fun delete(item: UserEntity)

    /**
     * Retrieve the birthdate for the record in the user table with the name equal to the string provided
     *
     * @param name, a string
     * @return LocalDate, birth_date
     */
    @Query("SELECT birth_date FROM user WHERE name = :name")
    suspend fun getBirthDate(name: String): LocalDate

    /**
     * Retrieves the user's age by calculating the difference between the user's birthday and the current date
     *
     * @param name, a string
     * @return Int, age of the user
     */
    @Query("SELECT CAST(round((julianday(date('now')) -  julianday(birth_date))/365 - 0.5)as INTEGER) AS age FROM user WHERE name = :name")
    suspend fun getAge(name: String): Int
}
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
     * Retrieves a List of UserEntity objects from the user table
     *
     * @return LiveData<List<UserEntity> a list of UserEntity objects
     */
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<UserEntity>>

    /**
     * Retrieves a UserEntity object from user table where
     * the name column matches the provided string.
     *
     * @param name, a string equal to the name of the user
     * @return  UserEntity object
     */
    @Query("SELECT * FROM user WHERE name = :name")
    suspend fun getByName(name: String): UserEntity

    /**
     * Retrieves the row count for the total of records in the users table
     *
     * @return  Int total number of rows found
     */
    @Query("SELECT COUNT(*) FROM user")
    fun getCount(): Int

    /**
     * Retrieves the row count for the number of records with
     * the name equal to the string provided in the user table
     *
     * @param name, a string equal to the name of the user
     * @return  Int total number of rows found
     */
    @Query("SELECT COUNT(*) FROM user WHERE name = :name")
    suspend fun getCountWithName(name: String): Int

    /**
     * Insert a UserEntity object into the user table
     *
     * @param item, a userEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: UserEntity)

    /**
     * Insert a list of UserEntity objects into the user table
     *
     * @param item, a userEntity
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<UserEntity>)

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
     * Delete all user records in the user table
     *
     * @return void
     */
    @Query("DELETE FROM user")
    suspend fun deleteAll()

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
package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.UserEntity

@Dao
interface UserDao {


    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user WHERE name = :name")
    suspend fun getByName(name: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: UserEntity)

    @Update
    suspend fun update(item: UserEntity)

    @Delete
    suspend fun delete(item: UserEntity)
}
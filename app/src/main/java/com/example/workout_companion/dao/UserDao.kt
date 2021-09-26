package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.UserEntity
import java.time.LocalDate
import java.util.*

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

    //@Query("SELECT round((julianday(date('now')) -  julianday(birth_date))/365 - 0.5) AS age FROM user")
    //@Query("SELECT cast(strftime('%Y.%m%d', 'now') - strftime('%Y.%m%d', birth_date) as int) as age FROM User where name = :name")
    @Query("SELECT birth_date FROM user WHERE name = :name")
    suspend fun getBirthDate(name: String): LocalDate

    @Query("SELECT CAST(round((julianday(date('now')) -  julianday(birth_date))/365 - 0.5)as INTEGER) AS age FROM user WHERE name = :name")
    suspend fun getAge(name: String): Int
}
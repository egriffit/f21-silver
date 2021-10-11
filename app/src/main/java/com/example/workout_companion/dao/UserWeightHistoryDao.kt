package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.UserWeightHistoryEntity

/**
 * The Data Abstraction Object for the UserWeightHistoryEntity
 *
 * Provides methods for SQL queries using the UserWeightHistoryEntity
 */
@Dao
interface UserWeightHistoryDao {

    /**
     * Retrieves a List of UserWeightHistoryEntity objects from the user_weight_history table
     *
     * @return LiveData<List<UserEntity> a list of UserEntity objects
     */
    @Query("SELECT * FROM user_weight_history WHERE user_weight_history.id")
    fun getUserWeightHistory(): LiveData<UserWeightHistoryEntity>


    /**
     * Delete user_weight_history record to clear user goals
     *
     * @return void
     */
    @Transaction
    @Query("""DELETE FROM user_weight_history
                     WHERE user_weight_history.id = 1
                                            """)
    suspend fun delete()
}
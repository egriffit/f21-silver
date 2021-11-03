package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Relation
import com.example.workout_companion.entity.GoalTypeEntity
import com.example.workout_companion.entity.UserEntity

/**
 * Class that joins a [UserEntity] and [GoalTypeEntity]
 *
 * @property user The user
 * @property goal The goal that matches the user.goal_id
 */
data class UserWithGoal(
    @Embedded
    val user: UserEntity,

    @Relation(
        parentColumn = "goal_id",
        entityColumn = "id",
    )
    val goal: GoalTypeEntity
)

/**
 * Data Access Object for getting the database's user and their connected goal
 */
@Dao
interface UserWithGoalDao {

    /**
     * Gets the user and goal as [LiveData]
     *
     * @return A [LiveData] object containing the [UserWithGoal]
     */
    @Query("SELECT user.*, goal_type.* FROM user INNER JOIN goal_type ON user.goal_id = goal_type.id")
    fun getUserWithGoal(): LiveData<UserWithGoal>
}
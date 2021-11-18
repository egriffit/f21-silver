package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A table of user fitness goals (e.g. Lose Weight, Build Muscle)
 *
 * @property id                 the primary key of the entity.
 * @property goal               the name describing the goal.
 * @property caloric_addition   the number of calories above or below maintenance needed to reach the goal.
 */
@Entity(tableName = "goal_type")
data class GoalTypeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "goal")
    var goal: String,

    @ColumnInfo(name = "caloric_addition")
    var caloric_addition: Int,
)
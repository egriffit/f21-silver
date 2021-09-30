package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A table of user fitness goals (e.g. Lose Weight, Build Muscle)
 *
 * @property id    the primary key of the entity.
 * @property name  the name describing the goal.
 */
@Entity(tableName = "goal_type")
data class GoalTypeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,
)
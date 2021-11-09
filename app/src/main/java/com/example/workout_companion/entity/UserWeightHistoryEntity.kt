package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A user_weight_history table in the database
 *
 * @property user_weight_date       the date of the recorded weight
 * @property user_weight_name       the name of the user
 * @property user_weight        the weight of the user
 */
@Entity(tableName = "user_weight_history")
data class UserWeightHistoryEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "user_weight_date")
    var user_weight_date: Int,

    @ColumnInfo(name = "user_weight_name")
    var user_weight_name: String,

    @ColumnInfo(name = "user_weight")
    var user_weight: Double
)

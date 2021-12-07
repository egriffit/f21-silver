package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.workout_companion.enumeration.NutritionStatusEnum
import java.time.LocalDate

/**
 * An entity for the nutrition_status table in the database
 *
 * @property id             primary key
 * @property status         nutrition status enumeration value
 * @property date           date on which status was recorded
 */
@Entity(tableName = "nutrition_status")
data class NutritionStatusEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "status")
    var status: NutritionStatusEnum,

    @ColumnInfo(name = "date")
    var date: LocalDate,
)
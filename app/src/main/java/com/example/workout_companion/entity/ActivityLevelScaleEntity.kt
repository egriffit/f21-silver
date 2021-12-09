package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 *
 * An entity for the activity_level
 *
 * Stores the scaling factor for activity level used for calculations
 *
 * @property id                         Primary key, id
 * @property activity_level             Activity level name
 * @property scale                      Scale in decimal
 * @property name_of_calculation        Name
 *
 */

@Entity(tableName = "activity_level_scale")
data class ActivityLevelScaleEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "activity_level")
    var activity_level: String,

    @ColumnInfo(name = "scale")
    var scale: Double,

    @ColumnInfo(name = "name_of_calculations")
    var name_of_calculation: String
) {

}



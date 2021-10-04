package com.example.workout_companion.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * An entity representing the framework_day table in the database
 *
 * @property id                 the primary key id of each entry in the table.
 * @property framework_type_id  the foreign key pointing to the framework_type that contains this day.
 * @property name               the name of the day
 */
@Entity(tableName = "framework_day",
        foreignKeys = [ForeignKey(entity = FrameworkTypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["framework_type_id"],
            onDelete = ForeignKey.CASCADE)
        ]
)
data class FrameworkDayEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "framework_type_id", index = true)
    var framework_type_id: Int,

    @ColumnInfo(name = "name")
    var name: String,
)
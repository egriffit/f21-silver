package com.example.workout_companion.entity

import androidx.room.*
import com.example.workout_companion.utility.DateTimeConverter
import java.time.LocalDate

/**
 * An entity representing the framework_component_set table in the database
 *
 * @property id The primary key of each entry.
 * @property workout_date The date of the workout as a foreign key.
 * @property component_id The id of the framework component as a foreign key.
 */
@Entity(tableName = "framework_component_set",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["date"],
            childColumns = ["workout_date"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = FrameworkComponentEntity::class,
            parentColumns = ["id"],
            childColumns = ["component_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class FrameworkComponentSetEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @TypeConverters(DateTimeConverter::class)
    @ColumnInfo(name = "workout_date", index = true)
    var workout_date: LocalDate,

    @ColumnInfo(name = "component_id", index = true)
    var component_id: Int,
)

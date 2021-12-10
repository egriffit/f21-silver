package com.example.workout_companion.entity

import androidx.room.*
import com.example.workout_companion.utility.DateTimeConverter
import java.time.LocalDate

/**
 * An entity representing the workout_component table in the database
 *
 * @property id The primary key of each entry.
 * @property workout_date The date of the workout as a foreign key.
 * @property component_id The id of the framework component as a foreign key.
 */
@Entity(tableName = "workout_component",
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
data class WorkoutComponentEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @TypeConverters(DateTimeConverter::class)
    @ColumnInfo(name = "workout_date", index = true)
    var workout_date: LocalDate,

    @ColumnInfo(name = "component_id", index = true)
    var component_id: Int,

    @ColumnInfo(name = "wger_id")
    var wger_id: Int? = null,
) {
    constructor(date: LocalDate, componentId: Int) : this(0, date, componentId, null)
}

package com.example.workout_companion.entity

import androidx.room.*
import com.example.workout_companion.enumeration.Progress
import com.example.workout_companion.enumeration.ProgressConverter

/**
 * Entity representing the sets performed during a workout stored in the database
 *
 * @param id The primary key id
 * @param framework_component_set_id The foreign key id of the framework component this set comes from
 * @param reps The repetitions performed during this set
 * @param weight The weight used during this set
 * @param status The state of the set
 * @param wger_id The foreign key id of the wger exercise
 */
@Entity(tableName = "set",
        foreignKeys = [
            ForeignKey(
                entity = WorkoutComponentEntity::class,
                parentColumns = ["id"],
                childColumns = ["framework_component_set_id"],
                onDelete = ForeignKey.CASCADE
            )
        ]
)
data class SetEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "framework_component_set_id", index = true)
    var framework_component_set_id: Int,

    @ColumnInfo(name = "reps")
    var reps: Int,

    @ColumnInfo(name = "weight")
    var weight: Double,

    @TypeConverters(ProgressConverter::class)
    @ColumnInfo(name = "status")
    var status: Progress,

    @ColumnInfo(name = "wger_id")
    var wger_id: Int,
)

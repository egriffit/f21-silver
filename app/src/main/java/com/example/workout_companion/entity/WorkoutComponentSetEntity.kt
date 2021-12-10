package com.example.workout_companion.entity

import androidx.room.*
import com.example.workout_companion.enumeration.Progress
import com.example.workout_companion.enumeration.ProgressConverter

/**
 * Entity representing the sets performed during a workout stored in the database
 *
 * @param id The primary key id
 * @param workout_component_id The foreign key id of the framework component this set comes from
 * @param reps The repetitions performed during this set
 * @param weight The weight used during this set
 * @param status The state of the set
 * @param wger_id The foreign key id of the wger exercise or null if it is not selected
 */
@Entity(tableName = "workout_component_set",
        foreignKeys = [
            ForeignKey(
                entity = WorkoutComponentEntity::class,
                parentColumns = ["id"],
                childColumns = ["workout_component_id"],
                onDelete = ForeignKey.CASCADE
            )
        ]
)
data class WorkoutComponentSetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "workout_component_id", index = true)
    var workout_component_id: Int,

    @ColumnInfo(name = "reps")
    var reps: Int = 0,

    @ColumnInfo(name = "weight")
    var weight: Double = 0.0,

    @TypeConverters(ProgressConverter::class)
    @ColumnInfo(name = "status")
    var status: Progress = Progress.IN_PROGRESS,
) {
    constructor(componentId: Int, reps: Int, weight: Double, status: Progress) :
            this(0, componentId, reps, weight, status)
}

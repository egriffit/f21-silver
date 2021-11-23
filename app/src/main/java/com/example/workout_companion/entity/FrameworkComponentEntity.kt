package com.example.workout_companion.entity

import androidx.room.*
import com.example.workout_companion.enumeration.MuscleGroup
import com.example.workout_companion.enumeration.MuscleGroupConverter

/**
 * A framework_component table in the database
 *
 * @property id     the primary key
 * @property framework_day_id   the foreign key to the framework day.
 * @property muscle_group       the muscle group.
 * @property target_sets        the target sets of the component.
 * @property target_reps        the target total reps performed across all sets.
 */
@Entity(tableName = "framework_component",
        foreignKeys = [ForeignKey(entity = FrameworkDayEntity::class,
            parentColumns = ["id"],
            childColumns = ["framework_day_id"],
            onDelete = ForeignKey.CASCADE)]
)
data class FrameworkComponentEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "framework_day_id", index = true)
    var framework_day_id: Int,

    @ColumnInfo(name = "muscle_group")
    @TypeConverters(MuscleGroupConverter::class)
    var muscle_group: MuscleGroup,

    @ColumnInfo(name = "target_sets")
    var target_sets: Int,

    @ColumnInfo(name = "target_reps")
    var target_reps: Int,
)
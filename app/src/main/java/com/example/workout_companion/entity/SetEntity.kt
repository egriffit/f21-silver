package com.example.workout_companion.entity

import androidx.room.*
import com.example.workout_companion.enumeration.Progress
import com.example.workout_companion.enumeration.ProgressConverter

@Entity(tableName = "set",
        foreignKeys = [
            ForeignKey(
                entity = FrameworkComponentSetEntity::class,
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

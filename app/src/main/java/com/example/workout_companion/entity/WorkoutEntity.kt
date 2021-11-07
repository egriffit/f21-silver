package com.example.workout_companion.entity

import androidx.room.*
import com.example.workout_companion.enumeration.Progress
import com.example.workout_companion.enumeration.ProgressConverter
import com.example.workout_companion.utility.DateTimeConverter
import java.time.LocalDate

@Entity(tableName = "workout",
        foreignKeys = [ForeignKey(entity = FrameworkTypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["framework_id"],
            onDelete = ForeignKey.NO_ACTION
        )]
)
data class WorkoutEntity(
    @PrimaryKey
    @ColumnInfo(name = "date")
    @TypeConverters(DateTimeConverter::class)
    var date: LocalDate,

    @ColumnInfo(name = "status")
    @TypeConverters(ProgressConverter::class)
    var status: Progress,

    @ColumnInfo(name = "framework_id", index = true)
    var framework_id: Int,
)

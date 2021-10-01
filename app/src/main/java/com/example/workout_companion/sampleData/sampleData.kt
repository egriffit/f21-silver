package com.example.workout_companion.sampleData

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.workout_companion.entity.CurrentUserGoalEntity
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.mock.entity.FrameworkWithGoalEntity
import java.time.LocalDate
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
val birthDate : LocalDate = LocalDate.of (1990, Month.JANUARY, 1)
val user = UserEntity("John Smith", "beginner", "male", birthDate, 2, "moderate")

val FrameWorkList : List<FrameworkWithGoalEntity> = listOf(
    FrameworkWithGoalEntity(2, "Frame_work_2", 1, 2, "Gain Mass"),
    FrameworkWithGoalEntity(5, "Frame_work_5", 2, 2, "Gain Mass"),
    FrameworkWithGoalEntity(6, "Frame_work_6", 3, 2, "Gain Mass"),
    FrameworkWithGoalEntity(6, "Frame_work_7", 1, 2, "Gain Mass"),
)

val currentUser = CurrentUserGoalEntity(1, 2)
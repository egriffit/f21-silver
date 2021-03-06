package com.example.workout_companion.sampleData

import com.example.workout_companion.entity.CurrentUserGoalEntity
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.entity.FrameworkWithGoalEntity
import com.example.workout_companion.enumeration.ActivityLevel
import com.example.workout_companion.enumeration.ExperienceLevel
import com.example.workout_companion.enumeration.Sex
import java.time.LocalDate
import java.time.Month

val user = UserEntity("John Smith", ExperienceLevel.BEGINNER, Sex.MALE, LocalDate.of (1990, Month.JANUARY, 1), 2, 167.64, 75.0, ActivityLevel.MODERATELY_ACTIVE, 0)

val FrameWorkList : List<FrameworkWithGoalEntity> = listOf(
    FrameworkWithGoalEntity(2, "Frame_work_2", 1, 2, "Gain Mass"),
    FrameworkWithGoalEntity(5, "Frame_work_5", 2, 2, "Gain Mass"),
    FrameworkWithGoalEntity(6, "Frame_work_6", 3, 2, "Gain Mass"),
    FrameworkWithGoalEntity(6, "Frame_work_7", 1, 2, "Gain Mass"),
)

val currentUser = CurrentUserGoalEntity(1,1, 2)
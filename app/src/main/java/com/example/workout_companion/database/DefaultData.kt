package com.example.workout_companion.database

import com.example.workout_companion.entity.FrameworkComponentEntity
import com.example.workout_companion.entity.FrameworkDayEntity
import com.example.workout_companion.entity.FrameworkTypeEntity
import com.example.workout_companion.entity.GoalTypeEntity
import com.example.workout_companion.enumeration.MuscleGroup

/**
 * A collection of the goals that exist in our model. Add any new goals here
 * and they will be added to the database automatically.
 */
val GOALS = mapOf(
    Pair(0, GoalTypeEntity(0, "Build Muscle", 250)),
    Pair(1, GoalTypeEntity(1, "Gain Strength", 500)),
    Pair(2, GoalTypeEntity(2, "Lose Weight", -500)),
)

val FRAMEWORK_TYPES = listOf(
    FrameworkTypeEntity(0, "Full Body", 1, 3),
    FrameworkTypeEntity(1, "Push/Pull", 0, 4),
    FrameworkTypeEntity(2, "Upper/Lower", 2, 4),
)

val FRAMEWORK_DAYS = listOf(
    // Full Body
    FrameworkDayEntity(0, 0, "Day A"),
    FrameworkDayEntity(1, 0, "Day B"),
    FrameworkDayEntity(2, 0, "Day C"),

    // Push/Pull
    FrameworkDayEntity(3, 1, "Push A"),
    FrameworkDayEntity(4, 1, "Pull A"),
    FrameworkDayEntity(5, 1, "Push B"),
    FrameworkDayEntity(6, 1, "Pull B"),

    // Upper/Lower
    FrameworkDayEntity(7, 2, "Upper A"),
    FrameworkDayEntity(8, 2, "Lower A"),
    FrameworkDayEntity(9, 2, "Upper B"),
    FrameworkDayEntity(10, 2, "Lower B"),
)

val FRAMEWORK_COMPONENTS = listOf(
    // Full Body - Day A
    FrameworkComponentEntity(0, 0, MuscleGroup.BACK, 4, 32),
    FrameworkComponentEntity(1, 0, MuscleGroup.CHEST, 4, 32),
    FrameworkComponentEntity(2, 0, MuscleGroup.LEGS, 4, 32),
    FrameworkComponentEntity(3, 0, MuscleGroup.BACK, 3, 30),
    FrameworkComponentEntity(4, 0, MuscleGroup.CHEST, 3, 30),
    FrameworkComponentEntity(5, 0, MuscleGroup.LEGS, 3, 30),

    // Full Body - Day B
    FrameworkComponentEntity(6, 1, MuscleGroup.BACK, 4, 32),
    FrameworkComponentEntity(7, 1, MuscleGroup.CHEST, 4, 32),
    FrameworkComponentEntity(8, 1, MuscleGroup.LEGS, 4, 32),
    FrameworkComponentEntity(9, 1, MuscleGroup.BACK, 3, 30),
    FrameworkComponentEntity(10, 1, MuscleGroup.CHEST, 3, 30),
    FrameworkComponentEntity(11, 1, MuscleGroup.LEGS, 3, 30),

    // Full Body - Day C
    FrameworkComponentEntity(12, 2, MuscleGroup.BACK, 4, 32),
    FrameworkComponentEntity(13, 2, MuscleGroup.CHEST, 4, 32),
    FrameworkComponentEntity(14, 2, MuscleGroup.LEGS, 4, 32),
    FrameworkComponentEntity(15, 2, MuscleGroup.BACK, 3, 30),
    FrameworkComponentEntity(16, 2, MuscleGroup.CHEST, 3, 30),
    FrameworkComponentEntity(17, 2, MuscleGroup.LEGS, 3, 30),

    // Push/Pull - Push A
    FrameworkComponentEntity(18, 3, MuscleGroup.CHEST, 4, 28),
    FrameworkComponentEntity(19, 3, MuscleGroup.CHEST, 3, 30),
    FrameworkComponentEntity(20, 3, MuscleGroup.SHOULDERS, 4, 32),
    FrameworkComponentEntity(21, 3, MuscleGroup.TRICEPS, 3, 36),

    // Push/Pull - Pull A
    FrameworkComponentEntity(22, 4, MuscleGroup.LEGS, 4, 28),
    FrameworkComponentEntity(23, 4, MuscleGroup.LEGS, 3, 30),
    FrameworkComponentEntity(24, 4, MuscleGroup.BACK, 4, 28),
    FrameworkComponentEntity(25, 4, MuscleGroup.BACK, 3, 30),
    FrameworkComponentEntity(26, 4, MuscleGroup.BICEPS, 3, 36),

    // Push/Pull - Push B
    FrameworkComponentEntity(27, 5, MuscleGroup.CHEST, 4, 28),
    FrameworkComponentEntity(28, 5, MuscleGroup.CHEST, 3, 30),
    FrameworkComponentEntity(29, 5, MuscleGroup.SHOULDERS, 4, 32),
    FrameworkComponentEntity(30, 5, MuscleGroup.TRICEPS, 3, 36),

    // Push/Pull - Pull B
    FrameworkComponentEntity(31, 6, MuscleGroup.LEGS, 4, 28),
    FrameworkComponentEntity(32, 6, MuscleGroup.LEGS, 3, 30),
    FrameworkComponentEntity(33, 6, MuscleGroup.BACK, 4, 28),
    FrameworkComponentEntity(34, 6, MuscleGroup.BACK, 3, 30),
    FrameworkComponentEntity(35, 6, MuscleGroup.BICEPS, 3, 36),

    // Upper/Lower - Upper A
    FrameworkComponentEntity(36, 7, MuscleGroup.CHEST, 4, 28),
    FrameworkComponentEntity(37, 7, MuscleGroup.BACK, 4, 28),
    FrameworkComponentEntity(38, 7, MuscleGroup.SHOULDERS, 3, 30),
    FrameworkComponentEntity(39, 7, MuscleGroup.TRICEPS, 3, 30),
    FrameworkComponentEntity(40, 7, MuscleGroup.BICEPS, 3, 30),

    // Upper/Lower - Lower A
    FrameworkComponentEntity(41, 8, MuscleGroup.LEGS, 5, 35),
    FrameworkComponentEntity(42, 8, MuscleGroup.LEGS, 4, 40),
    FrameworkComponentEntity(43, 8, MuscleGroup.LEGS, 3, 30),
    FrameworkComponentEntity(44, 8, MuscleGroup.CALVES, 3, 45),

    // Upper/Lower - Upper B
    FrameworkComponentEntity(45, 9, MuscleGroup.CHEST, 4, 28),
    FrameworkComponentEntity(46, 9, MuscleGroup.BACK, 4, 28),
    FrameworkComponentEntity(47, 9, MuscleGroup.SHOULDERS, 3, 30),
    FrameworkComponentEntity(48, 9, MuscleGroup.TRICEPS, 3, 30),
    FrameworkComponentEntity(49, 9, MuscleGroup.BICEPS, 3, 30),

    // Upper/Lower - Lower B
    FrameworkComponentEntity(50, 10, MuscleGroup.LEGS, 5, 35),
    FrameworkComponentEntity(51, 10, MuscleGroup.LEGS, 4, 40),
    FrameworkComponentEntity(52, 10, MuscleGroup.LEGS, 3, 30),
    FrameworkComponentEntity(53, 10, MuscleGroup.CALVES, 3, 45),
)
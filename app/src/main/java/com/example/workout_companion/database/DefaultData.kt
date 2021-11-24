package com.example.workout_companion.database

import com.example.workout_companion.dao.FrameworkDayWithComponents
import com.example.workout_companion.dao.FrameworkWithDays
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
    Pair(0, GoalTypeEntity(1, "Build Muscle", 250)),
    Pair(1, GoalTypeEntity(2, "Gain Strength", 500)),
    Pair(2, GoalTypeEntity(3, "Lose Weight", -500)),
)

val FRAMEWORK_TYPES = listOf(
    FrameworkTypeEntity(1, "Full Body", 2, 3),
    FrameworkTypeEntity(2, "Push/Pull", 1, 4),
    FrameworkTypeEntity(3, "Upper/Lower", 3, 4),
)


private val FULL_BODY_FRAMEWORK = FrameworkTypeEntity(1, "Full Body", 2, 3)
private val FULL_BODY_DAY_A_WITH_COMPONENTS = FrameworkDayWithComponents(
    day = FrameworkDayEntity(1, 1, "Day A"),
    components = listOf(
        FrameworkComponentEntity(1, 1, MuscleGroup.BACK, 4, 32),
        FrameworkComponentEntity(2, 1, MuscleGroup.CHEST, 4, 32),
        FrameworkComponentEntity(3, 1, MuscleGroup.LEGS, 4, 32),
        FrameworkComponentEntity(4, 1, MuscleGroup.BACK, 3, 30),
        FrameworkComponentEntity(5, 1, MuscleGroup.CHEST, 3, 30),
        FrameworkComponentEntity(6, 1, MuscleGroup.LEGS, 3, 30),
    )
)
private val FULL_BODY_DAY_B_WITH_COMPONENTS = FrameworkDayWithComponents(
    day = FrameworkDayEntity(2, 1, "Day B"),
    components = listOf(
        FrameworkComponentEntity(7, 2, MuscleGroup.BACK, 4, 32),
        FrameworkComponentEntity(8, 2, MuscleGroup.CHEST, 4, 32),
        FrameworkComponentEntity(9, 2, MuscleGroup.LEGS, 4, 32),
        FrameworkComponentEntity(10, 2, MuscleGroup.BACK, 3, 30),
        FrameworkComponentEntity(11, 2, MuscleGroup.CHEST, 3, 30),
        FrameworkComponentEntity(12, 2, MuscleGroup.LEGS, 3, 30),
    )
)
private val FULL_BODY_DAY_C_WITH_COMPONENTS = FrameworkDayWithComponents(
    day = FrameworkDayEntity(3, 1, "Day C"),
    components = listOf(
        FrameworkComponentEntity(13, 3, MuscleGroup.BACK, 4, 32),
        FrameworkComponentEntity(14, 3, MuscleGroup.CHEST, 4, 32),
        FrameworkComponentEntity(15, 3, MuscleGroup.LEGS, 4, 32),
        FrameworkComponentEntity(16, 3, MuscleGroup.BACK, 3, 30),
        FrameworkComponentEntity(17, 3, MuscleGroup.CHEST, 3, 30),
        FrameworkComponentEntity(18, 3, MuscleGroup.LEGS, 3, 30),
    )
)
private val FULL_BODY_WITH_DAYS = FrameworkWithDays(
    framework = FULL_BODY_FRAMEWORK,
    days = listOf(
        FULL_BODY_DAY_A_WITH_COMPONENTS,
        FULL_BODY_DAY_B_WITH_COMPONENTS,
        FULL_BODY_DAY_C_WITH_COMPONENTS,
    )
)


private val PUSH_PULL_FRAMEWORK = FrameworkTypeEntity(2, "Push/Pull", 1, 4)
private val PUSH_A = FrameworkDayWithComponents(
    day = FrameworkDayEntity(4, 2, "Push A"),
    components = listOf(
        FrameworkComponentEntity(19, 4, MuscleGroup.CHEST, 4, 28),
        FrameworkComponentEntity(20, 4, MuscleGroup.CHEST, 3, 30),
        FrameworkComponentEntity(21, 4, MuscleGroup.SHOULDERS, 4, 32),
        FrameworkComponentEntity(22, 4, MuscleGroup.TRICEPS, 3, 36),
    )
)
private val PULL_A = FrameworkDayWithComponents(
    day =     FrameworkDayEntity(5, 2, "Pull A"),
    components = listOf(
        FrameworkComponentEntity(23, 5, MuscleGroup.LEGS, 4, 28),
        FrameworkComponentEntity(24, 5, MuscleGroup.LEGS, 3, 30),
        FrameworkComponentEntity(25, 5, MuscleGroup.BACK, 4, 28),
        FrameworkComponentEntity(26, 5, MuscleGroup.BACK, 3, 30),
        FrameworkComponentEntity(27, 5, MuscleGroup.BICEPS, 3, 36),
    )
)
private val PUSH_B = FrameworkDayWithComponents(
    day = FrameworkDayEntity(6, 2, "Push B"),
    components = listOf(
        FrameworkComponentEntity(28, 6, MuscleGroup.CHEST, 4, 28),
        FrameworkComponentEntity(29, 6, MuscleGroup.CHEST, 3, 30),
        FrameworkComponentEntity(30, 6, MuscleGroup.SHOULDERS, 4, 32),
        FrameworkComponentEntity(31, 6, MuscleGroup.TRICEPS, 3, 36),
    )
)
private val PULL_B = FrameworkDayWithComponents(
    day = FrameworkDayEntity(7, 2, "Pull B"),
    components = listOf(
        FrameworkComponentEntity(32, 7, MuscleGroup.LEGS, 4, 28),
        FrameworkComponentEntity(33, 7, MuscleGroup.LEGS, 3, 30),
        FrameworkComponentEntity(34, 7, MuscleGroup.BACK, 4, 28),
        FrameworkComponentEntity(35, 7, MuscleGroup.BACK, 3, 30),
        FrameworkComponentEntity(36, 7, MuscleGroup.BICEPS, 3, 36),
    )
)
private val PUSH_PULL_WITH_DAYS = FrameworkWithDays(
    framework = PUSH_PULL_FRAMEWORK,
    days = listOf(
        PUSH_A,
        PULL_A,
        PUSH_B,
        PULL_B,
    )
)


private val UPPER_LOWER_FRAMEWORK = FrameworkTypeEntity(3, "Upper/Lower", 3, 4)
private val UPPER_A = FrameworkDayWithComponents(
    day = FrameworkDayEntity(8, 3, "Upper A"),
    components = listOf(
        FrameworkComponentEntity(37, 8, MuscleGroup.CHEST, 4, 28),
        FrameworkComponentEntity(38, 8, MuscleGroup.BACK, 4, 28),
        FrameworkComponentEntity(39, 8, MuscleGroup.SHOULDERS, 3, 30),
        FrameworkComponentEntity(40, 8, MuscleGroup.TRICEPS, 3, 30),
        FrameworkComponentEntity(41, 8, MuscleGroup.BICEPS, 3, 30),
    )
)
private val LOWER_A = FrameworkDayWithComponents(
    day = FrameworkDayEntity(9, 3, "Lower A"),
    components = listOf(
        FrameworkComponentEntity(42, 9, MuscleGroup.LEGS, 5, 35),
        FrameworkComponentEntity(43, 9, MuscleGroup.LEGS, 4, 40),
        FrameworkComponentEntity(44, 9, MuscleGroup.LEGS, 3, 30),
        FrameworkComponentEntity(45, 9, MuscleGroup.CALVES, 3, 45),
    )
)
private val UPPER_B = FrameworkDayWithComponents(
    day = FrameworkDayEntity(10, 3, "Upper B"),
    components = listOf(
        FrameworkComponentEntity(46, 10, MuscleGroup.CHEST, 4, 28),
        FrameworkComponentEntity(47, 10, MuscleGroup.BACK, 4, 28),
        FrameworkComponentEntity(48, 10, MuscleGroup.SHOULDERS, 3, 30),
        FrameworkComponentEntity(49, 10, MuscleGroup.TRICEPS, 3, 30),
        FrameworkComponentEntity(50, 10, MuscleGroup.BICEPS, 3, 30),
    )
)
private val LOWER_B = FrameworkDayWithComponents(
    day = FrameworkDayEntity(11, 3, "Lower B"),
    components = listOf(
        FrameworkComponentEntity(51, 11, MuscleGroup.LEGS, 5, 35),
        FrameworkComponentEntity(52, 11, MuscleGroup.LEGS, 4, 40),
        FrameworkComponentEntity(53, 11, MuscleGroup.LEGS, 3, 30),
        FrameworkComponentEntity(54, 11, MuscleGroup.CALVES, 3, 45),
    )
)
private val UPPER_LOWER_WITH_DAYS = FrameworkWithDays(
    framework = UPPER_LOWER_FRAMEWORK,
    days = listOf(
        UPPER_A,
        LOWER_A,
        UPPER_B,
        LOWER_B,
    )
)


val DEFAULT_FRAMEWORKS_WITH_DAYS = listOf(
    FULL_BODY_WITH_DAYS,
    PUSH_PULL_WITH_DAYS,
    UPPER_LOWER_WITH_DAYS,
)


val FRAMEWORK_DAYS = listOf(
    // Full Body
    FrameworkDayEntity(1, 1, "Day A"),
    FrameworkDayEntity(2, 1, "Day B"),
    FrameworkDayEntity(3, 1, "Day C"),

    // Push/Pull
    FrameworkDayEntity(4, 2, "Push A"),
    FrameworkDayEntity(5, 2, "Pull A"),
    FrameworkDayEntity(6, 2, "Push B"),
    FrameworkDayEntity(7, 2, "Pull B"),

    // Upper/Lower
    FrameworkDayEntity(8, 3, "Upper A"),
    FrameworkDayEntity(9, 3, "Lower A"),
    FrameworkDayEntity(10, 3, "Upper B"),
    FrameworkDayEntity(11, 3, "Lower B"),
)

val FRAMEWORK_COMPONENTS = listOf(
    // Full Body - Day A
    FrameworkComponentEntity(1, 1, MuscleGroup.BACK, 4, 32),
    FrameworkComponentEntity(2, 1, MuscleGroup.CHEST, 4, 32),
    FrameworkComponentEntity(3, 1, MuscleGroup.LEGS, 4, 32),
    FrameworkComponentEntity(4, 1, MuscleGroup.BACK, 3, 30),
    FrameworkComponentEntity(5, 1, MuscleGroup.CHEST, 3, 30),
    FrameworkComponentEntity(6, 1, MuscleGroup.LEGS, 3, 30),

    // Full Body - Day B
    FrameworkComponentEntity(7, 2, MuscleGroup.BACK, 4, 32),
    FrameworkComponentEntity(8, 2, MuscleGroup.CHEST, 4, 32),
    FrameworkComponentEntity(9, 2, MuscleGroup.LEGS, 4, 32),
    FrameworkComponentEntity(10, 2, MuscleGroup.BACK, 3, 30),
    FrameworkComponentEntity(11, 2, MuscleGroup.CHEST, 3, 30),
    FrameworkComponentEntity(12, 2, MuscleGroup.LEGS, 3, 30),

    // Full Body - Day C
    FrameworkComponentEntity(13, 3, MuscleGroup.BACK, 4, 32),
    FrameworkComponentEntity(14, 3, MuscleGroup.CHEST, 4, 32),
    FrameworkComponentEntity(15, 3, MuscleGroup.LEGS, 4, 32),
    FrameworkComponentEntity(16, 3, MuscleGroup.BACK, 3, 30),
    FrameworkComponentEntity(17, 3, MuscleGroup.CHEST, 3, 30),
    FrameworkComponentEntity(18, 3, MuscleGroup.LEGS, 3, 30),

    // Push/Pull - Push A
    FrameworkComponentEntity(19, 4, MuscleGroup.CHEST, 4, 28),
    FrameworkComponentEntity(20, 4, MuscleGroup.CHEST, 3, 30),
    FrameworkComponentEntity(21, 4, MuscleGroup.SHOULDERS, 4, 32),
    FrameworkComponentEntity(22, 4, MuscleGroup.TRICEPS, 3, 36),

    // Push/Pull - Pull A
    FrameworkComponentEntity(23, 5, MuscleGroup.LEGS, 4, 28),
    FrameworkComponentEntity(24, 5, MuscleGroup.LEGS, 3, 30),
    FrameworkComponentEntity(25, 5, MuscleGroup.BACK, 4, 28),
    FrameworkComponentEntity(26, 5, MuscleGroup.BACK, 3, 30),
    FrameworkComponentEntity(27, 5, MuscleGroup.BICEPS, 3, 36),

    // Push/Pull - Push B
    FrameworkComponentEntity(28, 6, MuscleGroup.CHEST, 4, 28),
    FrameworkComponentEntity(29, 6, MuscleGroup.CHEST, 3, 30),
    FrameworkComponentEntity(30, 6, MuscleGroup.SHOULDERS, 4, 32),
    FrameworkComponentEntity(31, 6, MuscleGroup.TRICEPS, 3, 36),

    // Push/Pull - Pull B
    FrameworkComponentEntity(32, 7, MuscleGroup.LEGS, 4, 28),
    FrameworkComponentEntity(33, 7, MuscleGroup.LEGS, 3, 30),
    FrameworkComponentEntity(34, 7, MuscleGroup.BACK, 4, 28),
    FrameworkComponentEntity(35, 7, MuscleGroup.BACK, 3, 30),
    FrameworkComponentEntity(36, 7, MuscleGroup.BICEPS, 3, 36),

    // Upper/Lower - Upper A
    FrameworkComponentEntity(37, 8, MuscleGroup.CHEST, 4, 28),
    FrameworkComponentEntity(38, 8, MuscleGroup.BACK, 4, 28),
    FrameworkComponentEntity(39, 8, MuscleGroup.SHOULDERS, 3, 30),
    FrameworkComponentEntity(40, 8, MuscleGroup.TRICEPS, 3, 30),
    FrameworkComponentEntity(41, 8, MuscleGroup.BICEPS, 3, 30),

    // Upper/Lower - Lower A
    FrameworkComponentEntity(42, 9, MuscleGroup.LEGS, 5, 35),
    FrameworkComponentEntity(43, 9, MuscleGroup.LEGS, 4, 40),
    FrameworkComponentEntity(44, 9, MuscleGroup.LEGS, 3, 30),
    FrameworkComponentEntity(45, 9, MuscleGroup.CALVES, 3, 45),

    // Upper/Lower - Upper B
    FrameworkComponentEntity(46, 10, MuscleGroup.CHEST, 4, 28),
    FrameworkComponentEntity(47, 10, MuscleGroup.BACK, 4, 28),
    FrameworkComponentEntity(48, 10, MuscleGroup.SHOULDERS, 3, 30),
    FrameworkComponentEntity(49, 10, MuscleGroup.TRICEPS, 3, 30),
    FrameworkComponentEntity(50, 10, MuscleGroup.BICEPS, 3, 30),

    // Upper/Lower - Lower B
    FrameworkComponentEntity(51, 11, MuscleGroup.LEGS, 5, 35),
    FrameworkComponentEntity(52, 11, MuscleGroup.LEGS, 4, 40),
    FrameworkComponentEntity(53, 11, MuscleGroup.LEGS, 3, 30),
    FrameworkComponentEntity(54, 11, MuscleGroup.CALVES, 3, 45),
)
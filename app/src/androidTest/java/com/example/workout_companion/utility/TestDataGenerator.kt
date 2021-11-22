package com.example.workout_companion.utility

import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.*
import com.example.workout_companion.enumeration.*
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.Month

class TestDataGenerator {

    companion object {

        val USER = UserEntity(
            name = "John Smith",
            experience_level = ExperienceLevel.BEGINNER,
            sex = Sex.MALE,
            birth_date = LocalDate.of (1990, Month.JANUARY, 1),
            max_workouts_per_week = 2,
            height = 160.0,
            weight = 70.0,
            activity_level= ActivityLevel.MODERATELY_ACTIVE,
            goal_id = 0
        )

        @JvmStatic
        fun addUserToDB(db: WCDatabase) = runBlocking {
            db.userDao().insert(USER)
        }

        var next_goal_id = 0
        val GOALS: List<GoalTypeEntity> = listOf(
            GoalTypeEntity(next_goal_id++, "Test Goal 0", 500),
            GoalTypeEntity(next_goal_id++, "Test Goal 1", -500),
            GoalTypeEntity(next_goal_id++, "Test Goal 2", 250),
        )

        @JvmStatic
        fun addGoalsToDB(db: WCDatabase) = runBlocking {
            for (goal in GOALS) {
                db.goalTypeDao().addGoal(goal)
            }
        }

        var next_framework_id = 0
        val GOAL_0_FRAMEWORKS: List<FrameworkTypeEntity> = listOf(
            FrameworkTypeEntity(next_framework_id++, "Test Framework 0", 0, 3),
            FrameworkTypeEntity(next_framework_id++, "Test Framework 1", 0, 5),
        )

        val GOAL_1_FRAMEWORKS: List<FrameworkTypeEntity> = listOf(
            FrameworkTypeEntity(next_framework_id++, "Test Framework 2", 1, 2),
        )

        val GOAL_2_FRAMEWORKS: List<FrameworkTypeEntity> = listOf(
            FrameworkTypeEntity(next_framework_id++, "Test Framework 3", 2, 4),
            FrameworkTypeEntity(next_framework_id++, "Test Framework 4", 2, 6),
            FrameworkTypeEntity(next_framework_id++, "Test Framework 5", 2, 3),
        )

        @JvmStatic
        fun addFrameworksToDB(db: WCDatabase) = runBlocking {
            db.frameworkTypeDao().addFrameworks(
                GOAL_0_FRAMEWORKS
                        + GOAL_1_FRAMEWORKS
                        + GOAL_2_FRAMEWORKS
            )
        }

        var next_day_id = 0
        val FRAMEWORK_0_DAYS: List<FrameworkDayEntity> = listOf(
            FrameworkDayEntity(next_day_id++, 0, "Day 1"),
            FrameworkDayEntity(next_day_id++, 0, "Day 2"),
            FrameworkDayEntity(next_day_id++, 0, "Day 3"),
        )

        val FRAMEWORK_1_DAYS: List<FrameworkDayEntity> = listOf(
            FrameworkDayEntity(next_day_id++, 1, "Chest Day"),
            FrameworkDayEntity(next_day_id++, 1, "Back Day"),
            FrameworkDayEntity(next_day_id++, 1, "Legs Day"),
            FrameworkDayEntity(next_day_id++, 1, "Arms Day"),
            FrameworkDayEntity(next_day_id++, 1, "Shoulders Day"),
        )

        val FRAMEWORK_2_DAYS: List<FrameworkDayEntity> = listOf(
            FrameworkDayEntity(next_day_id++, 2, "Upper Day"),
            FrameworkDayEntity(next_day_id++, 2, "Lower Day"),
        )

        val FRAMEWORK_3_DAYS: List<FrameworkDayEntity> = listOf(
            FrameworkDayEntity(next_day_id++, 3, "Upper Day 1"),
            FrameworkDayEntity(next_day_id++, 3, "Lower Day 1"),
            FrameworkDayEntity(next_day_id++, 3, "Upper Day 2"),
            FrameworkDayEntity(next_day_id++, 3, "Lower Day 2"),
        )

        val FRAMEWORK_4_DAYS: List<FrameworkDayEntity> = listOf(
            FrameworkDayEntity(next_day_id++, 4, "Chest Day"),
            FrameworkDayEntity(next_day_id++, 4, "Triceps Day"),
            FrameworkDayEntity(next_day_id++, 4, "Shoulders Day"),
            FrameworkDayEntity(next_day_id++, 4, "Back Day"),
            FrameworkDayEntity(next_day_id++, 4, "Biceps Day"),
            FrameworkDayEntity(next_day_id++, 4, "Leg Day"),
        )

        val FRAMEWORK_5_DAYS: List<FrameworkDayEntity> = listOf(
            FrameworkDayEntity(next_day_id++, 5, "Full Body Day 1"),
            FrameworkDayEntity(next_day_id++, 5, "Full Body Day 2"),
            FrameworkDayEntity(next_day_id++, 5, "Full Body Day 3"),
        )

        @JvmStatic
        fun addFrameworkDaysToDB(db: WCDatabase) = runBlocking {
            db.frameworkDayDao().addFrameworkDays(FRAMEWORK_0_DAYS
                    + FRAMEWORK_1_DAYS
                    + FRAMEWORK_2_DAYS
                    + FRAMEWORK_3_DAYS
                    + FRAMEWORK_4_DAYS
                    + FRAMEWORK_5_DAYS
            )
        }

        var next_component_id = 0
        val FRAMEWORK_0_DAY_0_COMPONENTS: List<FrameworkComponentEntity> = listOf(
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_0_DAYS[0].id, MuscleGroup.CHEST, 3, 24),
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_0_DAYS[0].id, MuscleGroup.BACK, 3, 24),
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_0_DAYS[0].id, MuscleGroup.LEGS, 3, 24),
        )

        val FRAMEWORK_0_DAY_1_COMPONENTS: List<FrameworkComponentEntity> = listOf(
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_0_DAYS[1].id, MuscleGroup.CHEST, 3, 24),
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_0_DAYS[1].id, MuscleGroup.BACK, 3, 24),
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_0_DAYS[1].id, MuscleGroup.LEGS, 3, 24),
        )

        val FRAMEWORK_0_DAY_2_COMPONENTS: List<FrameworkComponentEntity> = listOf(
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_0_DAYS[2].id, MuscleGroup.CHEST, 3, 30),
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_0_DAYS[2].id, MuscleGroup.BACK, 3, 30),
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_0_DAYS[2].id, MuscleGroup.LEGS, 3, 30),
        )

        val FRAMEWORK_2_DAY_0_COMPONENTS: List<FrameworkComponentEntity> = listOf(
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_2_DAYS[0].id, MuscleGroup.LEGS, 5, 25),
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_2_DAYS[0].id, MuscleGroup.LEGS, 3, 30),
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_2_DAYS[0].id, MuscleGroup.BACK, 3, 30),
        )

        val FRAMEWORK_2_DAY_1_COMPONENTS: List<FrameworkComponentEntity> = listOf(
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_2_DAYS[1].id, MuscleGroup.CHEST, 5, 25),
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_2_DAYS[1].id, MuscleGroup.BACK, 5, 25),
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_2_DAYS[1].id, MuscleGroup.TRICEPS, 3, 30),
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_2_DAYS[1].id, MuscleGroup.BICEPS, 3, 30),
            FrameworkComponentEntity(next_component_id++, FRAMEWORK_2_DAYS[1].id, MuscleGroup.SHOULDERS, 3, 30),
        )

        @JvmStatic
        fun addFrameworkComponentsToDB(db: WCDatabase) = runBlocking {
            db.frameworkComponentDao().addFrameworkComponents(FRAMEWORK_0_DAY_0_COMPONENTS
                + FRAMEWORK_0_DAY_1_COMPONENTS
                + FRAMEWORK_0_DAY_2_COMPONENTS
                + FRAMEWORK_2_DAY_0_COMPONENTS
                + FRAMEWORK_2_DAY_1_COMPONENTS)
        }

        @JvmStatic
        fun getTestFramework(id: Int, goal_id: Int): FrameworkTypeEntity {
            return FrameworkTypeEntity(id, "Framework $id", goal_id, id * id + 1)
        }

        @JvmStatic
        fun getTestFrameworks(numToCreate: Int, goal_id: Int) : List<FrameworkTypeEntity> {
            var frameworks = mutableListOf<FrameworkTypeEntity>()
            for (i in 1..numToCreate) {
                frameworks.add(getTestFramework(i, goal_id))
            }
            return frameworks
        }

        val WORKOUTS: List<WorkoutEntity> = listOf(
            WorkoutEntity(LocalDate.of(2021, 10, 1), Progress.NOT_STARTED, FRAMEWORK_0_DAYS[0].id),
            WorkoutEntity(LocalDate.of(2021, 10, 2), Progress.IN_PROGRESS, FRAMEWORK_2_DAYS[1].id),
            WorkoutEntity(LocalDate.of(2021, 10, 3), Progress.COMPLETE, FRAMEWORK_2_DAYS[0].id),
            WorkoutEntity(LocalDate.of(2021, 10, 4), Progress.NOT_STARTED, FRAMEWORK_0_DAYS[2].id),
        )

        @JvmStatic
        fun addWorkoutsToDB(db: WCDatabase) = runBlocking {
            for (workout in WORKOUTS) {
                db.workoutDao().addWorkout(workout)
            }
        }

        private var nextFrameworkComponentSetId = 0
        val WORKOUT_0_COMPONENTS: List<WorkoutComponentEntity> = listOf(
            WorkoutComponentEntity(nextFrameworkComponentSetId++, WORKOUTS[0].date, FRAMEWORK_0_DAY_0_COMPONENTS[0].id),
            WorkoutComponentEntity(nextFrameworkComponentSetId++, WORKOUTS[0].date, FRAMEWORK_0_DAY_0_COMPONENTS[1].id),
            WorkoutComponentEntity(nextFrameworkComponentSetId++, WORKOUTS[0].date, FRAMEWORK_0_DAY_0_COMPONENTS[2].id),
        )

        val WORKOUT_1_COMPONENTS: List<WorkoutComponentEntity> = listOf(
            WorkoutComponentEntity(nextFrameworkComponentSetId++, WORKOUTS[1].date, FRAMEWORK_2_DAY_1_COMPONENTS[0].id),
            WorkoutComponentEntity(nextFrameworkComponentSetId++, WORKOUTS[1].date, FRAMEWORK_2_DAY_1_COMPONENTS[1].id),
            WorkoutComponentEntity(nextFrameworkComponentSetId++, WORKOUTS[1].date, FRAMEWORK_2_DAY_1_COMPONENTS[2].id),
            WorkoutComponentEntity(nextFrameworkComponentSetId++, WORKOUTS[1].date, FRAMEWORK_2_DAY_1_COMPONENTS[3].id),
            WorkoutComponentEntity(nextFrameworkComponentSetId++, WORKOUTS[1].date, FRAMEWORK_2_DAY_1_COMPONENTS[4].id),
        )

        val WORKOUT_2_COMPONENTS: List<WorkoutComponentEntity> = listOf(
            WorkoutComponentEntity(nextFrameworkComponentSetId++, WORKOUTS[2].date, FRAMEWORK_2_DAY_0_COMPONENTS[0].id),
            WorkoutComponentEntity(nextFrameworkComponentSetId++, WORKOUTS[2].date, FRAMEWORK_2_DAY_0_COMPONENTS[1].id),
            WorkoutComponentEntity(nextFrameworkComponentSetId++, WORKOUTS[2].date, FRAMEWORK_2_DAY_0_COMPONENTS[2].id),
        )

        val WORKOUT_3_COMPONENTS: List<WorkoutComponentEntity> = listOf(
            WorkoutComponentEntity(nextFrameworkComponentSetId++, WORKOUTS[3].date, FRAMEWORK_0_DAY_2_COMPONENTS[0].id),
            WorkoutComponentEntity(nextFrameworkComponentSetId++, WORKOUTS[3].date, FRAMEWORK_0_DAY_2_COMPONENTS[1].id),
            WorkoutComponentEntity(nextFrameworkComponentSetId++, WORKOUTS[3].date, FRAMEWORK_0_DAY_2_COMPONENTS[2].id),
        )

        @JvmStatic
        fun addFrameworkComponentSetsToDB(db: WCDatabase) = runBlocking {
            for (set in WORKOUT_0_COMPONENTS + WORKOUT_1_COMPONENTS + WORKOUT_2_COMPONENTS + WORKOUT_3_COMPONENTS) {
                db.frameworkComponentSetDao().addWorkoutComponent(set)
            }
        }
    }
}

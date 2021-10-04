package com.example.workout_companion.utility

import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkDayEntity
import com.example.workout_companion.entity.FrameworkTypeEntity
import com.example.workout_companion.entity.GoalTypeEntity
import kotlinx.coroutines.runBlocking

class TestDataGenerator {

    companion object {

        var next_goal_id = 0
        val GOALS: List<GoalTypeEntity> = listOf(
            GoalTypeEntity(next_goal_id++, "Test Goal 0"),
            GoalTypeEntity(next_goal_id++, "Test Goal 1"),
            GoalTypeEntity(next_goal_id++, "Test Goal 2"),
        )

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
        fun addGoalsToDB(db: WCDatabase) = runBlocking {
            for (goal in GOALS) {
                db.goalTypeDao().addGoal(goal)
            }
        }

        @JvmStatic
        fun addFrameworksToDB(db: WCDatabase) = runBlocking {
            db.frameworkTypeDao().addFrameworks(GOAL_0_FRAMEWORKS
                    + GOAL_1_FRAMEWORKS
                    + GOAL_2_FRAMEWORKS
            )
        }

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
    }
}

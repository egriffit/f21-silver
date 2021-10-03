package com.example.workout_companion.utility

import com.example.workout_companion.entity.FrameworkTypeEntity

class TestDataGenerator {

    companion object {
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
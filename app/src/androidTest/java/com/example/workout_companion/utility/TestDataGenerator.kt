package com.example.workout_companion.utility

import com.example.workout_companion.entity.FrameworkTypeEntity

class TestDataGenerator {

    companion object {
        @JvmStatic
        fun getTestFramework(id: Int): FrameworkTypeEntity {
            return FrameworkTypeEntity(id, "Framework $id", id * id + 1)
        }

        @JvmStatic
        fun getTestFrameworks(numToCreate: Int) : List<FrameworkTypeEntity> {
            var frameworks = mutableListOf<FrameworkTypeEntity>()
            for (i in 1..numToCreate) {
                frameworks.add(getTestFramework(i))
            }
            return frameworks
        }
    }
}
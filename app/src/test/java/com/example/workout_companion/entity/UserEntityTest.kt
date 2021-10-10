package com.example.workout_companion.entity

import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.Month

class UserEntityTest {

    @Test
    fun constructorTest() {
        val name = "John Smith"
        val experienceLevel = "beginner"
        val sex = "male"
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val maxWorkoutsPerWeek = 2
        val activityLevel = "moderate"
        val user = UserEntity(name, experienceLevel, sex, birthDate, maxWorkoutsPerWeek, activityLevel)

        Assert.assertEquals(user.name, name)
        Assert.assertEquals(user.experience_level, experienceLevel)
        Assert.assertEquals(user.sex, sex)
        Assert.assertEquals(user.birth_date, birthDate)
        Assert.assertEquals(user.max_workouts_per_week, maxWorkoutsPerWeek)
        Assert.assertEquals(user.activity_level, activityLevel)
    }
}
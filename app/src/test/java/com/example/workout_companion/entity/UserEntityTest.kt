package com.example.workout_companion.entity

import com.example.workout_companion.utility.ActivityLevel
import com.example.workout_companion.utility.ExperienceLevel
import com.example.workout_companion.utility.Sex
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.Month

class UserEntityTest {

    @Test
    fun constructorTest() {
        val name = "John Smith"
        val experienceLevel = ExperienceLevel.INTERMEDIATE
        val sex = Sex.MALE
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val maxWorkoutsPerWeek = 2
        val activityLevel = ActivityLevel.MODERATELY_ACTIVE
        val height = 160.0
        val user = UserEntity(name, experienceLevel, sex, birthDate, maxWorkoutsPerWeek, height, activityLevel)

        Assert.assertEquals(user.name, name)
        Assert.assertEquals(user.experience_level, experienceLevel)
        Assert.assertEquals(user.sex, sex)
        Assert.assertEquals(user.birth_date, birthDate)
        Assert.assertEquals(user.max_workouts_per_week, maxWorkoutsPerWeek)
        Assert.assertEquals(user.max_workouts_per_week, maxWorkoutsPerWeek)
        Assert.assertEquals(user.activity_level, activityLevel)
    }
}
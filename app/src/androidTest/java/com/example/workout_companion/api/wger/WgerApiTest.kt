package com.example.workout_companion.api.wger

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.api.nutrition_api_ninja.NutritionApiNinjaApi
import com.example.workout_companion.api.nutrition_api_ninja.Properties.api_key
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
import com.example.workout_companion.api.utility.ExerciseData
import com.example.workout_companion.api.wger.entities.ExerciseInfo
import com.example.workout_companion.api.wger.entities.wgerExercise
import com.example.workout_companion.api.wger.utility.muscleName
import com.example.workout_companion.api.wger.utility.muscleNameConverter.toMuscleName
import com.example.workout_companion.enumeration.MuscleGroupConverter.fromMuscleGroup
import com.example.workout_companion.enumeration.MuscleGroupConverter.toMuscleGroup

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WgerApiTest: TestCase() {

    @Test
    fun testGetExerciseByMuscle() = runBlocking() {
        val api = wgerApi().create(WgerApi::class.java)
        val muscleName = toMuscleName("Biceps brachii")
        val call: wgerExercise = api.getExerciseByMuscle(2, muscleName)
        MatcherAssert.assertThat(
            call.results.elementAt(0).name, CoreMatchers.equalTo("Biceps Curls With Barbell")
        )
    }

    @Test
    fun testGetExerciseById() = runBlocking() {
        val api = wgerApi().create(WgerApi::class.java)
        val call: ExerciseInfo = api.getExerciseById(105, 2)
        MatcherAssert.assertThat(
            call.id, CoreMatchers.equalTo(105))
        MatcherAssert.assertThat(call.name, CoreMatchers.equalTo("Deadlifts"))
    }
}

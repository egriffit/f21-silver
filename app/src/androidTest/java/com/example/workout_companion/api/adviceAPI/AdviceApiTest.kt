package com.example.workout_companion.api.adviceAPI

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.api.adviceAPI.entity.advice
import com.example.workout_companion.api.wger.WgerApi
import com.example.workout_companion.api.wger.entities.wgerExercise
import com.example.workout_companion.api.wger.utility.muscleName
import org.junit.Assert.*

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdviceApiTest: TestCase() {

    @Test
    fun testGetExerciseByMuscle() = runBlocking {
        val api = adviceApi().create(AdviceApi::class.java)
        val adviceType = "gain strength"
        val call: advice = api.getAdviceByType(adviceType)
        MatcherAssert.assertThat(
            call.elementAt(0).adviceType, CoreMatchers.equalTo("gain strength")
        )
    }
}
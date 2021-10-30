package com.example.workout_companion.api.nutrition_api_ninja

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.api.nutrition_api_ninja.Properties.api_key
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NutritionAppNinjaApiKtTest: TestCase() {
    @Test
    fun testGetFood() = runBlocking() {
        val api = nutritionApiNinjaApi().create(NutritionApiNinjaApi::class.java)
        val call: ApiNinjaNutrition = api.getFood(api_key,"carrot")
        MatcherAssert.assertThat(call[0].name, CoreMatchers.equalTo("carrot"))
    }

    @Test
    fun testGetFirstFoodId() = runBlocking() {
        val api = nutritionApiNinjaApi().create(NutritionApiNinjaApi::class.java)
        val call: ApiNinjaNutrition = api.getFood(api_key,"carrot")
        MatcherAssert.assertThat(call[0].calories, CoreMatchers.equalTo(34.0))
        MatcherAssert.assertThat(call[0].fat_total_g, CoreMatchers.equalTo(0.2))
        MatcherAssert.assertThat(call[0].protein_g, CoreMatchers.equalTo(0.8))
        MatcherAssert.assertThat(call[0].carbohydrates_total_g, CoreMatchers.equalTo(8.3))
    }
}
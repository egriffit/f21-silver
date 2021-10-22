package com.example.workout_companion.api.nutrition_api_ninja.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.api.edamam.EdamamAPI
import com.example.workout_companion.api.nutrition_api_ninja.Properties
import com.example.workout_companion.api.edamam.edamamApi
import com.example.workout_companion.api.edamam.entities.EdamamFood
import com.example.workout_companion.api.edamam.repository.EdamamRepository
import com.example.workout_companion.api.nutrition_api_ninja.NutritionApiNinjaApi
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
import com.example.workout_companion.api.nutrition_api_ninja.nutritionApiNinjaApi
import com.example.workout_companion.api.utility.Resource
import org.junit.Assert.*

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NutritionApiNinjaRepositoryTest : TestCase() {
    private lateinit var api: NutritionApiNinjaApi
    private lateinit var repository: NutritionApiNinjaRepository


    @Before
    public override fun setUp() {
        api = nutritionApiNinjaApi(Properties.base_url)
        repository = NutritionApiNinjaRepository(api)
    }

    @Test
    fun testGetFood() = runBlocking() {
        val call: Resource<ApiNinjaNutrition> = repository.getFood("carrot")
        MatcherAssert.assertThat(call.data?.get(0)?.name, CoreMatchers.equalTo("carrot"))
    }

    @Test
    fun testGetFirstFoodId() = runBlocking() {
        val call: Resource<ApiNinjaNutrition> = repository.getFood("carrot")
        MatcherAssert.assertThat(call.data?.get(0)?.name, CoreMatchers.equalTo("carrot"))
        MatcherAssert.assertThat(call.data?.get(0)?.calories, CoreMatchers.equalTo(34.0))
        MatcherAssert.assertThat(call.data?.get(0)?.fat_total_g, CoreMatchers.equalTo(0.2))
        MatcherAssert.assertThat(call.data?.get(0)?.protein_g, CoreMatchers.equalTo(0.8))
        MatcherAssert.assertThat(call.data?.get(0)?.carbohydrates_total_g, CoreMatchers.equalTo(8.3))
    }
}
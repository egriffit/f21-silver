package com.example.workout_companion.api.edamam

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.api.edamam.entities.EdamamFood
import com.example.workout_companion.api.edamam.entities.Food
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EdamamApiTest: TestCase() {
    @Test
    fun testGetFood() = runBlocking() {
        val edamamAPI = edamamApi(baseUrl = Properties.base_url)
        val call: EdamamFood = edamamAPI.getEdamamByFood(Properties.app_id, Properties.app_key,"carrot")
        MatcherAssert.assertThat(call.text, CoreMatchers.equalTo("carrot"))
    }

    @Test
    fun testGetFirstFoodId() = runBlocking() {
        val edamamAPI = edamamApi(baseUrl = Properties.base_url)
        val call: EdamamFood = edamamAPI.getEdamamByFood(Properties.app_id, Properties.app_key,"carrot")
        MatcherAssert.assertThat(call.hints.elementAt(0).food.foodId, CoreMatchers.equalTo("food_ai215e5b85pdh5ajd4aafa3w2zm8"))
        MatcherAssert.assertThat(call.hints.elementAt(0).food.nutrients.ENERC_KCAL.toInt(), CoreMatchers.equalTo(41))
        MatcherAssert.assertThat(call.hints.elementAt(0).food.nutrients.FAT, CoreMatchers.equalTo(0.24))
        MatcherAssert.assertThat(call.hints.elementAt(0).food.nutrients.PROCNT, CoreMatchers.equalTo(0.93))
        MatcherAssert.assertThat(call.hints.elementAt(0).food.nutrients.CHOCDF, CoreMatchers.equalTo(9.58))
    }
}
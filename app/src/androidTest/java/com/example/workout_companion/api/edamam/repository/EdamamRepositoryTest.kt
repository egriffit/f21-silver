package com.example.workout_companion.api.edamam.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.api.edamam.EdamamAPI
import com.example.workout_companion.api.edamam.Properties
import com.example.workout_companion.api.edamam.edamamApi
import com.example.workout_companion.api.edamam.entities.EdamamFood
import com.example.workout_companion.api.utility.Resource
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.repository.UserRepository
import com.example.workout_companion.utility.getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
class EdamamRepositoryTest : TestCase() {
    private lateinit var api: EdamamAPI
    private lateinit var repository: EdamamRepository


    @Before
    public override fun setUp() {
        api = edamamApi(Properties.base_url)
        repository = EdamamRepository(api)
    }

    @Test
    fun testGetFood() = runBlocking() {
        val result: Resource<EdamamFood> = repository.getFood("carrot")
        MatcherAssert.assertThat(result.data?.text, CoreMatchers.equalTo("carrot"))
    }

    @Test
    fun testGetFirstFoodId() = runBlocking() {
        val result: Resource<EdamamFood> = repository.getFood("carrot")
        MatcherAssert.assertThat(result.data?.parsed?.elementAt(0)?.food?.foodId, CoreMatchers.equalTo("food_ai215e5b85pdh5ajd4aafa3w2zm8"))
        MatcherAssert.assertThat(result.data?.parsed?.elementAt(0)?.food?.nutrients?.ENERC_KCAL?.toInt(), CoreMatchers.equalTo(41))
        MatcherAssert.assertThat(result.data?.parsed?.elementAt(0)?.food?.nutrients?.FAT, CoreMatchers.equalTo(0.24))
        MatcherAssert.assertThat(result.data?.parsed?.elementAt(0)?.food?.nutrients?.PROCNT, CoreMatchers.equalTo(0.93))
        MatcherAssert.assertThat(result.data?.parsed?.elementAt(0)?.food?.nutrients?.CHOCDF, CoreMatchers.equalTo(9.58))
    }

}
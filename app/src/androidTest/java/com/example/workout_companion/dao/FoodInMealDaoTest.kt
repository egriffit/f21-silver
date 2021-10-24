package com.example.workout_companion.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.utility.ActivityLevel
import com.example.workout_companion.utility.ExperienceLevel
import com.example.workout_companion.utility.Sex
import com.example.workout_companion.utility.getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.Month


@RunWith(AndroidJUnit4::class)
class FoodInMealDaoTest : TestCase(){

    private lateinit var db: WCDatabase
    private lateinit var dao: FoodInMealDao
    private lateinit var mealDao: MealDao
    private lateinit var foodao: FoodTypeDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.foodInMealDao()
        mealDao = db.mealDao()
        foodao = db.foodTypeDao()
    }

    @After
    fun closeDB(){
        db.close()
    }

    @Test
    fun TestGetDate() = runBlocking(){
        val today = LocalDate.now()

        val yesterday = LocalDate.of(2021, Month.OCTOBER, 23)
        val meals = listOf(
            MealEntity(1, "breakfast", 500.0, 40.1, 20.1, 15.0, today),
                    MealEntity(2, "breakfast", 500.0, 40.1, 20.1, 15.0, yesterday)
        )
        mealDao.insert(meals)

//        MatcherAssert.assertThat(foundMeal.elementAt(0), CoreMatchers.equalTo(meals.elementAt(0),))
    }

}
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
class MealDaoTest : TestCase(){

    private lateinit var db: WCDatabase
    private lateinit var dao: MealDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.mealDao()
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

        dao.insert(meals)
        val foundMeal: List<MealEntity> = dao.getByDate(today).getOrAwaitValue()
        MatcherAssert.assertThat(foundMeal.elementAt(0), CoreMatchers.equalTo(meals.elementAt(0),))
    }

    @Test
    fun TestGetByName() = runBlocking(){
        val today = LocalDate.now()

        val yesterday = LocalDate.of(2021, Month.OCTOBER, 23)
        val meals = listOf(
            MealEntity(1, "lunch", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(2, "breakfast", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(3, "breakfast", 500.0, 40.1, 20.1, 15.0, yesterday)
        )

        dao.insert(meals)
        val foundMeal: List<MealEntity> = dao.getByName("breakfast").getOrAwaitValue()
        MatcherAssert.assertThat(foundMeal.elementAt(0), CoreMatchers.equalTo(meals.elementAt(1),))
    }

    @Test
    fun TestGetCount() = runBlocking(){
        val today = LocalDate.now()

        val yesterday = LocalDate.of(2021, Month.OCTOBER, 23)
        val meals = listOf(
            MealEntity(1, "lunch", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(2, "breakfast", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(3, "breakfast", 500.0, 40.1, 20.1, 15.0, yesterday)
        )

        dao.insert(meals)
        val mealCount: Int = dao.getCount()
        MatcherAssert.assertThat(mealCount, CoreMatchers.equalTo(2))
    }

    @Test
    fun TestUpdate() = runBlocking(){
        val today = LocalDate.now()

        val yesterday = LocalDate.of(2021, Month.OCTOBER, 23)
        val meals = listOf(
            MealEntity(1, "lunch", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(2, "breakfast", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(3, "breakfast", 500.0, 40.1, 20.1, 15.0, yesterday)
        )
        val updateMeal = MealEntity(2, "breakfast", 700.0, 40.1, 20.1, 15.0, today)

        dao.insert(meals)
        dao.update(updateMeal)
        val foundMeal: List<MealEntity> = dao.getByName("breakfast").getOrAwaitValue()
        MatcherAssert.assertThat(foundMeal.elementAt(0).calories, CoreMatchers.equalTo(updateMeal.calories))
    }

    @Test
    fun TestDelete() = runBlocking(){
        val today = LocalDate.now()

        val yesterday = LocalDate.of(2021, Month.OCTOBER, 23)
        val meals = listOf(
            MealEntity(1, "lunch", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(2, "breakfast", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(3, "breakfast", 500.0, 40.1, 20.1, 15.0, yesterday)
        )
        val updateMeal = MealEntity(2, "breakfast", 700.0, 40.1, 20.1, 15.0, today)

        dao.insert(meals)
        dao.delete(meals.elementAt(1))
        val mealCount: Int = dao.getCount()
        MatcherAssert.assertThat(mealCount, CoreMatchers.equalTo(1))
    }

    @Test
    fun TestDeleteAll() = runBlocking(){
        val today = LocalDate.now()

        val yesterday = LocalDate.of(2021, Month.OCTOBER, 23)
        val meals = listOf(
            MealEntity(1, "lunch", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(2, "breakfast", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(3, "breakfast", 500.0, 40.1, 20.1, 15.0, yesterday)
        )
        val updateMeal = MealEntity(2, "breakfast", 700.0, 40.1, 20.1, 15.0, today)

        dao.insert(meals)
        dao.deleteAll()
        val mealCount: Int = dao.getCount()
        MatcherAssert.assertThat(mealCount, CoreMatchers.equalTo(0))
    }
}
package com.example.workout_companion.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.MealEntity
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
        val foundMeal: List<MealEntity> = dao.getByDate(today)
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
        val foundMeal: List<MealEntity> = dao.getByName("breakfast")
        MatcherAssert.assertThat(foundMeal.elementAt(0), CoreMatchers.equalTo(meals.elementAt(1),))
    }

    @Test
    fun TestGetCount() = runBlocking(){
        val today = LocalDate.now()

        val day = LocalDate.of(2021, Month.OCTOBER, 23)
        val meals = listOf(
            MealEntity(1, "lunch", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(2, "breakfast", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(3, "breakfast", 500.0, 40.1, 20.1, 15.0, day)
        )

        dao.insert(meals)
        val mealCount: Int = dao.getCount()
        MatcherAssert.assertThat(mealCount, CoreMatchers.equalTo(2))
    }

    fun TestGetMealId() = runBlocking(){
        val today = LocalDate.now()

        val yesterday = LocalDate.of(2021, Month.OCTOBER, 23)
        val meals = listOf(
            MealEntity(1, "lunch", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(2, "breakfast", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(3, "breakfast", 500.0, 40.1, 20.1, 15.0, yesterday)
        )

        dao.insert(meals)
        val firstMealId: Int = dao.getMealId("lunch")
        val secondMealID: Int = dao.getMealId("breakfast")
        val thirdMealID: Int = dao.getMealId("breakfast", yesterday)

        MatcherAssert.assertThat(firstMealId, CoreMatchers.equalTo(1))
        MatcherAssert.assertThat(secondMealID, CoreMatchers.equalTo(2))
        MatcherAssert.assertThat(thirdMealID, CoreMatchers.equalTo(3))
    }

    @Test
    fun TestCalMealTotal() = runBlocking(){

        val today = LocalDate.now()

        val yesterday = LocalDate.of(2021, Month.OCTOBER, 23)
        val meals = listOf(
            MealEntity(1, "lunch", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(2, "breakfast", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(3, "breakfast", 500.0, 40.1, 20.1, 15.0, yesterday)
        )

        dao.insert(meals)
        val totalForDay = dao.calcMealTotal()
        MatcherAssert.assertThat(totalForDay.calories, CoreMatchers.equalTo(1000.0))
        MatcherAssert.assertThat(totalForDay.carbohydrates, CoreMatchers.equalTo(80.2))
        MatcherAssert.assertThat(totalForDay.protein, CoreMatchers.equalTo(40.2))
        MatcherAssert.assertThat(totalForDay.fat, CoreMatchers.equalTo(30.0))
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
        val foundMeal: List<MealEntity> = dao.getByName("breakfast")
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
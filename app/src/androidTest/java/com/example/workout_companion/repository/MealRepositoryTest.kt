package com.example.workout_companion.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.FoodTypeDao
import com.example.workout_companion.dao.MealDao
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.sampleData.sampleFoodTypeList
import com.example.workout_companion.utility.getOrAwaitValue
import org.junit.Assert.*

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
class MealRepositoryTest : TestCase() {
    private lateinit var db: WCDatabase
    private lateinit var dao: MealDao
    private lateinit var repository: MealRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.mealDao()
        repository = MealRepository(dao)
    }

    @After
    public override fun tearDown() {
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

        repository.insert(meals)
        val foundMeal: List<MealEntity> = repository.getMealsByDate(today)
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

        repository.insert(meals)
        val foundMeal: List<MealEntity> = repository.getMealByName("breakfast")
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

        repository.insert(meals)
        val mealCount: Int = repository.getCount()
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

        repository.insert(meals)
        val firstMealId: Int = repository.getMealId("lunch")
        val secondMealID: Int = repository.getMealId("breakfast")
        val thirdMealID: Int = repository.getMealId("breakfast", yesterday)

        MatcherAssert.assertThat(firstMealId, CoreMatchers.equalTo(1))
        MatcherAssert.assertThat(secondMealID, CoreMatchers.equalTo(2))
        MatcherAssert.assertThat(thirdMealID, CoreMatchers.equalTo(3))
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

        repository.insert(meals)
        repository.update(updateMeal)
        val foundMeal: List<MealEntity> = repository.getMealByName("breakfast")
        MatcherAssert.assertThat(foundMeal.elementAt(0).calories, CoreMatchers.equalTo(updateMeal.calories))
    }

    @Test
    fun TestAddToMeal() = runBlocking(){
        val today = LocalDate.now()

        val meals = listOf(
            MealEntity(1, "breakfast", 0.0, 0.0, 0.0, 0.0, today),
        )
        val eggs = FoodTypeEntity(1, "eggs", "-1", 100.0, 144.3, 0.7, 12.6, 9.4)
        repository.insert(meals)
        repository.addToMeal(meals[0], eggs, 1.0)
        val foundMeal: List<MealEntity> = repository.getMealByName("breakfast")
        MatcherAssert.assertThat(foundMeal.elementAt(0).type, CoreMatchers.equalTo("breakfast"))
        MatcherAssert.assertThat(foundMeal.elementAt(0).calories, CoreMatchers.equalTo(eggs.calories))
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

        repository.insert(meals)
        repository.delete(meals.elementAt(1))
        val mealCount: Int = repository.getCount()
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

        repository.insert(meals)
        repository.deleteAll()
        val mealCount: Int = repository.getCount()
        MatcherAssert.assertThat(mealCount, CoreMatchers.equalTo(0))
    }
}
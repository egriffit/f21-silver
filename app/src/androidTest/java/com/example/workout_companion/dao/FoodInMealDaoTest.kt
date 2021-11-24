package com.example.workout_companion.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FoodInMealEntity
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.entity.MealWithFoodsEntity
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
    private lateinit var foodDao: FoodTypeDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() = runBlocking{
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.foodInMealDao()
        mealDao = db.mealDao()
        foodDao = db.foodTypeDao()

        val today = LocalDate.now()

        val yesterday = LocalDate.of(2021, Month.OCTOBER, 23)
        val meals = listOf(
            MealEntity(1, "breakfast", 500.0, 40.1, 20.1, 15.0, today),
            MealEntity(2, "breakfast", 500.0, 40.1, 20.1, 15.0, yesterday)
        )
        val breakFastFoods = listOf(
            FoodTypeEntity(1, "Bacoon", "-1", 100.0, 200.0, 2.0, 50.0, 48.0),
            FoodTypeEntity(2, "Eggs", "-1", 20.0, 100.0, 2.0, 15.0, 3.0) ,
            FoodTypeEntity(3, "Cereal", "-1", 100.0, 400.0, 86.0, 4.0, 10.0)
        )
        foodDao.insert(breakFastFoods)
        mealDao.insert(meals)

        val foodsInMeals = listOf(
            FoodInMealEntity(1, 3, 1.0),
            FoodInMealEntity(2, 1, 2.0),
            FoodInMealEntity(2, 2, 1.0),
        )
        dao.insert(foodsInMeals)

    }

    @After
    fun closeDB(){
        db.close()
    }

    @Test
    fun TestGetFoodInMeal() = runBlocking(){

        val found: List<MealWithFoodsEntity>? = dao.getFoods(2)
        val foundFoods: MutableList<FoodTypeEntity> = mutableListOf()
        if (found != null) {
            for(m in found ){
                foundFoods.add(m.foods.elementAt(0))
            }
        }
        val expected = listOf(
            FoodTypeEntity(1, "Bacoon", "-1", 100.0, 200.0, 2.0, 50.0, 48.0),
            FoodTypeEntity(2, "Eggs", "-1", 20.0, 100.0, 2.0, 15.0, 3.0) ,
        )
        MatcherAssert.assertThat(foundFoods.toList(), CoreMatchers.equalTo(expected))
        MatcherAssert.assertThat(found?.elementAt(1)?.meal?.id, CoreMatchers.equalTo(2))
    }

    @Test
    fun TestGetFoodInMealByName() = runBlocking(){
        val date = LocalDate.of(2021, Month.OCTOBER, 23)

        val found: List<MealWithFoodsEntity>? = dao.getFoodInMeal("breakfast", date)
        val foundFoods: MutableList<FoodTypeEntity> = mutableListOf()
        if (found != null) {
            for(m in found ){
                foundFoods.add(m.foods.elementAt(0))
            }
        }
        val expected = listOf(
            FoodTypeEntity(1, "Bacoon", "-1", 100.0, 200.0, 2.0, 50.0, 48.0),
            FoodTypeEntity(2, "Eggs", "-1", 20.0, 100.0, 2.0, 15.0, 3.0) ,
        )
        MatcherAssert.assertThat(foundFoods.toList(), CoreMatchers.equalTo(expected))
        MatcherAssert.assertThat(found?.elementAt(1)?.meal?.id, CoreMatchers.equalTo(2))
    }

    @Test
    fun TestGetFoodCountInMeal() = runBlocking(){
        val date = LocalDate.of(2021, Month.OCTOBER, 23)

        val found: List<MealWithFoodsEntity>? = dao.getFoodInMeal("breakfast", date)
        val foundFoods: MutableList<FoodTypeEntity> = mutableListOf()
        if (found != null) {
            for(m in found ){
                foundFoods.add(m.foods.elementAt(0))
            }
        }
        val expected = listOf(
            FoodTypeEntity(1, "Bacoon", "-1", 100.0, 200.0, 2.0, 50.0, 48.0),
            FoodTypeEntity(2, "Eggs", "-1", 20.0, 100.0, 2.0, 15.0, 3.0) ,
        )
        MatcherAssert.assertThat(foundFoods.toList(), CoreMatchers.equalTo(expected))
        MatcherAssert.assertThat(found?.elementAt(1)?.meal?.id, CoreMatchers.equalTo(2))
    }

    @Test
    fun TestGetFoodCountInMeal2() = runBlocking(){
        val date = LocalDate.of(2021, Month.OCTOBER, 23)

        val found: List<MealWithFoodsEntity>? = dao.getFoodInMeal(2)
        val foundFoods: MutableList<FoodTypeEntity> = mutableListOf()
        if (found != null) {
            for(m in found ){
                foundFoods.add(m.foods.elementAt(0))
            }
        }
        val expected = listOf(
            FoodTypeEntity(1, "Bacoon", "-1", 100.0, 200.0, 2.0, 50.0, 48.0),
            FoodTypeEntity(2, "Eggs", "-1", 20.0, 100.0, 2.0, 15.0, 3.0) ,
        )
        MatcherAssert.assertThat(foundFoods.toList(), CoreMatchers.equalTo(expected))
        MatcherAssert.assertThat(found?.elementAt(1)?.meal?.id, CoreMatchers.equalTo(2))
    }

    @Test
    fun TestUpdate() = runBlocking(){
        val date = LocalDate.of(2021, Month.OCTOBER, 23)

        val foodToUpdate = FoodInMealEntity(2, 2, 2.0)
        dao.update(foodToUpdate)
        val found: List<MealWithFoodsEntity>? = dao.getFoodInMeal(2)
        val foundFoods: MutableList<FoodTypeEntity> = mutableListOf()
        if (found != null) {
            for (m in found) {
                foundFoods.add(m.foods.elementAt(0))
            }
        }
        MatcherAssert.assertThat(found?.elementAt(1)?.food_in_meal?.servings, CoreMatchers.equalTo(2.0))
    }

    @Test
    fun TestDelete() = runBlocking(){
        val date = LocalDate.of(2021, Month.OCTOBER, 23)
        val foodToDelete = FoodInMealEntity(2, 1, 2.0)
        dao.delete(foodToDelete)
        val found: List<MealWithFoodsEntity>? = dao.getFoodInMeal(2)
        val foundFoods: MutableList<FoodTypeEntity> = mutableListOf()
        if (found != null) {
            for (m in found) {
                foundFoods.add(m.foods.elementAt(0))
            }
        }
        val expected = listOf(
            FoodTypeEntity(2, "Eggs", "-1", 20.0, 100.0, 2.0, 15.0, 3.0) ,
        )
        MatcherAssert.assertThat(foundFoods.toList(), CoreMatchers.equalTo(expected))
    }

    @Test
    fun TestDeleteMeal() = runBlocking(){
        val date = LocalDate.of(2021, Month.OCTOBER, 23)
        val foodToDelete = FoodInMealEntity(2, 1, 2.0)
        dao.delete(2)
        val found: List<MealWithFoodsEntity>? = dao.getFoodInMeal(2)
        val foundFoods: MutableList<FoodTypeEntity> = mutableListOf()
        if (found != null) {
            for (m in found) {
                foundFoods.add(m.foods.elementAt(0))
            }
        }
        val expected: List<FoodTypeEntity> = listOf()
        MatcherAssert.assertThat(foundFoods.toList(), CoreMatchers.equalTo(expected))
    }
}
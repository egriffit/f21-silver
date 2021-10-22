package com.example.workout_companion.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.*
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FoodTypeEntity
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

@RunWith(AndroidJUnit4::class)
class FoodTypeRepositoryTest : TestCase() {
    private lateinit var db: WCDatabase
    private lateinit var dao: FoodTypeDao
    private lateinit var repository: FoodTypeRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.foodTypeDao()
        repository = FoodTypeRepository(dao)
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun getAllFoods() = runBlocking{
        val foods = listOf(
            FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
            80.0, 100.0, 1.3, 98.0, 0.7),
            FoodTypeEntity(2, "lettuce", "-1",
                80.0, 100.0, 1.3, 98.0, 0.7)
        )
        repository.insert(foods)
        val foundFoods: List<FoodTypeEntity> = repository.getAllFoods.getOrAwaitValue()
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(foods))
    }


    @Test
    fun getFoodByName() = runBlocking{
        val food = listOf(FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
            80.0, 100.0, 1.3, 98.0, 0.7))
        repository.insert(food)
        val byName:List<FoodTypeEntity> = repository.getFoodByName("carrot").getOrAwaitValue()
        MatcherAssert.assertThat(byName, CoreMatchers.equalTo(food))
    }

    @Test
    fun getCount() = runBlocking{
        val foods = listOf(FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
            80.0, 100.0, 1.3, 98.0, 0.7),
            FoodTypeEntity(2, "lettuce", "-1",
                80.0, 100.0, 1.3, 98.0, 0.7))
        repository.insert(foods)
        val foundFoods: Int = repository.getCount()
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(2))
    }

    @Test
    fun testGetCount() = runBlocking{
        val foods = listOf(FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
            80.0, 100.0, 1.3, 98.0, 0.7),
            FoodTypeEntity(2, "lettuce", "-1",
                80.0, 100.0, 1.3, 98.0, 0.7))
        repository.insert(foods)
        val foundFoods: Int = repository.getCount("carrot")
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(1))
    }

    @Test
    fun update() = runBlocking{
        val foods = FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
            80.0, 100.0, 1.3, 98.0, 0.7)
        val newFood = FoodTypeEntity(1, "carrot", "-1",
            80.0, 100.0, 1.3, 98.0, 0.7)

        repository.insert(foods)
        repository.update(newFood)
        val found :List<FoodTypeEntity> = repository.getFoodByName("carrot").getOrAwaitValue()
        MatcherAssert.assertThat(found.elementAt(0).edamam_id, CoreMatchers.equalTo(newFood.edamam_id))
    }

    @Test
    fun delete() = runBlocking{
        val foods = listOf(FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
            80.0, 100.0, 1.3, 98.0, 0.7),
            FoodTypeEntity(2, "lettuce", "-1",
                80.0, 100.0, 1.3, 98.0, 0.7))

        repository.insert(foods)
        repository.delete(foods[0])
        val foundFoods: Int = repository.getCount("carrot")
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(0))
    }

    @Test
    fun deleteAll() = runBlocking{
        val foods = listOf(FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
            80.0, 100.0, 1.3, 98.0, 0.7),
            FoodTypeEntity(2, "lettuce", "-1",
                80.0, 100.0, 1.3, 98.0, 0.7))

        repository.insert(foods)
        repository.deleteAll()
        val foundFoods: Int = repository.getCount()
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(0))
    }
}
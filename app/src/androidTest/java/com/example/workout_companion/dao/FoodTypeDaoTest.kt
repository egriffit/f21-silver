package com.example.workout_companion.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FoodTypeEntity
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
class FoodTypeDaoTest : TestCase(){

    private lateinit var db: WCDatabase
    private lateinit var dao: FoodTypeDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.foodTypeDao()
    }

    @After
    fun closeDB(){
        db.close()
    }

    @Test
    fun TestGetAll() = runBlocking(){
        val foods = listOf(FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
            80.0, 100.0, 1.3, 98.0, 0.7),
       FoodTypeEntity(2, "lettuce", "-1",
            80.0, 100.0, 1.3, 98.0, 0.7))
        dao.insert(foods)
        val foundFoods: List<FoodTypeEntity> = dao.getAll().getOrAwaitValue()
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(foods))
    }

    @Test
    fun TestGetByName() = runBlocking(){
        val food = listOf(FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
            80.0, 100.0, 1.3, 98.0, 0.7))
        dao.insert(food)
        val byName:List<FoodTypeEntity> = dao.getByName("carrot").getOrAwaitValue()
        MatcherAssert.assertThat(byName, CoreMatchers.equalTo(food))
    }

    @Test
    fun TestGetCount() = runBlocking(){
        val foods = listOf(FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
            80.0, 100.0, 1.3, 98.0, 0.7),
            FoodTypeEntity(2, "lettuce", "-1",
                80.0, 100.0, 1.3, 98.0, 0.7))
        dao.insert(foods)
        val foundFoods: Int = dao.getCount()
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(2))
    }

    @Test
    fun TestGetCountWithName() = runBlocking(){
        val foods = listOf(FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
            80.0, 100.0, 1.3, 98.0, 0.7),
            FoodTypeEntity(2, "lettuce", "-1",
                80.0, 100.0, 1.3, 98.0, 0.7))
        dao.insert(foods)
        val foundFoods: Int = dao.getCountWithName("carrot")
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(1))
    }

    @Test
    fun TestUpdate() = runBlocking(){
        val foods = FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
            80.0, 100.0, 1.3, 98.0, 0.7)
            val newFood = FoodTypeEntity(1, "carrot", "-1",
            80.0, 100.0, 1.3, 98.0, 0.7)

        dao.insert(foods)
       dao.update(newFood)
        val found :List<FoodTypeEntity> = dao.getByName("carrot").getOrAwaitValue()
        MatcherAssert.assertThat(found.elementAt(0).edamam_id, CoreMatchers.equalTo(newFood.edamam_id))
    }

    @Test
    fun TestDelete() = runBlocking(){
        val foods = listOf(FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
            80.0, 100.0, 1.3, 98.0, 0.7),
            FoodTypeEntity(2, "lettuce", "-1",
                80.0, 100.0, 1.3, 98.0, 0.7))

        dao.insert(foods)
        dao.delete(foods[0])
        val foundFoods: Int = dao.getCountWithName("carrot")
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(0))
    }

    fun TestDeleteAll() = runBlocking(){
        val foods = listOf(FoodTypeEntity(1, "carrot", "food_ai215e5b85pdh5ajd4aafa3w2zm8",
            80.0, 100.0, 1.3, 98.0, 0.7),
            FoodTypeEntity(2, "lettuce", "-1",
                80.0, 100.0, 1.3, 98.0, 0.7))

        dao.insert(foods)
        dao.deleteAll()
        val foundFoods: Int = dao.getCount()
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(0))
    }
}
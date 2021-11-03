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
import com.example.workout_companion.sampleData.sampleFoodTypeList
import com.example.workout_companion.sampleData.sampleFoodTypeOneFoodList
import com.example.workout_companion.sampleData.sampleFoodUpdateExample
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
    fun testGetAll() = runBlocking(){
        dao.insert(sampleFoodTypeList)
        val foundFoods: List<FoodTypeEntity> = dao.getAll().getOrAwaitValue()
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(sampleFoodTypeList))
    }

    @Test
    fun testGetByName() = runBlocking(){
        val food = sampleFoodTypeOneFoodList
        dao.insert(sampleFoodTypeList)
        val byName: List<FoodTypeEntity> = dao.getByName("carrot").getOrAwaitValue()
        MatcherAssert.assertThat(byName, CoreMatchers.equalTo(food))
    }

    @Test
    fun testGetId() = runBlocking(){
        val food = sampleFoodTypeList
        dao.insert(food)
        val foundId: Int = dao.getId(sampleFoodTypeList.elementAt(0).name,
            sampleFoodTypeList.elementAt(0).calories, sampleFoodTypeList.elementAt(0).serving_size,
            sampleFoodTypeList.elementAt(0).carbohydrates, sampleFoodTypeList.elementAt(0).protein,
            sampleFoodTypeList.elementAt(0).fat)

        val foundId2: Int = dao.getId(sampleFoodTypeList.elementAt(1).name,
           sampleFoodTypeList.elementAt(1).calories, sampleFoodTypeList.elementAt(1).serving_size,
            sampleFoodTypeList.elementAt(1).carbohydrates, sampleFoodTypeList.elementAt(1).protein,
           sampleFoodTypeList.elementAt(1).fat)

        val foundFoods: List<FoodTypeEntity> = dao.getAll().getOrAwaitValue()
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(sampleFoodTypeList))
        MatcherAssert.assertThat(foundId, CoreMatchers.equalTo(1))
        MatcherAssert.assertThat(foundId2, CoreMatchers.equalTo(2))
    }

    @Test
    fun testGetCount() = runBlocking(){
        dao.insert(sampleFoodTypeList)
        val foundFoods: Int = dao.getCount()
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(2))
    }

    @Test
    fun testGetCountWithName() = runBlocking(){
        dao.insert(sampleFoodTypeList)
        val foundFoods: Int = dao.getCountWithName("carrot")
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(1))
    }

    @Test
    fun testGetCoun2() = runBlocking(){
        dao.insert(sampleFoodTypeList)

        val foundFoods: Int = dao.getCount(sampleFoodTypeList.elementAt(0).name,
            sampleFoodTypeList.elementAt(0).calories, sampleFoodTypeList.elementAt(0).serving_size,
            sampleFoodTypeList.elementAt(0).carbohydrates, sampleFoodTypeList.elementAt(0).protein,
            sampleFoodTypeList.elementAt(0).fat)
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(1))
    }

    @Test
    fun testUpdate() = runBlocking(){
        val newFood = sampleFoodUpdateExample
        dao.insert(sampleFoodTypeList[0])
        dao.update(newFood)
        val found :List<FoodTypeEntity> = dao.getByName("carrot").getOrAwaitValue()
        MatcherAssert.assertThat(found.elementAt(0).edamam_id, CoreMatchers.equalTo(newFood.edamam_id))
    }

    @Test
    fun testDelete() = runBlocking(){
        val foods = sampleFoodTypeList

        dao.insert(foods)
        dao.delete(foods[0])
        val foundFoods: Int = dao.getCountWithName("carrot")
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(0))
    }

    fun testDeleteAll() = runBlocking(){
        val foods = sampleFoodTypeList
        dao.insert(foods)
        dao.deleteAll()
        val foundFoods: Int = dao.getCount()
        MatcherAssert.assertThat(foundFoods, CoreMatchers.equalTo(0))
    }
}
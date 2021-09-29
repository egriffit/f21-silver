package com.example.workout_companion.entity

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.NutritionPlanTypeDao
import com.example.workout_companion.database.WCDatabase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NutritionPlanTypeEntityTest: TestCase(){
    private lateinit var db: WCDatabase
    private lateinit var dao: NutritionPlanTypeDao

    @Before
    public override fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.nutritionPlanTypeDao()
    }

    @After
    fun closeDB(){
        db.close()
    }

    @Test
    fun TestWriteAndReadNutritionPlanType() = runBlocking(){
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        dao.insert(plan)
        val byId = dao.getById(1)
        MatcherAssert.assertThat(byId, CoreMatchers.equalTo(plan))
    }

    @Test
    fun TestCount() = runBlocking(){
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        val plan2 = NutritionPlanTypeEntity(2, 2, 2500.0, 0.45, .30, .25)

        dao.insert(plan)
        dao.insert(plan2)
        val count: Int = dao.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(2))
    }

    @Test
    fun TestDelete() = runBlocking(){
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)

        dao.insert(plan)
        dao.delete(plan)
        val count: Int = dao.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(0))
    }

    @Test
    fun TestDeleteAll() = runBlocking(){
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        val plan2 = NutritionPlanTypeEntity(2, 2, 2500.0, 0.45, .30, .25)

        dao.insert(plan)
        dao.insert(plan2)
        dao.deleteAll()
        val count: Int = dao.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(0))
    }

    @Test
    fun TestUpdateNutritionPlanTyp2() = runBlocking(){
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        val newPlan = NutritionPlanTypeEntity(1, 1, 2200.0, 0.40, .35, .25)

        dao.insert(plan)
        dao.update(newPlan)
        val byName = dao.getById(1)
        MatcherAssert.assertThat(byName.calorie, CoreMatchers.equalTo(2200.0))
    }
}
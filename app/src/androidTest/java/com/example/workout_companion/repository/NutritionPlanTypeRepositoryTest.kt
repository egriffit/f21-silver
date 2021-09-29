package com.example.workout_companion.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.NutritionPlanTypeDao
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.NutritionPlanTypeEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NutritionPlanTypeRepositoryTest: TestCase(){
    private lateinit var db: WCDatabase
    private lateinit var dao: NutritionPlanTypeDao
    private lateinit var repository: NutritionPlanTypeRepository

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.nutritionPlanTypeDao()
        repository = NutritionPlanTypeRepository(dao)
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun testWriteAndReadPlan() = runBlocking{
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        repository.addPlan(plan)
        val byId = repository.getById(1)
        MatcherAssert.assertThat(byId, CoreMatchers.equalTo(plan))
    }

    @Test
    fun testGetCount() = runBlocking{
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        val plan2 = NutritionPlanTypeEntity(2, 2, 2500.0, 0.45, .30, .25)
        repository.addPlan(plan)
        repository.addPlan(plan2)
        val count: Int = repository.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(2))
    }

    @Test
    fun testUpdateUser() = runBlocking{
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        val newPlan = NutritionPlanTypeEntity(1, 1, 2200.0, 0.40, .35, .25)

        repository.addPlan(plan)
        repository.updatePlan(newPlan)
        val byName = dao.getById(1)
        MatcherAssert.assertThat(byName.calorie, CoreMatchers.equalTo(2200.0))
    }

    @Test
    fun testDelete() = runBlocking{
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        repository.addPlan(plan)
        repository.deletePlan(plan)
        val count: Int = repository.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(0))
    }

    @Test
    fun testDeleteAll() = runBlocking{
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        val plan2 = NutritionPlanTypeEntity(2, 2, 2500.0, 0.45, .30, .25)
        repository.addPlan(plan)
        repository.addPlan(plan2)
        repository.deletaAll()
        val count: Int = repository.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(0))
    }
}
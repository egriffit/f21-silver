package com.example.workout_companion.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.GoalTypeDao
import com.example.workout_companion.dao.NutritionPlanTypeDao
import com.example.workout_companion.dao.NutritionStatusDao
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.NutritionPlanTypeEntity
import com.example.workout_companion.entity.NutritionStatusEntity
import com.example.workout_companion.enumeration.NutritionStatusEnum
import com.example.workout_companion.utility.getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class NutritionStatusDaoTest: TestCase() {
    private lateinit var db: WCDatabase
    private lateinit var dao: NutritionStatusDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.nutritionStatusDao()
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun testWriteAndReadStatus() = runBlocking {
        val nutritionStatusEntity =
            NutritionStatusEntity(0, NutritionStatusEnum.ON_TRACK, LocalDate.now())
        dao.insert(nutritionStatusEntity)
        val byDate = dao.getTodaysStatus()
        MatcherAssert.assertThat(byDate.status, CoreMatchers.equalTo(nutritionStatusEntity.status))
    }

    @Test
    fun testCount() = runBlocking {
        val nutritionStatusEntity =
            NutritionStatusEntity(0, NutritionStatusEnum.ON_TRACK, LocalDate.now())
        dao.insert(nutritionStatusEntity)
        val count = dao.getCount(LocalDate.now())
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(1))
    }
}
package com.example.workout_companion.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.NutritionStatusDao
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.NutritionStatusEntity
import com.example.workout_companion.enumeration.NutritionStatusEnum
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
class NutritionStatusRepositoryTest: TestCase() {
    private lateinit var db: WCDatabase
    private lateinit var dao: NutritionStatusDao
    private lateinit var repository: NutritionStatusRepository

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.nutritionStatusDao()
        repository = NutritionStatusRepository(dao)
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun testWriteAndReadStatus() = runBlocking {
        val nutritionStatusEntity =
            NutritionStatusEntity(0, NutritionStatusEnum.ON_TRACK, LocalDate.now())
        repository.insert(nutritionStatusEntity)
        val byDate = repository.getTodaysNutritionStatus()
        MatcherAssert.assertThat(byDate.status, CoreMatchers.equalTo(nutritionStatusEntity.status))
    }
}
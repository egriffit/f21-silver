package com.example.workout_companion.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.GoalTypeEntity
import com.example.workout_companion.utility.TestDataGenerator
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FrameworkDayDaoTest : TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: WCDatabase
    private lateinit var goalTypeDao: GoalTypeDao
    private lateinit var frameworkTypeDao: FrameworkTypeDao
    private lateinit var frameworkDayDao: FrameworkDayDao

    @Before
    public override fun setUp() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        goalTypeDao = db.goalTypeDao()
        frameworkTypeDao = db.frameworkTypeDao()
        frameworkDayDao = db.frameworkDayDao()

        // Setup the database prior to all tests
        TestDataGenerator.addGoalsToDB(db)
        TestDataGenerator.addFrameworksToDB(db)
    }

    @After
    fun close() {
        db.close()
    }
}
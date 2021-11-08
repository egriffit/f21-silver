package com.example.workout_companion.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.utility.TestDataGenerator
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FrameworkComponentSetDaoTest : TestCase() {

    private lateinit var db: WCDatabase

    private lateinit var dao: FrameworkComponentSetDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = WCDatabase.getInstance(context)
        dao = db.frameworkComponentSetDao()

        // We need goals, framework types, framework days, framework components, and workouts
        TestDataGenerator.addGoalsToDB(db)
        TestDataGenerator.addFrameworksToDB(db)
        TestDataGenerator.addFrameworkDaysToDB(db)
        TestDataGenerator.addFrameworkComponentsToDB(db)
        TestDataGenerator.addWorkoutsToDB(db)
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun createFrameworkComponentSetTest() {

    }

}
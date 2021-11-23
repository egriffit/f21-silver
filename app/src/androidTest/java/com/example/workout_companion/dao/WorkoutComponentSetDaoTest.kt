package com.example.workout_companion.dao

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.room.util.DBUtil
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.enumeration.Progress
import com.example.workout_companion.utility.TestDataGenerator
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkoutComponentSetDaoTest : TestCase() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: WCDatabase
    private lateinit var dao: WorkoutComponentSetDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.workoutComponentSetDao()

        TestDataGenerator.addGoalsToDB(db)
        TestDataGenerator.addFrameworksToDB(db)
        TestDataGenerator.addFrameworkDaysToDB(db)
        TestDataGenerator.addFrameworkComponentsToDB(db)
        TestDataGenerator.addWorkoutsToDB(db)
        TestDataGenerator.addWorkoutComponentsToDB(db)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun addSetTest() = runBlocking {
        dao.addSet(TestDataGenerator.WORKOUT_0_COMPONENT_0_SETS[0])

        val setsInDb = dao.getAllSets()
        assertEquals(listOf(TestDataGenerator.WORKOUT_0_COMPONENT_0_SETS[0]), setsInDb)
    }

    @Test
    fun updateSetTest() = runBlocking {
        val set = TestDataGenerator.WORKOUT_0_COMPONENT_0_SETS[0]
        dao.addSet(set)

        set.reps = 14
        set.status = Progress.COMPLETE
        set.weight = 85.0
        set.wger_id = 10
        dao.updateSet(set)

        assertEquals(listOf(set), dao.getAllSets())
    }

    @Test
    fun deleteSetTest() = runBlocking {
        dao.addSet(TestDataGenerator.WORKOUT_0_COMPONENT_0_SETS[1])
        dao.removeSet(TestDataGenerator.WORKOUT_0_COMPONENT_0_SETS[1])

        assertTrue(dao.getAllSets().isEmpty())
    }
}
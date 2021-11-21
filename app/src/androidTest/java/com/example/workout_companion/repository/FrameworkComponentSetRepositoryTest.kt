package com.example.workout_companion.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.utility.TestDataGenerator
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FrameworkComponentSetRepositoryTest : TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: WCDatabase

    private lateinit var repository: FrameworkComponentSetRepository

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        repository = FrameworkComponentSetRepository(db.frameworkComponentSetDao())

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
    fun createSetTest() = runBlocking {
        repository.addFrameworkComponentSet(TestDataGenerator.WORKOUT_0_COMPONENT_SETS[0])

        val setsInDb = repository.getFrameworkComponentSetsForDate(TestDataGenerator.WORKOUT_0_COMPONENT_SETS[0].workout_date)
        assertEquals(listOf(TestDataGenerator.WORKOUT_0_COMPONENT_SETS[0]), setsInDb)
    }

    @Test
    fun updateSetTest() = runBlocking {
        repository.addFrameworkComponentSet(TestDataGenerator.WORKOUT_0_COMPONENT_SETS[1])

        val updatedSet = TestDataGenerator.WORKOUT_0_COMPONENT_SETS[1]
        updatedSet.component_id = TestDataGenerator.FRAMEWORK_2_DAY_0_COMPONENTS[0].id

        repository.updateFrameworkComponentSet(updatedSet)

        val setsInDb = repository.getFrameworkComponentSetsForDate(updatedSet.workout_date)
        assertEquals(listOf(updatedSet), setsInDb)
    }

    @Test
    fun deleteSetTest() = runBlocking {
        TestDataGenerator.addFrameworkComponentSetsToDB(db)
        repository.deleteFrameworkComponentSet(TestDataGenerator.WORKOUT_0_COMPONENT_SETS[0])

        val setsInDbAfterDelete = repository.getFrameworkComponentSetsForDate(TestDataGenerator.WORKOUTS[0].date)
        assertFalse(setsInDbAfterDelete.contains(TestDataGenerator.WORKOUT_0_COMPONENT_SETS[0]))
        assertTrue(setsInDbAfterDelete.contains(TestDataGenerator.WORKOUT_0_COMPONENT_SETS[1]))
        assertTrue(setsInDbAfterDelete.contains(TestDataGenerator.WORKOUT_0_COMPONENT_SETS[2]))
    }
}
package com.example.workout_companion.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.enumeration.Progress
import com.example.workout_companion.utility.TestDataGenerator
import com.example.workout_companion.utility.getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkoutRepositoryTest : TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: WCDatabase
    private lateinit var repository: WorkoutRepository

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        val dao = db.workoutDao()
        repository = WorkoutRepository(dao)

        // The workouts need framework days and components
        TestDataGenerator.addGoalsToDB(db)
        TestDataGenerator.addFrameworksToDB(db)
        TestDataGenerator.addFrameworkDaysToDB(db)
        TestDataGenerator.addFrameworkComponentsToDB(db)
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun createWorkoutTest() = runBlocking {
        repository.addWorkout(TestDataGenerator.WORKOUTS[0])

        val workoutsInDB = repository.workouts.getOrAwaitValue()
        assertEquals(workoutsInDB.size, 1)
        assertEquals(TestDataGenerator.WORKOUTS[0], workoutsInDB[0])
    }

    @Test
    fun updateWorkoutTest() = runBlocking {
        repository.addWorkout(TestDataGenerator.WORKOUTS[1])

        val updatedWorkout = TestDataGenerator.WORKOUTS[1]
        updatedWorkout.status = Progress.COMPLETE
        repository.updateWorkout(updatedWorkout)

        val workoutsInDB = repository.workouts.getOrAwaitValue()
        assertEquals(workoutsInDB.size, 1)
        assertEquals(updatedWorkout.status, workoutsInDB[0].status)
    }

    @Test
    fun getWorkoutOnDate() = runBlocking {
        TestDataGenerator.WORKOUTS.forEach {
            repository.addWorkout(it)
        }

        val workout = repository.getWorkoutOnDate(TestDataGenerator.WORKOUTS[0].date).getOrAwaitValue()
        assertEquals(TestDataGenerator.WORKOUTS[0], workout)
    }

    @Test
    fun deleteWorkoutTest() = runBlocking {
        TestDataGenerator.WORKOUTS.forEach {
            repository.addWorkout(it)
        }

        repository.deleteWorkout(TestDataGenerator.WORKOUTS[2])

        val workoutsInDb = repository.workouts.getOrAwaitValue()
        assertFalse(workoutsInDb.contains(TestDataGenerator.WORKOUTS[2]))
        assertTrue(workoutsInDb.contains(TestDataGenerator.WORKOUTS[0]))
        assertTrue(workoutsInDb.contains(TestDataGenerator.WORKOUTS[1]))
        assertTrue(workoutsInDb.contains(TestDataGenerator.WORKOUTS[3]))
    }

    @Test
    fun getWorkoutWithComponentsTest() = runBlocking {
        TestDataGenerator.addWorkoutsToDB(db)
        TestDataGenerator.addWorkoutComponentsToDB(db)

        val workout0WithSets = repository.getWorkoutWithComponents(TestDataGenerator.WORKOUTS[0].date).getOrAwaitValue()
        assertEquals(TestDataGenerator.WORKOUTS[0], workout0WithSets.workout)
        assertEquals(TestDataGenerator.WORKOUT_0_COMPONENTS, workout0WithSets.components)
    }
}
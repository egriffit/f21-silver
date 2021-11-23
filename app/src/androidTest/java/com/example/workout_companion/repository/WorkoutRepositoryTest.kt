package com.example.workout_companion.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.FrameworkDayDao
import com.example.workout_companion.dao.FrameworkDayWithComponents
import com.example.workout_companion.dao.WorkoutWithComponents
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
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class WorkoutRepositoryTest : TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: WCDatabase
    private lateinit var repository: WorkoutRepository
    private lateinit var frameworkDayDao: FrameworkDayDao
    private lateinit var framework0DaysWithComponents: LiveData<List<FrameworkDayWithComponents>>

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        repository = WorkoutRepository(db.workoutDao(), db.workoutComponentDao(), db.workoutComponentSetDao())
        frameworkDayDao = db.frameworkDayDao()

        // The workouts need framework days and components
        TestDataGenerator.addGoalsToDB(db)
        TestDataGenerator.addFrameworksToDB(db)
        TestDataGenerator.addFrameworkDaysToDB(db)
        TestDataGenerator.addFrameworkComponentsToDB(db)

        framework0DaysWithComponents = frameworkDayDao.getFrameworkDaysWithComponents(TestDataGenerator.GOAL_0_FRAMEWORKS[0].id)
    }

    @After
    fun closeDB() {
        db.close()
    }

    fun validateWorkoutMatchesFramework(workoutWithComponents: WorkoutWithComponents, frameworkDayWithComponents: FrameworkDayWithComponents) {
        assertEquals(frameworkDayWithComponents.day.id, workoutWithComponents.workout.framework_day_id)

        assertEquals(workoutWithComponents.components.size, frameworkDayWithComponents.components.size)
        for (i in workoutWithComponents.components.indices) {
            val workoutComponent = workoutWithComponents.components[i]
            val frameworkComponent = frameworkDayWithComponents.components[i]

            assertEquals(frameworkComponent.id, workoutComponent.component.component_id)
            assertEquals(frameworkComponent.target_sets, workoutComponent.sets.size)

            var totalReps = 0
            for (set in workoutComponent.sets) {
                totalReps += set.reps
            }
            assertEquals(frameworkComponent.target_reps, totalReps)
        }
    }

    @Test
    fun createWorkoutTest() = runBlocking {
        val frameworkDay0 = framework0DaysWithComponents.getOrAwaitValue()[0]
        repository.createWorkout(frameworkDay0)

        val workoutWithComponents = repository.getWorkoutWithComponents(LocalDate.now()).getOrAwaitValue()
        validateWorkoutMatchesFramework(workoutWithComponents, frameworkDay0)
    }

    /*@Test
    fun updateWorkoutTest() = runBlocking {
        repository.createWorkout(TestDataGenerator.WORKOUTS[1])

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
            repository.createWorkout(it)
        }

        val workout = repository.getWorkoutOnDate(TestDataGenerator.WORKOUTS[0].date).getOrAwaitValue()
        assertEquals(TestDataGenerator.WORKOUTS[0], workout)
    }

    @Test
    fun deleteWorkoutTest() = runBlocking {
        TestDataGenerator.WORKOUTS.forEach {
            repository.createWorkout(it)
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
        TestDataGenerator.addWorkoutComponentSetsToDB(db)

        val workout0WithComponents = repository.getWorkoutWithComponents(TestDataGenerator.WORKOUTS[0].date).getOrAwaitValue()
        assertEquals(TestDataGenerator.WORKOUTS[0], workout0WithComponents.workout)
        for (componentWithSets in workout0WithComponents.components) {
            assertTrue(TestDataGenerator.WORKOUT_0_COMPONENTS.contains(componentWithSets.component))
        }
        assertEquals(workout0WithComponents.components[0].sets, TestDataGenerator.WORKOUT_0_COMPONENT_0_SETS)
    }*/
}
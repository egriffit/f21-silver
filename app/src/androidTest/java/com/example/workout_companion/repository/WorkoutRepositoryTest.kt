package com.example.workout_companion.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.*
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
    private lateinit var completeDao: CompleteFrameworkDao
    private lateinit var framework0WithDays: LiveData<FrameworkWithDays>

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        repository = WorkoutRepository(db.workoutDao(), db.workoutComponentDao(), db.workoutComponentSetDao())
        completeDao = db.completeFrameworkDao()

        // The workouts need framework days and components
        TestDataGenerator.addGoalsToDB(db)
        TestDataGenerator.addFrameworksToDB(db)
        TestDataGenerator.addFrameworkDaysToDB(db)
        TestDataGenerator.addFrameworkComponentsToDB(db)

        framework0WithDays = completeDao.getFrameworkWithDaysById(TestDataGenerator.GOAL_0_FRAMEWORKS[0].id)
    }

    @After
    fun closeDB() {
        db.close()
    }

    private fun validateWorkoutMatchesFramework(workoutWithComponents: WorkoutWithComponents, frameworkDayWithComponents: FrameworkDayWithComponents) {
        assertEquals(frameworkDayWithComponents.day.id, workoutWithComponents.workout.framework_day_id)

        assertEquals(workoutWithComponents.components.size, frameworkDayWithComponents.components.size)
        for (i in workoutWithComponents.components.indices) {
            val workoutComponent = workoutWithComponents.components[i]
            val frameworkComponent = frameworkDayWithComponents.components[i]

            assertEquals(frameworkComponent.id, workoutComponent.component.component_id)
            assertEquals(frameworkComponent.target_sets, workoutComponent.sets.size)
            assertEquals(frameworkComponent.muscle_group, workoutComponent.muscleGroup)

            var totalReps = 0
            for (set in workoutComponent.sets) {
                totalReps += set.reps
            }
            assertEquals(frameworkComponent.target_reps, totalReps)
        }
    }

    private fun validateWorkoutIsRemoved(workoutWithComponents: WorkoutWithComponents) = runBlocking {
        val workoutsInDb = repository.workouts.getOrAwaitValue()
        assertFalse(workoutsInDb.contains(workoutWithComponents.workout))

        val componentsInDb = db.workoutComponentDao().getWorkoutComponentsForDate(LocalDate.now())
        val setsInDb = db.workoutComponentSetDao().getAllSets()
        for (component in workoutWithComponents.components) {
            assertFalse(componentsInDb.contains(component.component))

            for (set in component.sets) {
                assertFalse(setsInDb.containsAll(component.sets))
            }
        }
    }

    @Test
    fun createWorkoutTest() = runBlocking {
        val frameworkDay0 = framework0WithDays.getOrAwaitValue().days[0]
        repository.createWorkout(frameworkDay0)

        val workoutWithComponents = repository.getWorkoutWithComponents(LocalDate.now()).getOrAwaitValue()
        validateWorkoutMatchesFramework(workoutWithComponents, frameworkDay0)
    }

    @Test
    fun updateWorkoutTest() = runBlocking {
        val frameworkDay1 = framework0WithDays.getOrAwaitValue().days[1]
        repository.createWorkout(frameworkDay1)

        val updatedWorkout = repository.getWorkoutOnDate(LocalDate.now()).getOrAwaitValue()
        updatedWorkout.status = Progress.COMPLETE
        repository.updateWorkout(updatedWorkout)

        val workoutsInDB = repository.workouts.getOrAwaitValue()
        assertEquals(workoutsInDB.size, 1)
        assertEquals(updatedWorkout.status, workoutsInDB[0].status)
    }

    @Test
    fun completeSetTest() = runBlocking {
        val frameworkDay1 = framework0WithDays.getOrAwaitValue().days[1]
        repository.createWorkout(frameworkDay1)

        val workout = repository.getWorkoutWithComponents(LocalDate.now()).getOrAwaitValue()

        repository.completeWorkoutSet(workout.components[0].sets[0])

        assertEquals(Progress.COMPLETE, workout.components[0].sets[0].status)
    }

    @Test
    fun deleteWorkoutTest() = runBlocking {
        val frameworkDay1 = framework0WithDays.getOrAwaitValue().days[1]
        repository.createWorkout(frameworkDay1)

        val workoutWithComponents = repository.getWorkoutWithComponents(LocalDate.now()).getOrAwaitValue()
        repository.deleteWorkout(workoutWithComponents.workout)

        validateWorkoutIsRemoved(workoutWithComponents)
    }
}
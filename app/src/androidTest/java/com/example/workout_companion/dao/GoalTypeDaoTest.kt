package com.example.workout_companion.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.GoalTypeEntity
import com.example.workout_companion.utility.getGoalTestValues
import com.example.workout_companion.utility.getOrAwaitValue
import com.example.workout_companion.utility.getSingleGoalTestValue
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GoalTypeDaoTest : TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: WCDatabase
    private lateinit var dao: GoalTypeDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.goalTypeDao()
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun writeAndReadGoalTest() = runBlocking() {
        val goal = getSingleGoalTestValue(0)
        dao.addGoal(goal)

        assertTrue(dao.getAllGoals().getOrAwaitValue().contains(goal))
    }

    @Test
    fun addTwoGoalsTest() = runBlocking() {
        val goals = getGoalTestValues(2)
        for (goal in goals) {
            dao.addGoal(goal)
        }

        val loadedGoals = dao.getAllGoals().getOrAwaitValue()
        assertEquals(loadedGoals.size, 2)
        for (goal in goals) {
            assertTrue(loadedGoals.contains(goal))
        }
    }

    @Test
    fun addConflictingGoalsTest() = runBlocking() {
        val mainGoal = GoalTypeEntity(0, "Main name")
        dao.addGoal(mainGoal)
        val conflictingGoal = GoalTypeEntity(0, "Conflicting name")
        dao.addGoal(conflictingGoal)

        val allGoals = dao.getAllGoals().getOrAwaitValue()
        assertEquals(allGoals.size, 1)
        assertTrue(allGoals.contains(mainGoal))
        assertFalse(allGoals.contains(conflictingGoal))
    }

    @Test
    fun getGoalByIDTest() = runBlocking() {
        val goals = getGoalTestValues(2)
        for (goal in goals) {
            dao.addGoal(goal)
        }

        val retrievedGoal: GoalTypeEntity? = dao.getGoalById(2)
        assertNotNull(retrievedGoal)
        assertEquals(retrievedGoal, goals[1])
    }

    @Test
    fun getMissingGoalByIDTest() = runBlocking() {
        val goals = getGoalTestValues(2)
        for (goal in goals) {
            dao.addGoal(goal)
        }

        assertNull(dao.getGoalById(51))
    }

    @Test
    fun addALotOfGoalsTest() = runBlocking() {
        val goals = getGoalTestValues(100)
        for (goal in goals) {
            dao.addGoal(goal)
        }

        assertEquals(dao.getAllGoals().getOrAwaitValue(), goals)
    }
}
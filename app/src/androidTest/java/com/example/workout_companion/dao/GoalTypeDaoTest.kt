package com.example.workout_companion.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.GoalTypeEntity
import com.example.workout_companion.utility.getOrAwaitValue
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
        val goal = GoalTypeEntity(0, "Test Goal")
        dao.addGoal(goal)

        assertTrue(dao.getAllGoals().getOrAwaitValue().contains(goal))
    }

    @Test
    fun addTwoGoalsTest() = runBlocking() {
        val goal1 = GoalTypeEntity(0, "Goal 1")
        dao.addGoal(goal1)
        val goal2 = GoalTypeEntity(1, "Goal 2")
        dao.addGoal(goal2)

        val allGoals = dao.getAllGoals().getOrAwaitValue()
        assertEquals(allGoals.size, 2)
        assertTrue(allGoals.contains(goal1))
        assertTrue(allGoals.contains(goal2))
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
        val goal1 = GoalTypeEntity(0, "Goal 1")
        dao.addGoal(goal1)
        val goal2 = GoalTypeEntity(1, "Goal 2")
        dao.addGoal(goal2)

        val retrievedGoal: GoalTypeEntity? = dao.getGoalById(1)
        assertNotNull(retrievedGoal)
        assertEquals(retrievedGoal, goal2)
    }

    @Test
    fun getMissingGoalByIDTest() = runBlocking() {
        val goal1 = GoalTypeEntity(0, "Goal 0")
        val goal2 = GoalTypeEntity(1, "Goal 1")
        dao.addGoal(goal1)
        dao.addGoal(goal2)

        assertNull(dao.getGoalById(2))
    }
}
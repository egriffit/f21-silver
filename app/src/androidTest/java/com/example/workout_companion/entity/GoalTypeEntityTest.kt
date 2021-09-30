package com.example.workout_companion.entity

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.utility.getOrAwaitValue
import com.example.workout_companion.dao.GoalTypeDao
import com.example.workout_companion.database.WCDatabase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GoalTypeEntityTest: TestCase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

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
        assert(dao.getAllGoals().getOrAwaitValue().contains(goal))
    }
}
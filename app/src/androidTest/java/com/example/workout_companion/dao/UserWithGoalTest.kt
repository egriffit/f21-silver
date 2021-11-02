package com.example.workout_companion.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
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
class UserWithGoalTest : TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: WCDatabase
    private lateinit var dao: UserWithGoalDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.userWithGoalDao()

        TestDataGenerator.addGoalsToDB(db)
        TestDataGenerator.addUserToDB(db)
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun getUserAndGoalTest() = runBlocking(){
        val userWithGoal = dao.getUserWithGoal().getOrAwaitValue()
        assertEquals(userWithGoal.user, TestDataGenerator.USER)
        assertEquals(userWithGoal.goal, TestDataGenerator.GOALS[0])
    }
}
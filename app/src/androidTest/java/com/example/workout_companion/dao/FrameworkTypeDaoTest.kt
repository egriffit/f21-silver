package com.example.workout_companion.dao

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkTypeEntity
import com.example.workout_companion.entity.FrameworkWithGoalEntity
import com.example.workout_companion.entity.GoalTypeEntity
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
class FrameworkTypeDaoTest : TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: WCDatabase
    private lateinit var goalTypeDao: GoalTypeDao
    private lateinit var frameworkTypeDao: FrameworkTypeDao

    @Before
    public override fun setUp() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        goalTypeDao = db.goalTypeDao()
        frameworkTypeDao = db.frameworkTypeDao()

        // Create some basic goals here
        goalTypeDao.addGoal(GoalTypeEntity(0, "Goal 0", 500))
        goalTypeDao.addGoal(GoalTypeEntity(1, "Goal 1", -500))
        goalTypeDao.addGoal(GoalTypeEntity(2, "Goal 2", 250))
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun addSingleFrameworkTest() = runBlocking {
        val framework = FrameworkTypeEntity(0, "Framework 1", 0,3)
        frameworkTypeDao.addFramework(framework)

        assertTrue(frameworkTypeDao.getAllFrameworks().getOrAwaitValue().contains(framework))
    }

    @Test(expected = SQLiteConstraintException::class)
    fun addFrameworkWithNonExistentGoalTest() = runBlocking {
        val framework = FrameworkTypeEntity(0, "Framework 1", 5, 3)
        frameworkTypeDao.addFramework(framework)
    }

    @Test
    fun addMultipleFrameworksByCollectionTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(10, 0)
        frameworkTypeDao.addFrameworks(frameworks)

        assertEquals(frameworkTypeDao.getAllFrameworks().getOrAwaitValue(), frameworks)
    }

    @Test
    fun addMultipleFrameworksByVarArgsTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(5, 2)
        frameworkTypeDao.addFrameworks(frameworks[0], frameworks[1], frameworks[2], frameworks[3], frameworks[4])

        assertEquals(frameworkTypeDao.getAllFrameworks().getOrAwaitValue(), frameworks)
    }

    @Test
    fun deleteSingleFrameworkTest() = runBlocking {
        val framework = TestDataGenerator.getTestFramework(5, 1)
        frameworkTypeDao.addFramework(framework)
        frameworkTypeDao.deleteFramework(framework)

        assertFalse(frameworkTypeDao.getAllFrameworks().getOrAwaitValue().contains(framework))
    }

    @Test
    fun deleteMultipleFrameworksByCollectionTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(14, 0)
        frameworkTypeDao.addFrameworks(frameworks)
        frameworkTypeDao.deleteFrameworks(frameworks)

        val dbFrameworks = frameworkTypeDao.getAllFrameworks().getOrAwaitValue()
        for (framework in frameworks) {
            assertFalse(dbFrameworks.contains(framework))
        }
    }

    @Test
    fun deleteMultipleFrameworksByVarArgTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(3, 1)
        frameworkTypeDao.addFrameworks(frameworks)
        frameworkTypeDao.deleteFrameworks(frameworks[0], frameworks[2])

        val dbFrameworks = frameworkTypeDao.getAllFrameworks().getOrAwaitValue()
        assertFalse(dbFrameworks.contains(frameworks[0]))
        assertFalse(dbFrameworks.contains(frameworks[2]))
    }

    @Test
    fun getFrameworkByIdTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(10, 1)
        val frameworkToFind = TestDataGenerator.getTestFramework(99, 1)
        frameworkTypeDao.addFrameworks(frameworks + frameworkToFind)

        val foundFramework = frameworkTypeDao.getFrameworkById(99)
        assertNotNull(foundFramework)
        assertEquals(foundFramework, frameworkToFind)
    }

    @Test
    fun getNonExistentFramework() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(5, 0)
        frameworkTypeDao.addFrameworks(frameworks)

        assertNull(frameworkTypeDao.getFrameworkById(100))
    }

    @Test
    fun filterFrameworksByNumWorkoutsTest() = runBlocking {
        val framework1 = FrameworkTypeEntity(0, "Framework 0", 0,3)
        val framework2 = FrameworkTypeEntity(1, "Framework 1", 0,4)
        val framework3 = FrameworkTypeEntity(2, "Framework 2", 2,2)
        frameworkTypeDao.addFrameworks(framework1, framework2, framework3)

        val foundFrameworks = frameworkTypeDao.getFrameworksWithinMaxWorkouts(3).getOrAwaitValue()
        assertTrue(foundFrameworks.contains(framework1))
        assertFalse(foundFrameworks.contains(framework2))
        assertTrue(foundFrameworks.contains(framework3))
    }

    @Test
    fun getFrameworksWithGoalTest() = runBlocking {
        val framework1 = FrameworkTypeEntity(0, "Framework 0", 0, 3)
        val framework2 = FrameworkTypeEntity(1, "Framework 1", 1, 3)
        frameworkTypeDao.addFrameworks(framework1, framework2)

        val foundFrameworks = frameworkTypeDao.getFrameworksWithGoal(1).getOrAwaitValue()
        assertTrue(foundFrameworks.contains(framework2))
        assertFalse(foundFrameworks.contains(framework1))
    }

    @Test
    fun getFrameworksWithGoalNameTest() = runBlocking {
        val framework1 = FrameworkTypeEntity(0, "Framework 0", 0, 3)
        val framework2 = FrameworkTypeEntity(1, "Framework 1", 1, 3)
        frameworkTypeDao.addFrameworks(framework1, framework2)
        val frameworkWithGoal1 = listOf(FrameworkWithGoalEntity(0, "Framework 0", 3, 0, "Goal 0"))
        val foundFramework = frameworkTypeDao.getFrameworksWithGoalName("Goal 0").getOrAwaitValue()
        assertEquals(foundFramework, frameworkWithGoal1)
    }

    @Test
    fun getFrameworksWithGoalNameWithinMaxWorkoutsTest() = runBlocking {
        val framework1 = FrameworkTypeEntity(0, "Framework 0", 0, 3)
        val framework2 = FrameworkTypeEntity(1, "Framework 0", 0, 2)
        val framework3 = FrameworkTypeEntity(2, "Framework 0", 0, 1)

        frameworkTypeDao.addFrameworks(framework1, framework2, framework3)
        val frameworkWithGoal1 = listOf(
            FrameworkWithGoalEntity(1, "Framework 0", 2, 0, "Goal 0"),
            FrameworkWithGoalEntity(2, "Framework 0", 1, 0, "Goal 0"))
        val foundFramework = frameworkTypeDao.getFrameworksWithGoalNameWithinMaxWorkouts("Goal 0",2).getOrAwaitValue()
        assertEquals(foundFramework, frameworkWithGoal1)
    }
}
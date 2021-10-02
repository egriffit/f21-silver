package com.example.workout_companion.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkTypeEntity
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
    private lateinit var dao: FrameworkTypeDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.frameworkTypeDao()
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun addSingleFrameworkTest() = runBlocking {
        val framework = FrameworkTypeEntity(0, "Framework 1", 3)
        dao.addFramework(framework)

        assertTrue(dao.getAllFrameworks().getOrAwaitValue().contains(framework))
    }

    @Test
    fun addMultipleFrameworksByCollectionTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(10)
        dao.addFrameworks(frameworks)

        assertEquals(dao.getAllFrameworks().getOrAwaitValue(), frameworks)
    }

    @Test
    fun addMultipleFrameworksByVarArgsTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(5)
        dao.addFrameworks(frameworks[0], frameworks[1], frameworks[2], frameworks[3], frameworks[4])

        assertEquals(dao.getAllFrameworks().getOrAwaitValue(), frameworks)
    }

    @Test
    fun deleteSingleFrameworkTest() = runBlocking {
        val framework = TestDataGenerator.getTestFramework(5)
        dao.addFramework(framework)
        dao.deleteFramework(framework)

        assertFalse(dao.getAllFrameworks().getOrAwaitValue().contains(framework))
    }

    @Test
    fun deleteMultipleFrameworksByCollectionTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(14)
        dao.addFrameworks(frameworks)
        dao.deleteFrameworks(frameworks)

        val dbFrameworks = dao.getAllFrameworks().getOrAwaitValue()
        for (framework in frameworks) {
            assertFalse(dbFrameworks.contains(framework))
        }
    }

    @Test
    fun deleteMultipleFrameworksByVarArgTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(3)
        dao.addFrameworks(frameworks)
        dao.deleteFrameworks(frameworks[0], frameworks[2])

        val dbFrameworks = dao.getAllFrameworks().getOrAwaitValue()
        assertFalse(dbFrameworks.contains(frameworks[0]))
        assertFalse(dbFrameworks.contains(frameworks[2]))
    }

    @Test
    fun getFrameworkByIdTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(10)
        val frameworkToFind = TestDataGenerator.getTestFramework(99)
        dao.addFrameworks(frameworks + frameworkToFind)

        val foundFramework = dao.getFrameworkById(99)
        assertNotNull(foundFramework)
        assertEquals(foundFramework, frameworkToFind)
    }

    @Test
    fun getNonExistentFramework() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(5)
        dao.addFrameworks(frameworks)

        assertNull(dao.getFrameworkById(100))
    }

    @Test
    fun filterFrameworksByNumWorkoutsTest() = runBlocking {
        val framework1 = FrameworkTypeEntity(0, "Framework 0", 3)
        val framework2 = FrameworkTypeEntity(1, "Framework 1", 4)
        val framework3 = FrameworkTypeEntity(2, "Framework 2", 2)
        dao.addFrameworks(framework1, framework2, framework3)

        val foundFrameworks = dao.getFrameworksWithinMaxWorkouts(3).getOrAwaitValue()
        assertTrue(foundFrameworks.contains(framework1))
        assertFalse(foundFrameworks.contains(framework2))
        assertTrue(foundFrameworks.contains(framework3))
    }
}
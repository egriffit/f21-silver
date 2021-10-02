package com.example.workout_companion.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.FrameworkTypeDao
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkTypeEntity
import com.example.workout_companion.utility.TestDataGenerator
import com.example.workout_companion.utility.getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FrameworkTypeRepositoryTest : TestCase() {

    private lateinit var db: WCDatabase
    private lateinit var dao: FrameworkTypeDao
    private lateinit var repository: FrameworkTypeRepository

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.frameworkTypeDao()
        repository = FrameworkTypeRepository(dao)
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun addSingleFrameworkTest() = runBlocking {
        val framework = FrameworkTypeEntity(0, "Framework 1", 3)
        repository.addFramework(framework)

        assertTrue(repository.allFrameworks.getOrAwaitValue().contains(framework))
    }

    @Test
    fun addMultipleFrameworksByCollectionTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(10)
        repository.addFrameworks(frameworks)

        assertEquals(repository.allFrameworks.getOrAwaitValue(), frameworks)
    }

    @Test
    fun addMultipleFrameworksByVarArgsTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(5)
        repository.addFrameworks(frameworks[0], frameworks[1], frameworks[2], frameworks[3], frameworks[4])

        assertEquals(repository.allFrameworks.getOrAwaitValue(), frameworks)
    }

    @Test
    fun deleteSingleFrameworkTest() = runBlocking {
        val framework = TestDataGenerator.getTestFramework(5)
        repository.addFramework(framework)
        repository.deleteFramework(framework)

        assertFalse(repository.allFrameworks.getOrAwaitValue().contains(framework))
    }

    @Test
    fun deleteMultipleFrameworksByCollectionTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(14)
        repository.addFrameworks(frameworks)
        repository.deleteFrameworks(frameworks)

        val dbFrameworks = repository.allFrameworks.getOrAwaitValue()
        for (framework in frameworks) {
            assertFalse(dbFrameworks.contains(framework))
        }
    }

    @Test
    fun deleteMultipleFrameworksByVarArgTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(3)
        repository.addFrameworks(frameworks)
        repository.deleteFrameworks(frameworks[0], frameworks[2])

        val dbFrameworks = repository.allFrameworks.getOrAwaitValue()
        assertFalse(dbFrameworks.contains(frameworks[0]))
        assertFalse(dbFrameworks.contains(frameworks[2]))
    }

    @Test
    fun getFrameworkByIdTest() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(10)
        val frameworkToFind = TestDataGenerator.getTestFramework(99)
        repository.addFrameworks(frameworks + frameworkToFind)

        val foundFramework = repository.getFrameworkById(99)
        assertNotNull(foundFramework)
        assertEquals(foundFramework, frameworkToFind)
    }

    @Test
    fun getNonExistentFramework() = runBlocking {
        val frameworks = TestDataGenerator.getTestFrameworks(5)
        repository.addFrameworks(frameworks)

        assertNull(repository.getFrameworkById(100))
    }

    @Test
    fun filterFrameworksByNumWorkoutsTest() = runBlocking {
        val framework1 = FrameworkTypeEntity(0, "Framework 0", 3)
        val framework2 = FrameworkTypeEntity(1, "Framework 1", 4)
        val framework3 = FrameworkTypeEntity(2, "Framework 2", 2)
        repository.addFrameworks(framework1, framework2, framework3)

        val foundFrameworks = repository.getFrameworksWithinMaxWorkouts(3).getOrAwaitValue()
        assertTrue(foundFrameworks.contains(framework1))
        assertFalse(foundFrameworks.contains(framework2))
        assertTrue(foundFrameworks.contains(framework3))
    }

}

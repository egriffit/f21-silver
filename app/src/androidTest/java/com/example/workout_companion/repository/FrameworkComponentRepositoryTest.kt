package com.example.workout_companion.repository

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.FrameworkComponentDao
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkComponentEntity
import com.example.workout_companion.enumeration.MuscleGroup
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
class FrameworkComponentRepositoryTest : TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db : WCDatabase
    private lateinit var repository: FrameworkComponentRepository

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        repository = FrameworkComponentRepository(db.frameworkComponentDao())

        // Framework components need days
        // Framework days need frameworks
        // Frameworks need goals
        TestDataGenerator.addGoalsToDB(db)
        TestDataGenerator.addFrameworksToDB(db)
        TestDataGenerator.addFrameworkDaysToDB(db)
    }

    @After
    fun close() {
        db.close()
    }
    
    @Test
    fun addSingleComponentTest() = runBlocking {
        repository.addFrameworkComponent(TestDataGenerator.FRAMEWORK_0_DAY_0_COMPONENTS[0])

        val day0ComponentsInDB = repository.getAllComponentsOfDay(TestDataGenerator.FRAMEWORK_0_DAYS[0].id).getOrAwaitValue()
        assertEquals(day0ComponentsInDB.size, 1)
        assertTrue(day0ComponentsInDB.contains(TestDataGenerator.FRAMEWORK_0_DAY_0_COMPONENTS[0]))
    }

    @Test
    fun addCollectionOfComponentsTest() = runBlocking {
        repository.addFrameworkComponents(TestDataGenerator.FRAMEWORK_0_DAY_0_COMPONENTS)

        val day0ComponentsInDB = repository.getAllComponentsOfDay(TestDataGenerator.FRAMEWORK_0_DAYS[0].id).getOrAwaitValue()
        assertEquals(day0ComponentsInDB, TestDataGenerator.FRAMEWORK_0_DAY_0_COMPONENTS)
    }

    @Test
    fun addVarargOfComponentsTest() = runBlocking {
        repository.addFrameworkComponents(TestDataGenerator.FRAMEWORK_2_DAY_1_COMPONENTS[0],
            TestDataGenerator.FRAMEWORK_2_DAY_1_COMPONENTS[1])

        val day2ComponentsInDB = repository.getAllComponentsOfDay(TestDataGenerator.FRAMEWORK_2_DAYS[1].id).getOrAwaitValue()
        assertEquals(day2ComponentsInDB.size, 2)
        assertTrue(day2ComponentsInDB.contains(TestDataGenerator.FRAMEWORK_2_DAY_1_COMPONENTS[0]))
        assertTrue(day2ComponentsInDB.contains(TestDataGenerator.FRAMEWORK_2_DAY_1_COMPONENTS[1]))
    }

    @Test(expected = SQLiteConstraintException::class)
    fun addComponentToNonExistentDay() = runBlocking {
        val component = FrameworkComponentEntity(0, 900, MuscleGroup.BACK, 3, 30)
        repository.addFrameworkComponent(component)
    }

    @Test
    fun updateComponentTest() = runBlocking {
        val component = FrameworkComponentEntity(0, 0, MuscleGroup.ABS, 3, 30)
        repository.addFrameworkComponent(component)

        val updatedComponent = FrameworkComponentEntity(0, 0, MuscleGroup.BACK, 5, 25)
        repository.updateFrameworkComponent(updatedComponent)

        val day0ComponentsInDB = repository.getAllComponentsOfDay(0).getOrAwaitValue()
        assertEquals(day0ComponentsInDB.first(), updatedComponent)
    }

    @Test
    fun updateNonExistentComponentTest() = runBlocking {
        val component = FrameworkComponentEntity(0, 0, MuscleGroup.BACK, 3, 15)
        val componentsBeforeUpdate = repository.getAllComponentsOfDay(0).getOrAwaitValue()

        repository.updateFrameworkComponent(component)
        val componentsAfterUpdate = repository.getAllComponentsOfDay(0).getOrAwaitValue()

        assertEquals(componentsBeforeUpdate, componentsAfterUpdate)
    }

    @Test
    fun deleteSingleComponentTest() = runBlocking {
        repository.addFrameworkComponent(TestDataGenerator.FRAMEWORK_2_DAY_1_COMPONENTS[0])
        repository.deleteFrameworkComponent(TestDataGenerator.FRAMEWORK_2_DAY_1_COMPONENTS[0])

        val day1ComponentsInDB = repository.getAllComponentsOfDay(TestDataGenerator.FRAMEWORK_2_DAYS[1].id).getOrAwaitValue()
        assertTrue(day1ComponentsInDB.isEmpty())
    }

    @Test
    fun deleteCollectionOfComponentsTest() = runBlocking {
        repository.addFrameworkComponents(TestDataGenerator.FRAMEWORK_2_DAY_0_COMPONENTS)
        repository.deleteFrameworkComponents(TestDataGenerator.FRAMEWORK_2_DAY_0_COMPONENTS)

        val day0ComponentsInDB = repository.getAllComponentsOfDay(TestDataGenerator.FRAMEWORK_2_DAYS[0].id).getOrAwaitValue()
        assertTrue(day0ComponentsInDB.isEmpty())
    }

    @Test
    fun deleteVarargOfComponentsTest() = runBlocking {
        repository.addFrameworkComponents(TestDataGenerator.FRAMEWORK_2_DAY_1_COMPONENTS)
        repository.deleteFrameworkComponents(TestDataGenerator.FRAMEWORK_2_DAY_1_COMPONENTS[0],
            TestDataGenerator.FRAMEWORK_2_DAY_1_COMPONENTS[1],
            TestDataGenerator.FRAMEWORK_2_DAY_1_COMPONENTS[2])

        val day1ComponentsInDB = repository.getAllComponentsOfDay(TestDataGenerator.FRAMEWORK_2_DAYS[1].id).getOrAwaitValue()
        assertFalse(day1ComponentsInDB.contains(TestDataGenerator.FRAMEWORK_2_DAY_1_COMPONENTS[0]))
        assertFalse(day1ComponentsInDB.contains(TestDataGenerator.FRAMEWORK_2_DAY_1_COMPONENTS[1]))
        assertFalse(day1ComponentsInDB.contains(TestDataGenerator.FRAMEWORK_2_DAY_1_COMPONENTS[2]))
    }

    @Test
    fun deleteNonExistentComponentTest() = runBlocking {
        repository.addFrameworkComponents(TestDataGenerator.FRAMEWORK_0_DAY_0_COMPONENTS)

        val component = FrameworkComponentEntity(424, TestDataGenerator.FRAMEWORK_0_DAYS[0].id, MuscleGroup.BACK, 3, 15)
        val componentsBeforeRemove = repository.getAllComponentsOfDay(TestDataGenerator.FRAMEWORK_0_DAYS[0].id).getOrAwaitValue()

        repository.updateFrameworkComponent(component)
        val componentsAfterRemove = repository.getAllComponentsOfDay(TestDataGenerator.FRAMEWORK_0_DAYS[0].id).getOrAwaitValue()

        assertEquals(componentsBeforeRemove, componentsAfterRemove)
    }
}

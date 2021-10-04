package com.example.workout_companion.dao

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkDayEntity
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
class FrameworkDayDaoTest : TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: WCDatabase
    private lateinit var frameworkDayDao: FrameworkDayDao

    @Before
    public override fun setUp() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        frameworkDayDao = db.frameworkDayDao()

        // Framework days need frameworks
        // Frameworks need goals
        TestDataGenerator.addGoalsToDB(db)
        TestDataGenerator.addFrameworksToDB(db)
    }

    @After
    fun close() {
        db.close()
    }

    @Test
    fun addSingleDayTest() = runBlocking {
        frameworkDayDao.addFrameworkDay(TestDataGenerator.FRAMEWORK_0_DAYS[0])

        val framework0DaysInDB = frameworkDayDao.getAllFrameworkDays(0).getOrAwaitValue()
        assertEquals(framework0DaysInDB.size, 1)
        assertTrue(framework0DaysInDB.contains(TestDataGenerator.FRAMEWORK_0_DAYS[0]))
    }

    @Test
    fun addCollectionOfDaysTest() = runBlocking {
        frameworkDayDao.addFrameworkDays(TestDataGenerator.FRAMEWORK_1_DAYS)

        val framework1DaysInDB = frameworkDayDao.getAllFrameworkDays(1).getOrAwaitValue()
        assertEquals(framework1DaysInDB, TestDataGenerator.FRAMEWORK_1_DAYS)
    }

    @Test
    fun addVarargDaysTest() = runBlocking {
        frameworkDayDao.addFrameworkDays(TestDataGenerator.FRAMEWORK_2_DAYS[0],
            TestDataGenerator.FRAMEWORK_2_DAYS[1])

        val framework2DaysInDB = frameworkDayDao.getAllFrameworkDays(2).getOrAwaitValue()
        assertEquals(framework2DaysInDB.size, 2)
        assertTrue(framework2DaysInDB.contains(TestDataGenerator.FRAMEWORK_2_DAYS[0]))
        assertTrue(framework2DaysInDB.contains(TestDataGenerator.FRAMEWORK_2_DAYS[1]))
    }

    @Test(expected = SQLiteConstraintException::class)
    fun addDayToNonExistentFramework() = runBlocking {
        frameworkDayDao.addFrameworkDay(FrameworkDayEntity(100, 10, "Bad Day"))
    }

    @Test
    fun deleteSingleDay() = runBlocking {
        frameworkDayDao.addFrameworkDays(TestDataGenerator.FRAMEWORK_4_DAYS)
        frameworkDayDao.deleteFrameworkDay(TestDataGenerator.FRAMEWORK_4_DAYS[1])

        val framework4DaysInDB = frameworkDayDao.getAllFrameworkDays(4).getOrAwaitValue()
        assertFalse(framework4DaysInDB.contains(TestDataGenerator.FRAMEWORK_4_DAYS[1]))
    }

    @Test
    fun deleteCollectionOfDaysTest() = runBlocking {
        frameworkDayDao.addFrameworkDays(TestDataGenerator.FRAMEWORK_5_DAYS)
        frameworkDayDao.deleteFrameworkDays(TestDataGenerator.FRAMEWORK_5_DAYS)

        val framework5DaysInDB = frameworkDayDao.getAllFrameworkDays(5).getOrAwaitValue()
        for (day in TestDataGenerator.FRAMEWORK_5_DAYS) {
            assertFalse(framework5DaysInDB.contains(day))
        }
    }

    @Test
    fun deleteVarargDaysTest() = runBlocking {
        frameworkDayDao.addFrameworkDays(TestDataGenerator.FRAMEWORK_0_DAYS)
        frameworkDayDao.deleteFrameworkDays(TestDataGenerator.FRAMEWORK_0_DAYS[0],
            TestDataGenerator.FRAMEWORK_0_DAYS[2])

        val framework0DaysInDB = frameworkDayDao.getAllFrameworkDays(0).getOrAwaitValue()
        assertFalse(framework0DaysInDB.contains(TestDataGenerator.FRAMEWORK_0_DAYS[0]))
        assertFalse(framework0DaysInDB.contains(TestDataGenerator.FRAMEWORK_0_DAYS[2]))
    }

    @Test
    fun deleteNonExistentDayTest() = runBlocking {
        frameworkDayDao.addFrameworkDays(TestDataGenerator.FRAMEWORK_0_DAYS)
        val daysBeforeDelete = frameworkDayDao.getAllFrameworkDays(0).getOrAwaitValue()

        frameworkDayDao.deleteFrameworkDay(FrameworkDayEntity(10, 10, "Bad Day"))
        val daysAfterDelete = frameworkDayDao.getAllFrameworkDays(0).getOrAwaitValue()

        assertEquals(daysBeforeDelete, daysAfterDelete)
    }

    @Test
    fun updateSingleDayTest() = runBlocking {
        val day = FrameworkDayEntity(0, 1, "Test Day")
        frameworkDayDao.addFrameworkDay(day)

        day.name = "New Name"
        frameworkDayDao.updateFrameworkDay(day)

        val framework1DaysInDB = frameworkDayDao.getAllFrameworkDays(1).getOrAwaitValue()
        assertEquals(framework1DaysInDB.size, 1)
        assertEquals(framework1DaysInDB[0].name, "New Name")
    }

    @Test
    fun updateNonExistentDayTest() = runBlocking {
        val day = FrameworkDayEntity(0, 1, "Test Day")
        frameworkDayDao.updateFrameworkDay(day)

        val framework1DaysInDB = frameworkDayDao.getAllFrameworkDays(1).getOrAwaitValue()
        assertTrue(framework1DaysInDB.isEmpty())
    }
}
package com.example.workout_companion.repository

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.FrameworkDayDao
import com.example.workout_companion.dao.FrameworkTypeDao
import com.example.workout_companion.dao.GoalTypeDao
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.FrameworkDayEntity
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
class FrameworkDayRepositoryTest : TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: WCDatabase
    private lateinit var frameworkDayRepository: FrameworkDayRepository

    @Before
    public override fun setUp() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        frameworkDayRepository = FrameworkDayRepository(db.frameworkDayDao())

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
        frameworkDayRepository.addFrameworkDay(TestDataGenerator.FRAMEWORK_0_DAYS[0])

        val framework0DaysInDB = frameworkDayRepository.getAllFrameworkDays(0)
        TestCase.assertEquals(framework0DaysInDB.size, 1)
        TestCase.assertTrue(framework0DaysInDB.contains(TestDataGenerator.FRAMEWORK_0_DAYS[0]))
    }

    @Test
    fun addCollectionOfDaysTest() = runBlocking {
        frameworkDayRepository.addFrameworkDays(TestDataGenerator.FRAMEWORK_1_DAYS)

        val framework1DaysInDB = frameworkDayRepository.getAllFrameworkDays(1)
        TestCase.assertEquals(framework1DaysInDB, TestDataGenerator.FRAMEWORK_1_DAYS)
    }

    @Test
    fun addVarargDaysTest() = runBlocking {
        frameworkDayRepository.addFrameworkDays(
            TestDataGenerator.FRAMEWORK_2_DAYS[0],
            TestDataGenerator.FRAMEWORK_2_DAYS[1])

        val framework2DaysInDB = frameworkDayRepository.getAllFrameworkDays(2)
        TestCase.assertEquals(framework2DaysInDB.size, 2)
        TestCase.assertTrue(framework2DaysInDB.contains(TestDataGenerator.FRAMEWORK_2_DAYS[0]))
        TestCase.assertTrue(framework2DaysInDB.contains(TestDataGenerator.FRAMEWORK_2_DAYS[1]))
    }

    @Test(expected = SQLiteConstraintException::class)
    fun addDayToNonExistentFramework() = runBlocking {
        frameworkDayRepository.addFrameworkDay(FrameworkDayEntity(100, 10, "Bad Day"))
    }

    @Test
    fun deleteSingleDay() = runBlocking {
        frameworkDayRepository.addFrameworkDays(TestDataGenerator.FRAMEWORK_4_DAYS)
        frameworkDayRepository.deleteFrameworkDay(TestDataGenerator.FRAMEWORK_4_DAYS[1])

        val framework4DaysInDB = frameworkDayRepository.getAllFrameworkDays(4)
        TestCase.assertFalse(framework4DaysInDB.contains(TestDataGenerator.FRAMEWORK_4_DAYS[1]))
    }

    @Test
    fun deleteCollectionOfDaysTest() = runBlocking {
        frameworkDayRepository.addFrameworkDays(TestDataGenerator.FRAMEWORK_5_DAYS)
        frameworkDayRepository.deleteFrameworkDays(TestDataGenerator.FRAMEWORK_5_DAYS)

        val framework5DaysInDB = frameworkDayRepository.getAllFrameworkDays(5)
        for (day in TestDataGenerator.FRAMEWORK_5_DAYS) {
            TestCase.assertFalse(framework5DaysInDB.contains(day))
        }
    }

    @Test
    fun deleteVarargDaysTest() = runBlocking {
        frameworkDayRepository.addFrameworkDays(TestDataGenerator.FRAMEWORK_0_DAYS)
        frameworkDayRepository.deleteFrameworkDays(
            TestDataGenerator.FRAMEWORK_0_DAYS[0],
            TestDataGenerator.FRAMEWORK_0_DAYS[2])

        val framework0DaysInDB = frameworkDayRepository.getAllFrameworkDays(0)
        TestCase.assertFalse(framework0DaysInDB.contains(TestDataGenerator.FRAMEWORK_0_DAYS[0]))
        TestCase.assertFalse(framework0DaysInDB.contains(TestDataGenerator.FRAMEWORK_0_DAYS[2]))
    }

    @Test
    fun deleteNonExistentDayTest() = runBlocking {
        frameworkDayRepository.addFrameworkDays(TestDataGenerator.FRAMEWORK_0_DAYS)
        val daysBeforeDelete = frameworkDayRepository.getAllFrameworkDays(0)

        frameworkDayRepository.deleteFrameworkDay(FrameworkDayEntity(10, 10, "Bad Day"))
        val daysAfterDelete = frameworkDayRepository.getAllFrameworkDays(0)

        TestCase.assertEquals(daysBeforeDelete, daysAfterDelete)
    }

    @Test
    fun updateSingleDayTest() = runBlocking {
        val day = FrameworkDayEntity(0, 1, "Test Day")
        frameworkDayRepository.addFrameworkDay(day)

        day.name = "New Name"
        frameworkDayRepository.updateFrameworkDay(day)

        val framework1DaysInDB = frameworkDayRepository.getAllFrameworkDays(1)
        TestCase.assertEquals(framework1DaysInDB.size, 1)
        TestCase.assertEquals(framework1DaysInDB[0].name, "New Name")
    }

    @Test
    fun updateNonExistentDayTest() = runBlocking {
        val day = FrameworkDayEntity(0, 1, "Test Day")
        frameworkDayRepository.updateFrameworkDay(day)

        val framework1DaysInDB = frameworkDayRepository.getAllFrameworkDays(1)
        TestCase.assertTrue(framework1DaysInDB.isEmpty())
    }
}

package com.example.workout_companion.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.UserDao
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.utility.*
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.Month

@RunWith(AndroidJUnit4::class)
class UserRepositoryTest : TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: WCDatabase
    private lateinit var dao: UserDao
    private lateinit var repository: UserRepository

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.userDao()
        repository = UserRepository(dao)
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun testWriteAndReadUser() = runBlocking{
        repository.addUser(TestDataGenerator.USER)
        val byName = repository.getByName("John Smith")
        MatcherAssert.assertThat(byName, CoreMatchers.equalTo(TestDataGenerator.USER))
    }

    @Test
    fun testUserLiveData() = runBlocking {
        repository.addUser(TestDataGenerator.USER)
        val userInDB = repository.user.getOrAwaitValue()
        assertEquals(TestDataGenerator.USER, userInDB)
    }

    @Test
    fun testGetCount() = runBlocking{
        repository.addUser(TestDataGenerator.USER)
        val count: Int = repository.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(1))
    }

    @Test
    fun testGetCountWithName() = runBlocking{
        repository.addUser(TestDataGenerator.USER)
        val count: Int = repository.getCountWithName("John Smith")
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(1))
    }

    @Test
    fun testCheckIfUserExists() = runBlocking{
        repository.addUser(TestDataGenerator.USER)
        val exists: Boolean = repository.checkIfUserExists(TestDataGenerator.USER.name)
        assertTrue(exists)
    }

    @Test
    fun testUpdateUser() = runBlocking{
        val updatedUser = TestDataGenerator.USER
        updatedUser.experience_level = ExperienceLevel.EXPERT

        dao.insert(TestDataGenerator.USER)
        dao.update(updatedUser)
        val byName = dao.getByName(updatedUser.name)
        MatcherAssert.assertThat(byName.experience_level, CoreMatchers.equalTo(ExperienceLevel.EXPERT))
    }

    @Test
    fun testDelete() = runBlocking{
        repository.addUser(TestDataGenerator.USER)
        repository.deleteUser(TestDataGenerator.USER)
        val exists: Boolean = repository.checkIfUserExists(TestDataGenerator.USER.name)
        assertFalse(exists)
    }

    @Test
    fun testDeleteAll() = runBlocking{
        repository.addUser(TestDataGenerator.USER)
        repository.deleteAll()
        val count: Int = repository.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(0))
    }

    @Test
    fun testAge() = runBlocking{
        repository.addUser(TestDataGenerator.USER)
        val age: Int = repository.getAge(TestDataGenerator.USER.name)
        MatcherAssert.assertThat(age, CoreMatchers.equalTo(31))
    }

    @Test
    fun testWeight() = runBlocking {
        repository.addUser(TestDataGenerator.USER)
        val repoWeight = repository.getWeight(TestDataGenerator.USER.name)
        MatcherAssert.assertThat(TestDataGenerator.USER.weight, CoreMatchers.equalTo(repoWeight))
    }

}
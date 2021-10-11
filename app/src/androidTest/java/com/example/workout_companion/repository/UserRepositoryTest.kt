package com.example.workout_companion.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.UserDao
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.utility.ActivityLevel
import com.example.workout_companion.utility.Sex
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.Month

@RunWith(AndroidJUnit4::class)
class UserRepositoryTest : TestCase() {

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
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2, 160.0, ActivityLevel.MODERATELY_ACTIVE)
        repository.addUser(user)
        val byName = repository.getByName("John Smith")
        MatcherAssert.assertThat(byName, CoreMatchers.equalTo(user))
    }

    @Test
    fun testGetCount() = runBlocking{
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2,160.0, ActivityLevel.MODERATELY_ACTIVE)
        repository.addUser(user)
        val count: Int = repository.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(1))
    }

    @Test
    fun testGetCountWithName() = runBlocking{
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2,160.0, ActivityLevel.MODERATELY_ACTIVE)
        repository.addUser(user)
        val count: Int = repository.getCountWithName("John Smith")
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(1))
    }

    @Test
    fun testCheckIfUserExists() = runBlocking{
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2, 160.0, ActivityLevel.MODERATELY_ACTIVE)
        repository.addUser(user)
        val exists: Boolean = repository.checkIfUserExists("John Smith")
        assertTrue(exists)
    }


    @Test
    fun testUpdateUser() = runBlocking{
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2, 160.0, ActivityLevel.MODERATELY_ACTIVE)
        val newUser = UserEntity("John Smith", "experienced", Sex.MALE, birthDate, 2, 180.0, ActivityLevel.MODERATELY_ACTIVE)

        dao.insert(user)
        dao.update(newUser)
        val byName = dao.getByName("John Smith")
        MatcherAssert.assertThat(byName.experience_level, CoreMatchers.equalTo("experienced"))
    }

    @Test
    fun testDelete() = runBlocking{
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2, 160.0, ActivityLevel.MODERATELY_ACTIVE)
        repository.addUser(user)
        repository.deleteUser(user)
        val exists: Boolean = repository.checkIfUserExists("John Smith")
        assertFalse(exists)
    }

    @Test
    fun testDeleteAll() = runBlocking{
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2, 160.0, ActivityLevel.MODERATELY_ACTIVE)
        repository.addUser(user)
        repository.deletaAll()
        val count: Int = repository.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(0))
    }


    @Test
    fun testAge() = runBlocking{
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2, 160.0, ActivityLevel.MODERATELY_ACTIVE)

        repository.addUser(user)
        val age: Int = repository.getAge("John Smith")
        MatcherAssert.assertThat(age, CoreMatchers.equalTo(31))
    }

}
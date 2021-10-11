package com.example.workout_companion.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
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
class UserDaoTest : TestCase(){

    private lateinit var db: WCDatabase
    private lateinit var dao: UserDao
    @Before
    public override fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.userDao()
    }

    @After
    fun closeDB(){
        db.close()
    }

    @Test
    fun TestWriteAndReadUser() = runBlocking(){
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2, 160.0, ActivityLevel.MODERATELY_ACTIVE)
        dao.insert(user)
        val byName = dao.getByName("John Smith")
        MatcherAssert.assertThat(byName, CoreMatchers.equalTo(user))
    }

    @Test
    fun TestCount() = runBlocking(){
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val birthDate2 = LocalDate.of(1947, Month.JULY, 30)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2, 160.0,ActivityLevel.MODERATELY_ACTIVE)
        val user2 = UserEntity("Arnold Schwarzenegger", "expert", Sex.MALE, birthDate2, 3, 180.0,ActivityLevel.VERY_ACTIVE)

        dao.insert(user)
        dao.insert(user2)
        val count: Int = dao.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(2))
    }

    @Test
    fun TestCountWithName() = runBlocking(){
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2, 160.0,ActivityLevel.MODERATELY_ACTIVE)

        dao.insert(user)
        val count: Int = dao.getCountWithName("John Smith")
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(1))
    }

    @Test
    fun TestDelete() = runBlocking(){
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2, 160.0, ActivityLevel.MODERATELY_ACTIVE)

        dao.insert(user)
        dao.delete(user)
        val count: Int = dao.getCountWithName("John Smith")
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(0))
    }

    @Test
    fun TestDeleteAll() = runBlocking(){
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val birthDate2 = LocalDate.of(1947, Month.JULY, 30)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2, 160.0, ActivityLevel.MODERATELY_ACTIVE)
        val user2 = UserEntity("Arnold Schwarzenegger", "expert", Sex.MALE, birthDate2, 3, 180.0, ActivityLevel.EXTRA_ACTIVE)

        dao.insert(user)
        dao.insert(user2)
        dao.deleteAll()
        val count: Int = dao.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(0))
    }

    @Test
    fun TestUpdateUser() = runBlocking(){
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2, 160.0, ActivityLevel.MODERATELY_ACTIVE)
        val newUser = UserEntity("John Smith", "experienced", Sex.MALE, birthDate, 2, 160.0, ActivityLevel.MODERATELY_ACTIVE)

        dao.insert(user)
        dao.update(newUser)
        val byName = dao.getByName("John Smith")
        MatcherAssert.assertThat(byName.experience_level, CoreMatchers.equalTo("experienced"))
    }

    @Test
    fun TestBirthDate() = runBlocking(){
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2, 160.0, ActivityLevel.MODERATELY_ACTIVE)

        dao.insert(user)
        val bDate: LocalDate = dao.getBirthDate("John Smith")
        MatcherAssert.assertThat(bDate, CoreMatchers.equalTo(birthDate))
    }


    @Test
    fun TestAge() = runBlocking(){
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", Sex.MALE, birthDate, 2, 160.0, ActivityLevel.MODERATELY_ACTIVE)

        dao.insert(user)
        val age: Int = dao.getAge("John Smith")
        MatcherAssert.assertThat(age, CoreMatchers.equalTo(31))
    }
}
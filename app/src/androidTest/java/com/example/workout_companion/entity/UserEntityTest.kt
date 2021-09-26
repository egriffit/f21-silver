package com.example.workout_companion.entity

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.UserDao
import com.example.workout_companion.database.WCDatabase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.Month


@RunWith(AndroidJUnit4::class)
class UserEntityTest : TestCase(){

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
        val user = UserEntity("John Smith", "beginner", "male", birthDate, 2, "moderate")
        dao.insert(user)
        val byName = dao.getByName("John Smith")
        MatcherAssert.assertThat(byName, CoreMatchers.equalTo(user))
    }

    @Test
    fun TestUpdateUser() = runBlocking(){
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", "male", birthDate, 2, "moderate")
        val newUser = UserEntity("John Smith", "experienced", "male", birthDate, 2, "moderate")

        dao.insert(user)
        dao.update(newUser)
        val byName = dao.getByName("John Smith")
        MatcherAssert.assertThat(byName.experience_level, CoreMatchers.equalTo("experienced"))
    }

    @Test
    fun TestBirthDate() = runBlocking(){
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", "male", birthDate, 2, "moderate")

        dao.insert(user)
        val bDate: LocalDate = dao.getBirthDate("John Smith")
        MatcherAssert.assertThat(bDate, CoreMatchers.equalTo(birthDate))
    }


    @Test
    fun TestAge() = runBlocking(){
        val birthDate = LocalDate.of (1990, Month.JANUARY, 1)
        val user = UserEntity("John Smith", "beginner", "male", birthDate, 2, "moderate")

        dao.insert(user)
        val age: Int = dao.getAge("John Smith")
        MatcherAssert.assertThat(age, CoreMatchers.equalTo(31))
    }
}
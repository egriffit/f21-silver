package com.example.workout_companion.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.utility.ActivityLevel
import com.example.workout_companion.utility.ExperienceLevel
import com.example.workout_companion.utility.Sex
import com.example.workout_companion.utility.TestDataGenerator
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

        TestDataGenerator.addGoalsToDB(db)
    }

    @After
    fun closeDB(){
        db.close()
    }

    @Test
    fun TestWriteAndReadUser() = runBlocking(){
        dao.insert(TestDataGenerator.USER)
        val byName = dao.getByName(TestDataGenerator.USER.name)
        MatcherAssert.assertThat(byName, CoreMatchers.equalTo(TestDataGenerator.USER))
    }

    @Test
    fun TestCount() = runBlocking(){
        dao.insert(TestDataGenerator.USER)
        val count: Int = dao.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(1))
    }

    @Test
    fun TestCountWithName() = runBlocking(){
        dao.insert(TestDataGenerator.USER)
        val count: Int = dao.getCountWithName(TestDataGenerator.USER.name)
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(1))
    }

    @Test
    fun TestDelete() = runBlocking(){
        dao.insert(TestDataGenerator.USER)
        dao.delete(TestDataGenerator.USER)
        val count: Int = dao.getCountWithName(TestDataGenerator.USER.name)
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(0))
    }

    @Test
    fun TestDeleteAll() = runBlocking(){
        dao.insert(TestDataGenerator.USER)
        dao.deleteAll()
        val count: Int = dao.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(0))
    }

    @Test
    fun TestUpdateUser() = runBlocking(){
        var updatedUser = TestDataGenerator.USER
        updatedUser.weight = 18240.03

        dao.insert(TestDataGenerator.USER)
        dao.update(updatedUser)
        val byName = dao.getByName(TestDataGenerator.USER.name)
        MatcherAssert.assertThat(byName, CoreMatchers.equalTo(updatedUser))
    }

    @Test
    fun TestBirthDate() = runBlocking(){
        dao.insert(TestDataGenerator.USER)
        val bDate: LocalDate = dao.getBirthDate(TestDataGenerator.USER.name)
        MatcherAssert.assertThat(bDate, CoreMatchers.equalTo(TestDataGenerator.USER.birth_date))
    }


    @Test
    fun TestAge() = runBlocking(){
        dao.insert(TestDataGenerator.USER)
        val age: Int = dao.getAge(TestDataGenerator.USER.name)
        MatcherAssert.assertThat(age, CoreMatchers.equalTo(31))
    }

    @Test
    fun TestWeight() = runBlocking {
        dao.insert(TestDataGenerator.USER)
        val daoWeight = dao.getWeight(TestDataGenerator.USER.name)
        MatcherAssert.assertThat(TestDataGenerator.USER.weight, CoreMatchers.equalTo(daoWeight))
    }
}
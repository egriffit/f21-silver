package com.example.workout_companion.mock.entity

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.NutritionPlanTypeDao
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.CurrentUserGoalEntity
import com.example.workout_companion.entity.NutritionPlanTypeEntity
import com.example.workout_companion.mock.dao.CurrentUserGoalDao
import com.example.workout_companion.mock.dao.FrameworkTypeDao
import com.example.workout_companion.mock.dao.GoalTypeDao
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrentUserGoalEntityTest : TestCase(){
    private lateinit var db: WCDatabase
    private lateinit var dao: CurrentUserGoalDao
    private lateinit var nutritionDao: NutritionPlanTypeDao
    private lateinit var goalDao: GoalTypeDao
    private lateinit var framewworkDao: FrameworkTypeDao


    @Before
    public override fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.currentUserDao()
        goalDao = db.goalTypeDao()
        nutritionDao = db.nutritionPlanTypeDao()
        framewworkDao = db.frameworkTypeDao()
    }

    @After
    fun closeDB(){
        db.close()
    }

    @Test
    fun testWriteAndReadNutritionPlanType() = runBlocking{
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        val goal = listOf(
            GoalTypeEntity(1, "Lose Weight"),
            GoalTypeEntity(2, "Gain Mass"),
            GoalTypeEntity(3, "Gain Strength"),
        )
        val frameworks = listOf(
            FrameworkTypeEntity(1, "framework_1", 1, 1 ),
            FrameworkTypeEntity(2, "framework_2", 2, 1 ),
            FrameworkTypeEntity(3, "framework_3", 3, 1 ),
            FrameworkTypeEntity(4, "framework_4", 1, 2 ),
            FrameworkTypeEntity(5, "framework_5", 2, 2 ),
            FrameworkTypeEntity(6, "framework_6", 2, 3 ),
            FrameworkTypeEntity(7, "framework_7", 2, 1 ),
        )
        val currentGoal = CurrentUserGoalEntity(1, 4)
        nutritionDao.addPlan(plan)
        goalDao.addGoal(goal)
        framewworkDao.addFramework(frameworks)
        dao.addCurrentUserGoal(currentGoal)
        val result: CurrentNutritionPlanAndFrameworkEntity = dao.getCurrentGoals()
        val currentFrameworkName: String = result.FrameWorkWIthGoalEntity.name
        val currentGoalName: String = result.FrameWorkWIthGoalEntity.goal
        MatcherAssert.assertThat(currentFrameworkName, CoreMatchers.equalTo("framework_4"))
        MatcherAssert.assertThat(currentGoalName, CoreMatchers.equalTo("Lose Weight"))

    }
}
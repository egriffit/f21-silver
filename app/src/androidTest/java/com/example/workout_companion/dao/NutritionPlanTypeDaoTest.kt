package com.example.workout_companion.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.GoalAndNutritionPlanTypeEntity
import com.example.workout_companion.entity.GoalTypeEntity
import com.example.workout_companion.entity.NutritionPlanTypeEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NutritionPlanTypeDaoTest: TestCase(){
    private lateinit var db: WCDatabase
    private lateinit var dao: NutritionPlanTypeDao
    private lateinit var goalDao: GoalTypeDao
    @Before
    public override fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.nutritionPlanTypeDao()
        goalDao = db.goalTypeDao()
    }

    @After
    fun closeDB(){
        db.close()
    }

    @Test
    fun testWriteAndReadNutritionPlanType() = runBlocking{
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        dao.addPlan(plan)
        val byId = dao.getById(1)
        MatcherAssert.assertThat(byId, CoreMatchers.equalTo(plan))
    }
    @Test
    fun testFindPlanId() = runBlocking{
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        val plan2 = NutritionPlanTypeEntity(2, 2, 2500.0, 0.45, .30, .25)

        dao.addPlan(plan)
        dao.addPlan(plan2)
        val planId: Int = dao.findPlanId(2500.0, 0.45, .30, .25)
        MatcherAssert.assertThat(planId, CoreMatchers.equalTo(2))
    }

    @Test
    fun testCount() = runBlocking{
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        val plan2 = NutritionPlanTypeEntity(2, 2, 2500.0, 0.45, .30, .25)

        dao.addPlan(plan)
        dao.addPlan(plan2)
        val count: Int = dao.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(2))
    }

    @Test
    fun testDelete() = runBlocking{
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)

        dao.addPlan(plan)
        dao.delete(plan)
        val count: Int = dao.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(0))
    }

    @Test
    fun testDeleteAll() = runBlocking{
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        val plan2 = NutritionPlanTypeEntity(2, 2, 2500.0, 0.45, .30, .25)

        dao.addPlan(plan)
        dao.addPlan(plan2)
        dao.deleteAll()
        val count: Int = dao.getCount()
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(0))
    }

    @Test
    fun testUpdateNutritionPlanType() = runBlocking{
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        val newPlan = NutritionPlanTypeEntity(1, 1, 2200.0, 0.40, .35, .25)

        dao.addPlan(plan)
        dao.update(newPlan)
        val byName = dao.getById(1)
        MatcherAssert.assertThat(byName.calorie, CoreMatchers.equalTo(2200.0))
    }

    @Test
    fun testGetNutritionPlansWithGoals() = runBlocking{
        val goal = listOf(
            GoalTypeEntity(1, "Lose Weight", -500),
            GoalTypeEntity(2, "Gain Mass", 500),
            GoalTypeEntity(3, "Gain Strength", 250),
        )
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        goalDao.addGoal(goal[0])
        goalDao.addGoal(goal[1])
        goalDao.addGoal(goal[2])
        dao.addPlan(plan)
        val nutritionPlans: List<GoalAndNutritionPlanTypeEntity> = dao.getNutritionPlansWithGoals()
        val goalName = nutritionPlans[0].goalType.goal
        MatcherAssert.assertThat(goalName, CoreMatchers.equalTo("Lose Weight"))
    }

    @Test
    fun testGetNutritionPlansByGoal() = runBlocking{
        val goal = listOf(
            GoalTypeEntity(1, "Lose Weight", -500),
            GoalTypeEntity(2, "Gain Mass", 500),
            GoalTypeEntity(3, "Gain Strength", 250),
        )
        val plans = listOf(
            NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(2, 2, 2200.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(3, 3, 2500.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(4, 1, 1900.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(5, 2, 2100.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(6, 2, 2300.0, 0.45, .30, .25),
        )


        goalDao.addGoal(goal[0])
        goalDao.addGoal(goal[1])
        goalDao.addGoal(goal[2])
        dao.addPlan(plans)
        val gainMassPlans: List<GoalAndNutritionPlanTypeEntity> = dao.getNutritionPlansByGoal("Gain Mass")
        MatcherAssert.assertThat(gainMassPlans.size, CoreMatchers.equalTo(3))
    }

    @Test
    fun testGetNutritionPlanCountByGoal() = runBlocking{
        val goal = listOf(
            GoalTypeEntity(1, "Lose Weight", -500),
            GoalTypeEntity(2, "Gain Mass", 500),
            GoalTypeEntity(3, "Gain Strength", 250),
        )
        val plans = listOf(
            NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(2, 2, 2200.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(3, 3, 2500.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(4, 1, 1900.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(5, 2, 2100.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(6, 2, 2300.0, 0.45, .30, .25),
        )


        goalDao.addGoal(goal[0])
        goalDao.addGoal(goal[1])
        goalDao.addGoal(goal[2])
        dao.addPlan(plans)
        val gainMassPlanCount = dao.getNutritionPlanCountByGoal("Gain Mass")
        MatcherAssert.assertThat(gainMassPlanCount, CoreMatchers.equalTo(3))
    }
}
package com.example.workout_companion.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.*
import com.example.workout_companion.utility.getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrentUserGoalDaoTest : TestCase() {
    private lateinit var db: WCDatabase
    private lateinit var dao: CurrentUserGoalDao
    private lateinit var nutritionDao: NutritionPlanTypeDao
    private lateinit var goalDao: GoalTypeDao
    private lateinit var frameworkDao: FrameworkTypeDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.currentUserDao()
        goalDao = db.goalTypeDao()
        nutritionDao = db.nutritionPlanTypeDao()
        frameworkDao = db.frameworkTypeDao()


    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun testGetCurrentGoals() = runBlocking {
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        val goal = listOf(
            GoalTypeEntity(1, "Lose Weight", -500),
            GoalTypeEntity(2, "Gain Mass", 500),
            GoalTypeEntity(3, "Gain Strength", 250),
        )
        val frameworks = listOf(
            FrameworkTypeEntity(1, "framework_1", 1, 1),
            FrameworkTypeEntity(2, "framework_2", 2, 1),
            FrameworkTypeEntity(3, "framework_3", 3, 1),
            FrameworkTypeEntity(4, "framework_4", 1, 2),
            FrameworkTypeEntity(5, "framework_5", 2, 2),
            FrameworkTypeEntity(6, "framework_6", 2, 3),
            FrameworkTypeEntity(7, "framework_7", 2, 1),
        )
        val currentGoal = CurrentUserGoalEntity(1,1, 4)
        nutritionDao.addPlan(plan)
        for (singleGoal in goal) {
            goalDao.addGoal(singleGoal)
        }
        frameworkDao.addFrameworks(frameworks)
        dao.addCurrentUserGoal(currentGoal)
        val result: CurrentNutritionPlanAndFrameworkEntity =
            dao.getCurrentGoals().getOrAwaitValue()
        val currentFrameworkName: String? = result?.FrameWorkWithGoalEntity?.name
        val currentGoalName: String? = result?.FrameWorkWithGoalEntity?.goal
        MatcherAssert.assertThat(currentFrameworkName, CoreMatchers.equalTo("framework_4"))
        MatcherAssert.assertThat(currentGoalName, CoreMatchers.equalTo("Lose Weight"))
    }

    @Test
    fun getCurrentGoalIds() = runBlocking {
        val plan = NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25)
        val goal = listOf(
            GoalTypeEntity(1, "Lose Weight", -500),
            GoalTypeEntity(2, "Gain Mass", 500),
            GoalTypeEntity(3, "Gain Strength", 250),
        )
        val frameworks = listOf(
            FrameworkTypeEntity(1, "framework_1", 1, 1),
            FrameworkTypeEntity(2, "framework_2", 2, 1),
            FrameworkTypeEntity(3, "framework_3", 3, 1),
            FrameworkTypeEntity(4, "framework_4", 1, 2),
            FrameworkTypeEntity(5, "framework_5", 2, 2),
            FrameworkTypeEntity(6, "framework_6", 2, 3),
            FrameworkTypeEntity(7, "framework_7", 2, 1),
        )
        val currentGoal = CurrentUserGoalEntity(1,1, 4)
        nutritionDao.addPlan(plan)
        for (singleGoal in goal) {
            goalDao.addGoal(singleGoal)
        }
        frameworkDao.addFrameworks(frameworks)
        dao.addCurrentUserGoal(currentGoal)
        val result: CurrentUserGoalEntity = dao.getCurrentGoalIds().getOrAwaitValue()
        val currentNutritionPlanId: Int? = result?.nutrition_plan_type_id
        val currentFrameworkId: Int? = result?.framework_type_id
        assertEquals(currentGoal.nutrition_plan_type_id, currentNutritionPlanId)
        assertEquals(currentGoal.framework_type_id, currentFrameworkId)
    }

    @Test
    fun testCurrentGoalExists() = runBlocking {

        val currentGoal = CurrentUserGoalEntity(1, 1, 4)
        dao.addCurrentUserGoal(currentGoal)
        val exists: Boolean? = dao.currentGoalExists()
        if (exists != null) {
            assertTrue(exists)
        }
    }

    @Test
    fun testUpdateNutritionPlanID() = runBlocking {
        val goal = CurrentUserGoalEntity(1,1, 4)
        dao.addCurrentUserGoal(goal)
        dao.updateNutritionPlanID(3)
        val result: CurrentUserGoalEntity = dao.getCurrentGoalIds().getOrAwaitValue()
        val currentNutritionPlanId: Int? = result?.nutrition_plan_type_id
        assertEquals(3, currentNutritionPlanId)
        Assert.assertNotEquals(goal.nutrition_plan_type_id, currentNutritionPlanId)
    }

    @Test
    fun testUpdateFrameworkTypeID() = runBlocking {
        val goal = CurrentUserGoalEntity(1,1, 4)
        dao.addCurrentUserGoal(goal)
        dao.updateFrameworkTypeID(3)
        val result: CurrentUserGoalEntity = dao.getCurrentGoalIds().getOrAwaitValue()
        val currentFrameworkId: Int? = result?.framework_type_id
        assertEquals(3, currentFrameworkId)
        Assert.assertNotEquals(goal.framework_type_id, currentFrameworkId)
    }

    @Test
    fun testUpdateNutritionPlan() = runBlocking {
        val plans = listOf(
            NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(2, 2, 2500.0, 0.40, .35, .25)
        )

        val goals = listOf(
            GoalTypeEntity(1, "Lose Weight", -500),
            GoalTypeEntity(2, "Gain Mass", 500),
            GoalTypeEntity(3, "Gain Strength", 250),
        )
        val frameworks = listOf(
            FrameworkTypeEntity(1, "framework_1", 1, 1),
            FrameworkTypeEntity(2, "framework_2", 2, 1),
            FrameworkTypeEntity(3, "framework_3", 3, 1),
            FrameworkTypeEntity(4, "framework_4", 1, 2),
            FrameworkTypeEntity(5, "framework_5", 2, 2),
            FrameworkTypeEntity(6, "framework_6", 2, 3),
            FrameworkTypeEntity(7, "framework_7", 2, 1),
        )
        val currentGoal = CurrentUserGoalEntity(1, 1, 4)
        nutritionDao.addPlan(plans)
        for (singleGoal in goals) {
            goalDao.addGoal(singleGoal)
        }
        frameworkDao.addFrameworks(frameworks)
        dao.addCurrentUserGoal(currentGoal)
        dao.updateNutritionPlan(2500.0, 0.40, .35, .25)

        val result: CurrentUserGoalEntity = dao.getCurrentGoalIds().getOrAwaitValue()
        val currentNutritionPlanId: Int? = result?.nutrition_plan_type_id
        assertEquals(2, currentNutritionPlanId)
        Assert.assertNotEquals(1, currentNutritionPlanId)
    }

    @Test
    fun testUpdateFrameworkType() = runBlocking {
        val plans = listOf(
            NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(2, 2, 2500.0, 0.40, .35, .25)
        )

        val goals = listOf(
            GoalTypeEntity(1, "Lose Weight", -500),
            GoalTypeEntity(2, "Gain Mass", 500),
            GoalTypeEntity(3, "Gain Strength", 250),
        )
        val frameworks = listOf(
            FrameworkTypeEntity(1, "framework_1", 1, 1),
            FrameworkTypeEntity(2, "framework_2", 2, 1),
            FrameworkTypeEntity(3, "framework_3", 3, 1),
            FrameworkTypeEntity(4, "framework_4", 1, 2),
            FrameworkTypeEntity(5, "framework_5", 2, 2),
            FrameworkTypeEntity(6, "framework_6", 2, 3),
            FrameworkTypeEntity(7, "framework_7", 2, 1),
        )
        val currentGoal = CurrentUserGoalEntity(1, 1, 4)
        nutritionDao.addPlan(plans)
        for (singleGoal in goals) {
            goalDao.addGoal(singleGoal)
        }
        frameworkDao.addFrameworks(frameworks)
        dao.addCurrentUserGoal(currentGoal)
        dao.updateFrameworkType("framework_7")

        val result: CurrentUserGoalEntity = dao.getCurrentGoalIds().getOrAwaitValue()
        val current_nutrition_plan_Id: Int? = result?.nutrition_plan_type_id
        val current_framework_id: Int? = result?.framework_type_id
        assertEquals(7, current_framework_id)
        Assert.assertNotEquals(4, current_nutrition_plan_Id)
    }

    @Test
    fun testUpdateFrameworkAndNutritionPlanType() = runBlocking {
        val plans = listOf(
            NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(2, 2, 2500.0, 0.40, .35, .25)
        )

        val goals = listOf(
            GoalTypeEntity(1, "Lose Weight", -500),
            GoalTypeEntity(2, "Gain Mass", 500),
            GoalTypeEntity(3, "Gain Strength", 250),
        )
        val frameworks = listOf(
            FrameworkTypeEntity(1, "framework_1", 1, 1),
            FrameworkTypeEntity(2, "framework_2", 2, 1),
            FrameworkTypeEntity(3, "framework_3", 3, 1),
            FrameworkTypeEntity(4, "framework_4", 1, 2),
            FrameworkTypeEntity(5, "framework_5", 2, 2),
            FrameworkTypeEntity(6, "framework_6", 2, 3),
            FrameworkTypeEntity(7, "framework_7", 2, 1),
        )
        val currentGoal = CurrentUserGoalEntity(1, 1, 4)
        nutritionDao.addPlan(plans)
        for (singleGoal in goals) {
            goalDao.addGoal(singleGoal)
        }
        frameworkDao.addFrameworks(frameworks)
        dao.addCurrentUserGoal(currentGoal)
        dao.updateNutritionPlanAndFramework("framework_7", 2500.0, 0.40, .35, .25)

        val result: CurrentUserGoalEntity = dao.getCurrentGoalIds().getOrAwaitValue()
        val current_nutrition_plan_Id: Int? = result?.nutrition_plan_type_id
        val current_framework_id: Int? = result?.framework_type_id
        assertEquals(7, current_framework_id)
        assertEquals(2, current_nutrition_plan_Id)
        Assert.assertNotEquals(4, current_framework_id)
        Assert.assertNotEquals(1, current_nutrition_plan_Id)
    }

    @Test
    fun testUpdateFrameworkAndNutritionPlanTypeID() = runBlocking {
        val currentGoal = CurrentUserGoalEntity(1, 1, 4)
        dao.addCurrentUserGoal(currentGoal)
        dao.updateNutritionPlanAndFrameworkID(7, 2)

        val result: CurrentUserGoalEntity = dao.getCurrentGoalIds().getOrAwaitValue()
        val current_nutrition_plan_Id: Int? = result?.nutrition_plan_type_id
        val current_framework_id: Int? = result?.framework_type_id
        assertEquals(7, current_framework_id)
        assertEquals(2, current_nutrition_plan_Id)
        Assert.assertNotEquals(4, current_framework_id)
        Assert.assertNotEquals(1, current_nutrition_plan_Id)
    }
}
package com.example.workout_companion.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.CurrentUserGoalDao
import com.example.workout_companion.dao.FrameworkTypeDao
import com.example.workout_companion.dao.GoalTypeDao
import com.example.workout_companion.dao.NutritionPlanTypeDao
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.*
import com.example.workout_companion.utility.getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.*
import org.junit.Assert.*

import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CurrentUserGoalRepositoryTest: TestCase(){
    private lateinit var db: WCDatabase
    private lateinit var dao: CurrentUserGoalDao
    private lateinit var nutritionDao: NutritionPlanTypeDao
    private lateinit var goalDao: GoalTypeDao
    private lateinit var frameworkDao: FrameworkTypeDao
    private lateinit var repository: CurrentUserGoalRepository


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
        repository = CurrentUserGoalRepository(dao)
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun getCurrentGoalIds() = runBlocking{
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
        repository.addCurrentUserGoals(currentGoal)
        val result: CurrentUserGoalEntity = repository.getCurrentGoalIds.getOrAwaitValue()
        val currentNutritionPlanId: Int = result.nutrition_plan_type_id
        val currentFrameWorkId: Int = result.framework_type_id
        assertEquals(currentGoal.nutrition_plan_type_id, currentNutritionPlanId)
        assertEquals(currentGoal.framework_type_id, currentFrameWorkId)
    }

    @Test
    fun getCurrentUserGoals() = runBlocking{
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
        repository.addCurrentUserGoals(currentGoal)
        val result: CurrentNutritionPlanAndFrameworkEntity =
            repository.getCurrentUserGoals.getOrAwaitValue()
        val currentFrameworkName: String = result.FrameWorkWIthGoalEntity.name
        val currentGoalName: String = result.FrameWorkWIthGoalEntity.goal
        MatcherAssert.assertThat(currentFrameworkName, CoreMatchers.equalTo("framework_4"))
        MatcherAssert.assertThat(currentGoalName, CoreMatchers.equalTo("Lose Weight"))
    }

    @Test
    fun currentGoalExists() = runBlocking{
        val currentGoal = CurrentUserGoalEntity(1, 1, 4)
        repository.addCurrentUserGoals(currentGoal)
        assertTrue(repository.currentGoalExists())
    }

    @Test
    fun addCurrentUserGoals() = runBlocking{
        val currentGoal = CurrentUserGoalEntity(1, 1, 4)
        repository.addCurrentUserGoals(currentGoal)
        assertTrue(repository.currentGoalExists())
    }

    @Test
    fun updateNutritionPlan() = runBlocking{
        val plans = listOf(NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(2, 2, 2500.0, 0.40, .35, .25))

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
        repository.addCurrentUserGoals(currentGoal)
        repository.updateNutritionPlan(2500.0, 0.40, .35, .25)

        val result: CurrentUserGoalEntity = repository.getCurrentGoalIds.getOrAwaitValue()
        val currentNutritionPlanId: Int = result.nutrition_plan_type_id
        assertEquals(2, currentNutritionPlanId)
        assertNotEquals(1, currentNutritionPlanId)
    }

    @Test
    fun updateNutritionPlanID() = runBlocking{
        val goal = CurrentUserGoalEntity(1,1, 4)
        repository.addCurrentUserGoals(goal)
        repository.updateNutritionPlanID(3)
        val result: CurrentUserGoalEntity = repository.getCurrentGoalIds.getOrAwaitValue()
        val currentNutritionPlanId: Int = result.nutrition_plan_type_id
        assertEquals(3, currentNutritionPlanId)
        assertNotEquals(goal.nutrition_plan_type_id, currentNutritionPlanId)
    }

    @Test
    fun updateFrameworkType() = runBlocking{
        val plans = listOf(NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(2, 2, 2500.0, 0.40, .35, .25))

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
        repository.addCurrentUserGoals(currentGoal)
        repository.updateFrameworkType("framework_7")

        val result: CurrentUserGoalEntity = repository.getCurrentGoalIds.getOrAwaitValue()
        val currentNutritionPlanId: Int = result.nutrition_plan_type_id
        val currentFrameworkId: Int = result.framework_type_id
        assertEquals(7, currentFrameworkId)
        assertNotEquals(4, currentNutritionPlanId)
    }

    @Test
    fun updateFrameworkTypeID() = runBlocking{
        val goal = CurrentUserGoalEntity(1,1, 4)
        repository.addCurrentUserGoals(goal)
        repository.updateFrameworkTypeID(3)
        val result: CurrentUserGoalEntity = repository.getCurrentGoalIds.getOrAwaitValue()
        val currentFrameworkId: Int = result.framework_type_id
        assertEquals(3, currentFrameworkId)
        assertNotEquals(goal.framework_type_id, currentFrameworkId)
    }

    @Test
    fun updateNutritionPlanAndFramework() = runBlocking{
        val plans = listOf(NutritionPlanTypeEntity(1, 1, 2000.0, 0.45, .30, .25),
            NutritionPlanTypeEntity(2, 2, 2500.0, 0.40, .35, .25))

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
        repository.addCurrentUserGoals(currentGoal)
        repository.updateNutritionPlanAndFramework("framework_7", 2500.0, 0.40, .35, .25)

        val result: CurrentUserGoalEntity = repository.getCurrentGoalIds.getOrAwaitValue()
        val currentNutritionPlanId: Int = result.nutrition_plan_type_id
        val currentFrameworkId: Int = result.framework_type_id
        assertEquals(7, currentFrameworkId)
        assertEquals(2, currentNutritionPlanId)
        assertNotEquals(4, currentFrameworkId)
        assertNotEquals(1, currentNutritionPlanId)
    }

    @Test
    fun updateNutritionPlanAndFrameworkID() = runBlocking{
        val currentGoal = CurrentUserGoalEntity(1, 1, 4)
        repository.addCurrentUserGoals(currentGoal)
        repository.updateNutritionPlanAndFrameworkID(7, 2)

        val result: CurrentUserGoalEntity = repository.getCurrentGoalIds.getOrAwaitValue()
        val currentNutritionPlanId: Int = result.nutrition_plan_type_id
        val currentFrameworkId: Int = result.framework_type_id
        assertEquals(7, currentFrameworkId)
        assertEquals(2, currentNutritionPlanId)
        assertNotEquals(4, currentFrameworkId)
        assertNotEquals(1, currentNutritionPlanId)
    }
}
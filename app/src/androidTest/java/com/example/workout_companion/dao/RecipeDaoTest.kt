package com.example.workout_companion.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.entity.RecipeEntity
import com.example.workout_companion.sampleData.sampleRecipeList
import com.example.workout_companion.sampleData.sampleUpdatedRecipe
import com.example.workout_companion.utility.getOrAwaitValue
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
class RecipeDaoTest : TestCase(){

    private lateinit var db: WCDatabase
    private lateinit var dao: RecipeDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.recipeDao()
    }

    @After
    fun closeDB(){
        db.close()
    }

    @Test
    fun testGetRecipes() = runBlocking{

        dao.insert(sampleRecipeList)
        val found: List<RecipeEntity> = dao.getRecipes().getOrAwaitValue()
        MatcherAssert.assertThat(found, CoreMatchers.equalTo(sampleRecipeList))
    }

    @Test
    fun testGetByName() = runBlocking{
        dao.insert(sampleRecipeList)

        val found: List<RecipeEntity> = dao.getRecipe("Grilled Cheese")
        MatcherAssert.assertThat(found.elementAt(0), CoreMatchers.equalTo(sampleRecipeList.elementAt(0),))
    }

    @Test
    fun testGetCount() = runBlocking{
        dao.insert(sampleRecipeList)

        val recipeCount: Int = dao.getCount()
        MatcherAssert.assertThat(recipeCount, CoreMatchers.equalTo(2))
    }

    @Test
    fun testGetCountByName() = runBlocking{
        dao.insert(sampleRecipeList)

        val recipeCount: Int = dao.getCountByName("Grilled Cheese")
        MatcherAssert.assertThat(recipeCount, CoreMatchers.equalTo(1))
    }

    fun testGetRecipeID() = runBlocking{
        dao.insert(sampleRecipeList)

        val recipeCount: Int = dao.getRecipeId("Grilled Cheese")
        val recipeCount2: Int = dao.getRecipeId("Ham and Cheese Omelet")

        MatcherAssert.assertThat(recipeCount, CoreMatchers.equalTo(1))
        MatcherAssert.assertThat(recipeCount2, CoreMatchers.equalTo(2))
    }

    @Test
    fun testUpdate() = runBlocking{
        dao.insert(sampleRecipeList)

        dao.update(sampleUpdatedRecipe)
        val found: Int = dao.getCountByName("Grilled Cheese")
        val found2: Int = dao.getCountByName("Cheeseburger")
        MatcherAssert.assertThat(found, CoreMatchers.equalTo(0))
        MatcherAssert.assertThat(found2, CoreMatchers.equalTo(1))
    }

    @Test
    fun testDelete() = runBlocking{
        dao.insert(sampleRecipeList)

        dao.delete(sampleRecipeList.elementAt(1))
        val recipeCount: Int = dao.getCount()
        MatcherAssert.assertThat(recipeCount, CoreMatchers.equalTo(1))
    }

    @Test
    fun testDeleteAll() = runBlocking{
        dao.insert(sampleRecipeList)
        dao.deleteAll()
        val recipeCount: Int = dao.getCount()
        MatcherAssert.assertThat(recipeCount, CoreMatchers.equalTo(0))
    }
}
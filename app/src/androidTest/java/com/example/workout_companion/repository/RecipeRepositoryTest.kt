package com.example.workout_companion.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workout_companion.dao.RecipeDao
import com.example.workout_companion.database.WCDatabase
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

@RunWith(AndroidJUnit4::class)
class RecipeRepositoryTest : TestCase() {
    private lateinit var db: WCDatabase
    private lateinit var dao: RecipeDao
    private lateinit var repository: RecipeRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WCDatabase::class.java).build()
        dao = db.recipeDao()
        repository = RecipeRepository(dao)
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun testGetRecipes() = runBlocking {
        repository.insert(sampleRecipeList)
        val found: List<RecipeEntity> = repository.getAllRecipes.getOrAwaitValue()
        MatcherAssert.assertThat(found, CoreMatchers.equalTo(sampleRecipeList))
    }

    @Test
    fun testGetByName() = runBlocking{
        repository.insert(sampleRecipeList)

        val found: List<RecipeEntity> = repository.getRecipeByName("Grilled Cheese")
        MatcherAssert.assertThat(found.elementAt(0), CoreMatchers.equalTo(sampleRecipeList.elementAt(0)))
    }

    @Test
    fun testGetCount() = runBlocking{
        repository.insert(sampleRecipeList)

        val recipeCount: Int = repository.getCount()
        MatcherAssert.assertThat(recipeCount, CoreMatchers.equalTo(2))
    }

    @Test
    fun testGetCountByName() = runBlocking{
        repository.insert(sampleRecipeList)

        val recipeCount: Int = repository.getCount("Grilled Cheese")
        MatcherAssert.assertThat(recipeCount, CoreMatchers.equalTo(1))
    }

    fun testGetRecipeID() = runBlocking{
        repository.insert(sampleRecipeList)

        val recipeCount: Int = repository.getRecipeId("Grilled Cheese")
        val recipeCount2: Int = repository.getRecipeId("Ham and Cheese Omelet")

        MatcherAssert.assertThat(recipeCount, CoreMatchers.equalTo(1))
        MatcherAssert.assertThat(recipeCount2, CoreMatchers.equalTo(2))
    }

    @Test
    fun testUpdate() = runBlocking{
        repository.insert(sampleRecipeList)

        repository.update(sampleUpdatedRecipe)
        val found: Int = repository.getCount("Grilled Cheese")
        val found2: Int = repository.getCount("Cheeseburger")
        MatcherAssert.assertThat(found, CoreMatchers.equalTo(0))
        MatcherAssert.assertThat(found2, CoreMatchers.equalTo(1))
    }

    @Test
    fun testDelete() = runBlocking{
        repository.insert(sampleRecipeList)

        repository.delete(sampleRecipeList.elementAt(1))
        val recipeCount: Int = repository.getCount()
        MatcherAssert.assertThat(recipeCount, CoreMatchers.equalTo(1))
    }

    @Test
    fun testDeleteAll() = runBlocking{
        repository.insert(sampleRecipeList)
        repository.deleteAll()
        val recipeCount: Int = repository.getCount()
        MatcherAssert.assertThat(recipeCount, CoreMatchers.equalTo(0))
    }
}
package com.example.workout_companion.database

import android.content.Context
import androidx.room.*
import com.example.workout_companion.dao.*
import com.example.workout_companion.entity.*
import com.example.workout_companion.utility.DateTimeConverter

/**
 * Class used to instantiate the workout_companion_database Database
 */
@Database(entities = [
    UserEntity::class,
    NutritionPlanTypeEntity::class,
    GoalTypeEntity::class,
    CurrentUserGoalEntity::class,
    FrameworkTypeEntity::class,
    FrameworkDayEntity::class,
    FrameworkComponentEntity::class,
    FoodTypeEntity::class,
    MealEntity::class,
    FoodInMealEntity::class,
    WorkoutEntity::class,
    WorkoutComponentEntity::class,
    WorkoutComponentSetEntity::class,
    RecipeEntity::class,
    FoodInRecipeEntity::class,
    //Add all entities to this list
],
    views = [FrameworkWithGoalEntity::class],
version = 29,
exportSchema = false)
@TypeConverters(DateTimeConverter::class)
abstract class WCDatabase: RoomDatabase() {
    /**
     * Functions to retrieve the Data Abstraction objects for the entities in the database
     */
    abstract fun userDao(): UserDao
    abstract fun nutritionPlanTypeDao(): NutritionPlanTypeDao
    abstract fun goalTypeDao(): GoalTypeDao
    abstract fun currentUserDao(): CurrentUserGoalDao
    abstract fun frameworkTypeDao(): FrameworkTypeDao
    abstract fun frameworkDayDao(): FrameworkDayDao
    abstract fun frameworkComponentDao(): FrameworkComponentDao
    abstract fun completeFrameworkDao(): CompleteFrameworkDao
    abstract fun foodTypeDao(): FoodTypeDao
    abstract fun mealDao(): MealDao
    abstract fun foodInMealDao(): FoodInMealDao
    abstract fun userWithGoalDao(): UserWithGoalDao
    abstract fun recipeDao(): RecipeDao
    abstract fun foodInRecipeDao(): FoodInRecipeDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutComponentDao(): WorkoutComponentDao
    abstract fun workoutComponentSetDao(): WorkoutComponentSetDao


    companion object{
        @Volatile
        private var INSTANCE: WCDatabase? = null

        /**
         * Code to instantiate and retrieve the instance of the database
         * @param context, the context of the application using the database
         * @return WCDatabase object
         */
        fun getInstance(context: Context): WCDatabase{
            val sampleInstance = INSTANCE
            if(sampleInstance != null){
                return sampleInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WCDatabase::class.java,
                    "workout_companion_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }

}
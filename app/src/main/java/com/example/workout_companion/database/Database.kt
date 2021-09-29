package com.example.workout_companion.database

import android.content.Context
import androidx.room.*
import com.example.workout_companion.dao.NutritionPlanTypeDao
import com.example.workout_companion.dao.UserDao
import com.example.workout_companion.entity.NutritionPlanTypeEntity
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.utility.DateTimeConverter

/**
 * Class used to instantiate the workout_companion_database Database
 */
@Database(entities = [
    UserEntity::class,
    NutritionPlanTypeEntity::class
    //Add all entities to this list
],
version = 1,
exportSchema = false)
@TypeConverters(DateTimeConverter::class)
abstract class WCDatabase: RoomDatabase() {
    /**
     * Functions to retrieve the Data Abstraction objects for the entities in the database
     */
    abstract fun userDao(): UserDao
    abstract fun nutritionPlanTypeDao(): NutritionPlanTypeDao


    companion object{
        @Volatile
        private var INSTANCE: WCDatabase? = null

        /**
         * Code to instantiate and retrieve the instance of the database
         * @param Context, the context of the application using the database
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
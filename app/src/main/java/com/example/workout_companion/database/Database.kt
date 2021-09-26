package com.example.workout_companion.database

import android.content.Context
import androidx.room.*
import com.example.workout_companion.dao.GoalTypeDao
import com.example.workout_companion.dao.UserDao
import com.example.workout_companion.entity.GoalTypeEntity
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.utility.DateTimeConverter

@Database(entities = [
    UserEntity::class,
    GoalTypeEntity::class
    //Add all entities to this list
],
version = 1,
exportSchema = false)
@TypeConverters(DateTimeConverter::class)
abstract class WCDatabase: RoomDatabase() {
    //Insert all DAOs here
    abstract fun userDao(): UserDao
    abstract fun goalTypeDao(): GoalTypeDao

    companion object{
        @Volatile
        private var INSTANCE: WCDatabase? = null

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
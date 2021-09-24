package com.example.workout_companion.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workout_companion.dao.UserDao
import com.example.workout_companion.entity.UserEntity

@Database(entities = [
    UserEntity::class
    //Add all entities to this list
],
version = 1,
exportSchema = false)
abstract class WCDatabase: RoomDatabase() {
    //Insert all DAOs here
    abstract fun userDao(): UserDao

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
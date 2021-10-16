package com.example.workout_companion.api.edamam.repository

import com.example.workout_companion.api.edamam.EdamamAPI
import com.example.workout_companion.api.edamam.Properties
import com.example.workout_companion.api.edamam.entities.EdamamFood
import com.example.workout_companion.api.utility.Resource
import javax.inject.Inject

class EdamamRepository @Inject constructor(
    private val api: EdamamAPI
) {
    suspend fun getFood(food: String): Resource<EdamamFood> {
        val response = try{
            api.getEdamamByFood(Properties.app_id, Properties.app_key, food)
        }catch(e: Exception){
            return Resource.Error("An error occurred")
        }
        return Resource.Success(response)
    }
}
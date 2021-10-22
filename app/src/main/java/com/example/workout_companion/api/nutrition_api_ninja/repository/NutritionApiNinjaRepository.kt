package com.example.workout_companion.api.nutrition_api_ninja.repository

import com.example.workout_companion.api.nutrition_api_ninja.NutritionApiNinjaApi
import com.example.workout_companion.api.nutrition_api_ninja.Properties
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
import com.example.workout_companion.api.utility.Resource
import javax.inject.Inject

class NutritionApiNinjaRepository @Inject constructor(
private val api: NutritionApiNinjaApi

) {
    suspend fun getFood(food: String): Resource<ApiNinjaNutrition> {
        val response = try{
            api.getFood(Properties.api_key, food)
        }catch(e: Exception){
            return Resource.Error("An error occurred")
        }
        return Resource.Success(response)
    }
}
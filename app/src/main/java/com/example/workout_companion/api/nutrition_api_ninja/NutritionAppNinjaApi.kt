package com.example.workout_companion.api.nutrition_api_ninja

import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

fun nutritionApiNinjaApi(baseUrl: String): NutritionApiNinjaApi {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()
        .create(NutritionApiNinjaApi::class.java)
}

interface NutritionApiNinjaApi {
    @GET("nutrition")
    suspend fun getFood(
        @Header("X-Api-Key") apiKey: String,
        @Query("query") food: String,
    ): ApiNinjaNutrition
}
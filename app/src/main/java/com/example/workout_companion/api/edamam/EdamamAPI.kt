package com.example.workout_companion.api.edamam

import com.example.workout_companion.api.edamam.entities.EdamamFood
import retrofit2.http.GET
import retrofit2.http.Query

interface EdamamAPI {
    @GET("parser")
    suspend fun getEdamamByFood(
        @Query("app_id") app_id: String,
        @Query("ing") food: String,
        @Query("app_key") app_key: String
    ): EdamamFood
}
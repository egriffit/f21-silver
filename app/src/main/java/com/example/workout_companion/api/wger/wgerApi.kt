package com.example.workout_companion.api.wger

import com.example.workout_companion.api.wger.Properties.base_url
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
import com.example.workout_companion.api.wger.entities.ExerciseInfo
import com.example.workout_companion.api.wger.entities.wgerExercise
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

fun wgerApi(): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(base_url)
        .build()
}

interface WgerApi {
    @GET("exercise")
    suspend fun getExerciseByMuscle(
        @Query("language") language: Int,
        @Query("muscles") muscles: Int,
    ): wgerExercise

    @GET("exerciseinfo/{id}")
    suspend fun getExerciseById(
        @Path("id") id: Int,
        @Query("language") language: Int,
    ): ExerciseInfo

}
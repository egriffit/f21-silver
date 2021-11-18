package com.example.workout_companion.api.adviceAPI

import com.example.workout_companion.api.adviceAPI.Properties.base_url
import com.example.workout_companion.api.adviceAPI.entity.advice
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

fun adviceApi(): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(base_url)
        .build()
}

interface AdviceApi {
    @GET("getAdvice")
    suspend fun getAdviceByType(
        @Query("adviceType") adviceType: String,
    ): advice
}
package com.example.workout_companion.api.edamam

import com.example.workout_companion.api.edamam.entities.EdamamFood
import com.example.workout_companion.api.edamam.entities.Food
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


fun edamamApi(baseUrl: String): EdamamAPI {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()
        .create(EdamamAPI::class.java)
}

interface EdamamAPI {
    @GET("parser")
    suspend fun getEdamamByFood(
        @Query("app_id") app_id: String,
        @Query("app_key") app_key: String,
        @Query("ingr") food: String,
        ): EdamamFood

    @GET("parser")
    suspend fun getNextEdamamByFood(
        @Query("app_id") app_id: String,
        @Query("app_key") app_key: String,
        @Query("ingr") food: String,
        @Query("session") session: Int,

        ): EdamamFood

}
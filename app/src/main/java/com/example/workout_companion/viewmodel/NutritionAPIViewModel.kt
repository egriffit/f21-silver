package com.example.workout_companion.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout_companion.api.nutrition_api_ninja.NutritionApiNinjaApi
import com.example.workout_companion.api.nutrition_api_ninja.Properties.api_key
import com.example.workout_companion.api.nutrition_api_ninja.Properties.base_url
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
import com.example.workout_companion.api.nutrition_api_ninja.nutritionApiNinjaApi
import com.example.workout_companion.api.utility.FoodData
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NutritionAPIViewModel: ViewModel() {
    val foodResults = mutableStateListOf<ApiNinjaNutrition>()

    fun findFood(food: String) {
        viewModelScope.launch {
            val call = nutritionApiNinjaApi()
                .create(NutritionApiNinjaApi::class.java)


            val response = try {
                val foods = call.getFood(api_key, food)
                if(foods.size > 0){
                    foodResults.clear()
                    foodResults.add(foods)
                }else{
                    foodResults.clear()
                }
            } catch (e: Exception) {
                foodResults.clear()
            }
        }

    }

    fun getFoodsFromResponse(response: ApiNinjaNutrition?): List<FoodData> {
        var name: String = ""
        var calories: Int = 0
        var carbohydrates: Double = 0.0
        var protein: Double = 0.0
        var fat: Double = 0.0
        var foodId: String = ""
        var servings = mutableMapOf<String, Double>()
        var foodList: MutableList<FoodData> = mutableListOf()
        response?.forEach {
            name = it.name
            calories = it.calories.toInt()
            carbohydrates = it.carbohydrates_total_g
            protein = it.protein_g
            fat = it.fat_total_g
            servings.put("gram", it.serving_size_g)
            foodList.add(
                FoodData(
                    name,
                    foodId,
                    calories,
                    carbohydrates,
                    protein,
                    fat,
                    servings
                )
            )
        }
        return foodList
    }
}
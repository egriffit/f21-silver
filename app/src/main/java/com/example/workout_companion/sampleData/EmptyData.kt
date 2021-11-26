package com.example.workout_companion.sampleData

import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutritionItem
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.entity.RecipeEntity
import com.example.workout_companion.entity.MealEntity
import java.time.LocalDate

/* EmptyData.kt
A file to store uninitialized versions of data classes rather than convert them to
* a class with only a default constructor
*/
var emptyRecipeEntity = RecipeEntity(-1, "")
var emptyNutritionAPiItem =  ApiNinjaNutritionItem(
    0.0, 0.0,
    0, 0.0, 0.0, 0.0,
    "", 0, 0.0, 0.0,
    0, 0.0)
var emptyFoodTypeEntity = FoodTypeEntity(
    -1, "", "-1", 0.0, 0.0,
    0.0, 0.0, 0.0)

var emptyMealEntity = MealEntity(0, "", 0.0, 0.0, 0.0, 0.0, LocalDate.now())
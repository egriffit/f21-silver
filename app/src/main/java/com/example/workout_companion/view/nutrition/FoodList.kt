package com.example.workout_companion.view.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import com.example.workout_companion.api.nutrition_api_ninja.NutritionAPIViewModel
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import java.time.LocalDate
/***
 * Composable to display a column of foods in a meal
 * It consists of:
 * a column of foods names
 * a foodSearchBox
 *
 * @param foundINMealViewModel, a view model to work with the the food_in_meal table
 * @param meal, a string
 * @param open, a mutableStateBoolean to check if foods list is visible
 * @param apiNinjaViewModel, a view model to work with the NutritionAPI by API Ninja
 *
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FoodList(foodInMealViewModel: FoodInMealViewModel, meal: String, open: MutableState<Boolean>,
             nutritionAPIViewModel: NutritionAPIViewModel
)
{
    val today = LocalDate.now()
    var foundFoods =  foodInMealViewModel.getFoodsInMeal(meal, today).observeAsState(listOf()).value
    if(open.value){
        Column(){
            Text("Foods")
            //display current foods in meal
            for (food in foundFoods) {
                Row {
                    Text("${food.name} - Serving Size: ${food.serving_size} Carbohydrates: ${food.carbohydrates}g  Protein: ${food.protein}g Fat: ${food.fat}")
                }
            }
        }
        //search box to add food
        foodSearchBox(foodInMealViewModel, nutritionAPIViewModel)
    }
}
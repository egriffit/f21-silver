package com.example.workout_companion.view.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.viewmodel.NutritionAPIViewModel
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.FoodTypeViewModel
import com.example.workout_companion.viewmodel.MealViewModel

/***
 * Composable to display meals for the current day and a button to create a meal
 *
 * It consists of:
 * a lazy column of meal buttons || a text label advising no foods found
 *
 * @param navController, navController
 * @param meals, a list of MealEntity objects
 * @param mealViewModel, a view model to work with the meal table
 * @param foodInMealViewModel, a view model to work with the the food_in_meal table
 * @param nutritionAPIViewModel, a view model to work with the NutritionAPI by API Ninja
 *
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MealList(navController: NavController, meals: List<MealEntity>, foodTypeViewModel: FoodTypeViewModel,
             mealViewModel: MealViewModel, foodInMealViewModel: FoodInMealViewModel,
             nutritionAPIViewModel: NutritionAPIViewModel
){
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center){
        Text("Today's Meals")
    }

    LazyColumn(
        Modifier
            .background(Color(0xFFEDEAE0))
            .fillMaxWidth()
            .fillMaxHeight(0.4F)
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (meals.isNotEmpty()) {
            for (meal in meals)
                item {
                    Row(horizontalArrangement =  Arrangement.Center) {
                        MealButton(navController, meal.type, meal.calories, foodTypeViewModel, mealViewModel, foodInMealViewModel,
                            nutritionAPIViewModel)                    }
                }
        }else{
            item{
                Row(horizontalArrangement =  Arrangement.Center){
                    Text("No Meals found")
                }
            }
        }
    }
}
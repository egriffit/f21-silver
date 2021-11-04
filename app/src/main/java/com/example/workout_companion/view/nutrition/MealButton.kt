package com.example.workout_companion.view.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workout_companion.viewmodel.NutritionAPIViewModel
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.FoodTypeViewModel
import com.example.workout_companion.viewmodel.MealViewModel

/***
 * Composable to show and hide foods in a meal using a +/- button
 *
 * It consists of:
 * a button that updates a mutableState<boolean> and changes the + toa - and - to a +
 * a text label, the name of the meal
 * a FoodList composable to display found foods
 *
 * @param meal, string
 * @param foundINMealViewModel, a view model to work with the the food_in_meal table
 * @param apiNinjaViewModel, a view model to work with the NutritionAPI by API Ninja
 *
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun mealButton(meal: String, calories: Double, foodTypeViewModel: FoodTypeViewModel,
               mealViewModel: MealViewModel, foodInMealViewModel: FoodInMealViewModel,
               nutritionAPIViewModel: NutritionAPIViewModel
) {
    val open = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth()
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth()){
            Button(onClick = {open.value = !open.value},
            modifier = Modifier.background(color = Color.LightGray),
                border= BorderStroke(1.dp, Color.Black),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.LightGray)) {
                if(open.value){
                    Icon(
                        Icons.Sharp.ExpandMore,
                        contentDescription = "",
                        modifier = Modifier.background(color = Color.LightGray)
                            .size(20.dp),
                        tint = Color.Black
                    )
                }else{
                    Icon(
                        Icons.Sharp.NavigateNext,
                        contentDescription = "",
                        modifier = Modifier.background(color = Color.LightGray)
                            .size(20.dp),
                        tint = Color.Black
                    )
                }

            }
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Text(meal)
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Text("${calories.toInt()} cal")
        }

        FoodsInMeals(meal, open, foodTypeViewModel, mealViewModel, foodInMealViewModel, nutritionAPIViewModel)
    }
}
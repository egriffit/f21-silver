package com.example.workout_companion.view.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workout_companion.viewmodel.NutritionAPIViewModel
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.FoodTypeViewModel

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
fun mealButton(navController: NavController, meal: String, foodInMealViewModel: FoodInMealViewModel,
               nutritionAPIViewModel: NutritionAPIViewModel, foodTypeViewModel: FoodTypeViewModel
) {
    val open = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth()
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth()){
            Button(onClick = {open.value = !open.value}) {
                if(open.value){
                    Text("-",
                        fontSize = 15.sp)
                }else{
                    Text("+",
                        fontSize = 15.sp)
                }

            }
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Text(meal)
        }
        FoodList(navController, foodInMealViewModel, meal, open, nutritionAPIViewModel, foodTypeViewModel)
    }
}
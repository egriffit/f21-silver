package com.example.workout_companion.view.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ExpandMore
import androidx.compose.material.icons.sharp.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.viewmodel.NutritionAPIViewModel
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.FoodTypeViewModel
import com.example.workout_companion.viewmodel.MealViewModel
import kotlinx.coroutines.*

/***
 * Composable to show and hide foods in a meal using a +/- button
 *
 * It consists of:
 * a button that updates a mutableState<boolean> and changes the + toa - and - to a +
 * a text label, the name of the meal
 * a FoodList composable to display found foods
 *
 * @param navController, a NavController to navigate to different view
 * @param meal, string
 * @param calories, double, total calories for meal
 * @param foodTypeViewModel, a  view model to work with food_type table
 * @param mealViewModel, a view model to work with the the meal table
 * @param foodInMealViewModel, a view model to work with the the food_in_meal table
 * @param nutritionAPIViewModel, a view model to work with the NutritionAPI by API Ninja
 *
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MealButton(navController: NavController, meal: String, calories: Double,
               foodTypeViewModel: FoodTypeViewModel, mealViewModel: MealViewModel,
               foodInMealViewModel: FoodInMealViewModel, nutritionAPIViewModel: NutritionAPIViewModel
) {
        val open = remember { mutableStateOf(false) }
        val foundFoods =  foodInMealViewModel.foundFoods.observeAsState().value
        Column(
            modifier = Modifier.fillMaxWidth()
        )
        {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                OutlinedButton(onClick = {open.value = !open.value
                    runBlocking{
                        val job1: Job = launch(context = Dispatchers.IO){
                            foodInMealViewModel.foundFoods.postValue(null)
                            foodInMealViewModel.getFoodInMeal(meal)
                        }
                    }
                 },
                    modifier = Modifier.background(color = Color.LightGray),
//                    Modifier.fillMaxWidth(),
                    border= BorderStroke(1.dp, Color.Black),
//                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.LightGray)
                ) {

                    if(open.value){
                        Icon(
                            Icons.Sharp.ExpandMore,
                            contentDescription = "",
                            modifier = Modifier
                                .background(color = Color.LightGray)
                                .size(20.dp),
                            tint = Color.Black
                        )

                    }else{
                        Icon(
                            Icons.Sharp.NavigateNext,
                            contentDescription = "",
                            modifier = Modifier
                                .background(color = Color.LightGray)
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
            if (foundFoods != null) {
                FoodsInMeals(navController, meal, open, foundFoods)
            }
        }
}
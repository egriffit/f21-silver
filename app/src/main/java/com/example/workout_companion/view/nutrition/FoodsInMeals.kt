package com.example.workout_companion.view.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.workout_companion.viewmodel.NutritionAPIViewModel
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.FoodTypeViewModel
import com.example.workout_companion.viewmodel.MealViewModel
import java.time.LocalDate
/***
 * Composable to display a column of foods in a meal
 * It consists of:
 * a column of foods names
 * a foodSearchBox
 *
 *
 * @param navController, a NavController to navigate to different view
 * @param meal, string
 * @param open, a mutableStateBoolean to check if foods list is visible
 * @param foodTypeViewModel, a  view model to work with food_type table
 * @param mealViewModel, a view model to work with the the meal table
 * @param foundInMealViewModel, a view model to work with the the food_in_meal table
 * @param nutritionAPIViewModel, a view model to work with the NutritionAPI by API Ninja
 *
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FoodsInMeals(
    navController: NavController,
    meal: String,
    open: MutableState<Boolean>,
    foodTypeViewModel: FoodTypeViewModel,
    mealViewModel: MealViewModel,
    foodInMealViewModel: FoodInMealViewModel,
    nutritionAPIViewModel: NutritionAPIViewModel
) {
    val today = LocalDate.now()
    var foundFoods =  foodInMealViewModel.getFoodsInMeal(meal, today).observeAsState(listOf()).value
    if(open.value){
        Column(
            modifier = Modifier.fillMaxHeight()
        ){
            Text("Foods")
            Column(){
                //display current foods in meal
                for (food in foundFoods) {
                    Row {
                        FoodRow(navController, food)
                    }
                }
            }

        }
        //search box to add food

        foodSearchBox(navController, meal, foodTypeViewModel, mealViewModel, foodInMealViewModel, nutritionAPIViewModel)
    }
}
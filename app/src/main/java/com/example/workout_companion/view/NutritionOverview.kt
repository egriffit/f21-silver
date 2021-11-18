package com.example.workout_companion.view

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.workout_companion.viewmodel.NutritionAPIViewModel
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.view.nutrition.AddMealForm
import com.example.workout_companion.viewmodel.*

@RequiresApi(Build.VERSION_CODES.O)
/***
 * Composable form to display current nutrition and a form to create and update meals
 *
 * @param navController, a NavController to navigate to different view
 * @param foodTypeViewModel, a  view model to work with food_type table
 * @param mealViewModel, a view model to work with the the meal table
 * @param foundINMealViewModel, a view model to work with the the food_in_meal table
 * @param nutritionAPIViewModel, a view model to work with the NutritionAPI by API Ninja
 *
 */
@Composable
fun NutritionOverview(navController: NavController,
                      foodTypeViewModel: FoodTypeViewModel, mealViewModel: MealViewModel,
                      foodInMealViewModel: FoodInMealViewModel,
                      nutritionAPIViewModel: NutritionAPIViewModel){
    val context = LocalContext.current

    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            Column(modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .fillMaxHeight()
            )
            {
                Text("Nutrition Overview Page")
                AddMealForm(navController, foodTypeViewModel, mealViewModel,
                    foodInMealViewModel, nutritionAPIViewModel)
            }
        }
    )
}
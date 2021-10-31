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
@Composable
fun NutritionOverview(navController: NavController){
    val context = LocalContext.current

    val mealViewModel: MealViewModel = viewModel(
        factory = MealViewModelFactory(context.applicationContext as Application)
    )
    val foodInMealViewModel: FoodInMealViewModel = viewModel(
        factory = FoodInMealViewModelFactory(context.applicationContext as Application)
    )
    val foodTypeViewModel: FoodTypeViewModel = viewModel(
        factory = FoodTypeViewModelFactory(context.applicationContext as Application)
    )
//    val apiNinjaViewModel: NutritionApiNinjaViewModel = viewModel(
//        factory = NutritionApiNinjaViewModel.NutritionAPiNinjaViewModelFactory(context.applicationContext as Application)
//    )
    val nutritionAPIViewModel: NutritionAPIViewModel =  viewModel()

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
                AddMealForm(navController, mealViewModel, foodInMealViewModel,
                    nutritionAPIViewModel, foodTypeViewModel )
            }
        }
    )
}
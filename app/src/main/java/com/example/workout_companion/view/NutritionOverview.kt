package com.example.workout_companion.view

import android.app.Application
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
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.view.nutrition.AddMealForm
import com.example.workout_companion.viewmodel.CurrentUserGoalViewModel
import com.example.workout_companion.viewmodel.CurrentUserGoalViewModelFactory
import com.example.workout_companion.viewmodel.MealViewModel
import com.example.workout_companion.viewmodel.MealViewModelFactory

@Composable
fun NutritionOverview(navController: NavController){
    val context = LocalContext.current

    val mealViewModel: MealViewModel = viewModel(
        factory = MealViewModelFactory(context.applicationContext as Application)
    )
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
                AddMealForm(mealViewModel)
            }


        }
    )
}
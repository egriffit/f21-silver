package com.example.workout_companion.view

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.example.workout_companion.view.inputfields.LandingPage
import com.example.workout_companion.view.nutrition.FoundFoods
import com.example.workout_companion.viewmodel.UserViewModel
import com.example.workout_companion.viewmodel.UserViewModelFactory


@SuppressLint("NewApi")
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "splashScreen") {
        composable (route = "splashScreen") {
            SplashScreen(navController)
        }
        composable (route = "userForm" ) {
            //load user view model
            val context = LocalContext.current

            val userViewModel: UserViewModel = viewModel(
                factory = UserViewModelFactory(context.applicationContext as Application)
            )
            UserForm(navController, userViewModel)
        }
        composable (route = "mainView") {
            LandingPage(navController)
        }
        composable (route = "ExerciseOverview") {
            ExerciseOverview(navController)
        }
        composable (route = "NutritionOverview") {
            NutritionOverview(navController)
        }
        composable (route = "searchFood/{foodName}",
            arguments = listOf(
                navArgument("foodName") { type = NavType.StringType }
            )
        ){ backStackEntry ->
            FoundFoods(navController, backStackEntry.arguments?.getString("foodName"))
        }
        composable (route = "UpdateGoals") {
            UpdateGoalsView(navController)
        }
        composable (route = "Assessment") {
            AssessmentView(navController)
        }
        // Other routes go here
    }
}
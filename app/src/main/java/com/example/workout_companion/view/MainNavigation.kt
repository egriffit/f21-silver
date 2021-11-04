package com.example.workout_companion.view

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.example.workout_companion.view.inputfields.InfoForm
import com.example.workout_companion.view.inputfields.LandingPage
import com.example.workout_companion.view.nutrition.FoundFoods
import com.example.workout_companion.viewmodel.*


@Composable
fun MainNavigation() {
    // Put the view models you need here
    val goalTypeViewModel by lazy { viewModelProvider.get(GoalTypeViewModel::class.java) }
    val frameworkTypeViewModel by lazy { viewModelProvider.get(FrameworkTypeViewModel::class.java) }
    val frameworkDayViewModel by lazy { viewModelProvider.get(FrameworkDayViewModel::class.java) }
    val frameworkComponentViewModel by lazy { viewModelProvider.get(FrameworkComponentViewModel::class.java) }
    val userViewModel by lazy { viewModelProvider.get(UserViewModel::class.java) }
    val userWithGoalViewModel by lazy { viewModelProvider.get(UserWithGoalViewModel::class.java) }

    val navController = rememberNavController()
    NavHost(navController, startDestination = "splashScreen") {
        composable (route = "splashScreen") {
            SplashScreen(navController)
        }
        composable (route = "userForm" ) {
            UserForm(navController, userViewModel, userWithGoalViewModel)
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
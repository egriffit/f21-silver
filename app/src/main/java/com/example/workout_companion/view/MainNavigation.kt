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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workout_companion.entity.GoalTypeEntity
import com.example.workout_companion.utility.MainGoal
import com.example.workout_companion.view.inputfields.InfoForm
import com.example.workout_companion.view.inputfields.LandingPage
import com.example.workout_companion.viewmodel.*


@Composable
fun MainNavigation(viewModelProvider: ViewModelProvider) {
    // Put the view models you need here
    val goalTypeViewModel by lazy { viewModelProvider.get(GoalTypeViewModel::class.java) }
    val userViewModel by lazy { viewModelProvider.get(UserViewModel::class.java) }

    createDefaults(goalTypeViewModel)

    val navController = rememberNavController()
    NavHost(navController, startDestination = "splashScreen") {
        composable (route = "splashScreen") {
            SplashScreen(navController)
        }
        composable (route = "userForm" ) {
            val goals = goalTypeViewModel.allGoals
            UserForm(navController, userViewModel, goals)
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
        composable (route = "UpdateGoals") {
            UpdateGoalsView(navController)
        }
        composable (route = "Assessment") {
            AssessmentView(navController)
        }
        // Other routes go here
    }
}

fun createDefaults(goalViewModel: GoalTypeViewModel) {
    goalViewModel.addGoal(GoalTypeEntity(0, "Build Muscle", 250))
    goalViewModel.addGoal(GoalTypeEntity(1, "Gain Strength", 500))
    goalViewModel.addGoal(GoalTypeEntity(2, "Lose Weight", -500))
}
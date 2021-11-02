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
import com.example.workout_companion.database.GOALS
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
    val userWithGoalViewModel by lazy { viewModelProvider.get(UserWithGoalViewModel::class.java) }

    createDefaults(goalTypeViewModel)

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
        composable (route = "UpdateGoals") {
            UpdateGoalsView(navController)
        }
        composable (route = "Assessment") {
            AssessmentView(navController)
        }
        // Other routes go here
    }
}

/**
 * Create any default values if necessary. Add your default table values here.
 *
 * @property goalViewModel The view model to add goals with
 */
fun createDefaults(goalViewModel: GoalTypeViewModel) {
    for (goal in GOALS.values) {
        // We are not guarding this statement because our database policy
        // is to ignore any conflicting adds.
        // Probably not the best solution, but it works for now
        goalViewModel.addGoal(goal)
    }
}
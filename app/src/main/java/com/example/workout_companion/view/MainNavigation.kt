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
import com.example.workout_companion.view.inputfields.InfoForm
import com.example.workout_companion.view.inputfields.LandingPage
import com.example.workout_companion.viewmodel.CurrentUserGoalViewModel
import com.example.workout_companion.viewmodel.CurrentUserGoalViewModelFactory
import com.example.workout_companion.viewmodel.UserViewModel
import com.example.workout_companion.viewmodel.UserViewModelFactory


@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "splashScreen") {
        composable (route = "splashScreen") {
            SplashScreen(navController)
        }
        composable (route = "workouts") {
            WorkoutsView(navController)
        }
        composable (route = "addGoals" ) {
            //load user view model
            val context = LocalContext.current

            val userViewModel: UserViewModel = viewModel(
                factory = UserViewModelFactory(context.applicationContext as Application)
            )
            InfoForm(navController, userViewModel)
        }
        composable (route = "mainView") {
            LandingPage(navController)
        }
        // Other routes go here
    }
}
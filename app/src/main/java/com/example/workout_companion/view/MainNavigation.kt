package com.example.workout_companion.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.Text


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
        composable (route = "addGoals") {
            AddGoalView(navController)
        }
        composable (route = "mainView") {
            MainView(navController)
        }
        // Other routes go here
    }
}

@Composable
fun WorkoutsView(navController: NavController) {
    Column() {
        Text (
            text = "Workouts View"
        )
    }
}

@Composable
fun AddGoalsView(navController: NavController) {
    Column() {
        Text (
            text = "Add Goals View"
        )
    }
}
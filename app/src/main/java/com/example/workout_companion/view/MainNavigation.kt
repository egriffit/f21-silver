package com.example.workout_companion.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.Text

@Composable
fun AppNavController() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "workouts") {
        composable (route = "workouts") {
            WorkoutsView(navController)
        }
        composable (route = "addGoals") {
            AddGoalsView(navController)
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
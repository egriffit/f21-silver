package com.example.workout_companion.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.workout_companion.view.inputfields.TopNavigation

@Composable
fun AssessmentView(navController: NavController){
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = { WorkoutsView(navController) }
    )
}
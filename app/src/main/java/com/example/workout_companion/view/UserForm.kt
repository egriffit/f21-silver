package com.example.workout_companion.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.view.userform.InfoForm
import com.example.workout_companion.viewmodel.UserViewModel
import com.example.workout_companion.viewmodel.UserWithGoalViewModel

@Composable
fun UserForm(navController: NavController, userViewModel: UserViewModel, userWithGoalViewModel: UserWithGoalViewModel)
{
    Scaffold(
        topBar = {TopNavigation(navController)},
        bottomBar = {},
        content = { InfoForm(navController, userViewModel, userWithGoalViewModel) }
        )
}

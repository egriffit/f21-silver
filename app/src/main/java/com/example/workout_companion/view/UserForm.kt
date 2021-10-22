package com.example.workout_companion.view

import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.workout_companion.view.inputfields.InfoForm
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.UserViewModel

@Composable
fun UserForm(navController: NavController, userViewModel: UserViewModel)
{
    Scaffold(
        topBar = {TopNavigation(navController)},
        bottomBar = {},
        content = { InfoForm(navController, userViewModel) }
        )
}

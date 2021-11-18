package com.example.workout_companion.view

import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.workout_companion.entity.GoalTypeEntity
import com.example.workout_companion.view.inputfields.InfoForm
import com.example.workout_companion.view.inputfields.TopNavigation
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

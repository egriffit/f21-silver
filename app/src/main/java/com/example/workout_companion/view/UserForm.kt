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

@Composable
fun UserForm(navController: NavController, userViewModel: UserViewModel, goals: LiveData<List<GoalTypeEntity>>)
{
    Scaffold(
        topBar = {TopNavigation(navController)},
        bottomBar = {},
        content = { InfoForm(navController, userViewModel, goals) }
        )
}

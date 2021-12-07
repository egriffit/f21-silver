package com.example.workout_companion.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavController
import com.example.workout_companion.dao.WorkoutWithComponents
import com.example.workout_companion.entity.FrameworkDayEntity
import com.example.workout_companion.view.exercise.WorkoutView
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.FrameworkComponentViewModel

@Composable
fun ExerciseOverview(navController: NavController,
                     workoutState: State<WorkoutWithComponents?>,
                    frameworkDays: List<FrameworkDayEntity>,
                     frameworkComponentViewModel: FrameworkComponentViewModel
){
    Scaffold(
    topBar = { TopNavigation(navController) },
    bottomBar = {},
    content = { WorkoutView(navController, workoutState, frameworkDays, frameworkComponentViewModel) }
    )
}

package com.example.workout_companion.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavController
import com.example.workout_companion.dao.FrameworkDayWithComponents
import com.example.workout_companion.dao.FrameworkWithDays
import com.example.workout_companion.dao.WorkoutWithComponents
import com.example.workout_companion.view.exercise.WorkoutView
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.WorkoutViewModel
import kotlinx.coroutines.Job
import kotlin.reflect.KFunction1

@Composable
fun ExerciseOverview(
    navController: NavController,
    workoutState: State<WorkoutWithComponents?>,
    frameworkWithDays: State<FrameworkWithDays?>,
    workoutViewModel: WorkoutViewModel,
){
    Scaffold(
    topBar = { TopNavigation(navController) },
    bottomBar = {},
    content = { WorkoutView(navController, workoutState, frameworkWithDays, workoutViewModel) }
    )
}

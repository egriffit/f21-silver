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
import com.example.workout_companion.viewmodel.WgerAPIViewModel
import com.example.workout_companion.viewmodel.WorkoutComponentViewModel
import com.example.workout_companion.viewmodel.WorkoutViewModel

@Composable
fun ExerciseOverview(navController: NavController,
                     workoutState: State<WorkoutWithComponents?>,
                    frameworkDays: List<FrameworkDayEntity>,
                     frameworkComponentViewModel: FrameworkComponentViewModel,
                     wgerAPIViewModel: WgerAPIViewModel,
                     workoutViewModel: WorkoutViewModel,
                     workoutComponentViewModel: WorkoutComponentViewModel
){
    Scaffold(
    topBar = { TopNavigation(navController) },
    bottomBar = {},
    content = { WorkoutView(navController, workoutState, frameworkDays,
        frameworkComponentViewModel, wgerAPIViewModel, workoutViewModel, workoutComponentViewModel) }
    )
}

@Composable
fun ExerciseOverview(navController: NavController,
                     workoutState: State<WorkoutWithComponents?>,
                     frameworkDays: List<FrameworkDayEntity>,
                     dayId: Int,
                     frameworkComponentViewModel: FrameworkComponentViewModel,
                     wgerAPIViewModel: WgerAPIViewModel,
                     workoutViewModel: WorkoutViewModel,
                     workoutComponentViewModel: WorkoutComponentViewModel
){
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = { WorkoutView(navController, workoutState, dayId, frameworkDays,
            frameworkComponentViewModel, wgerAPIViewModel, workoutViewModel, workoutComponentViewModel) }
    )
}

@Composable
fun ExerciseOverview(navController: NavController,
                     workoutState: State<WorkoutWithComponents?>,
                     frameworkDays: List<FrameworkDayEntity>,
                     dayId: Int,
                     muscleId: Int,
                     exerciseId: Int,
                     searchedMuscle: String,
                     frameworkComponentViewModel: FrameworkComponentViewModel,
                     wgerAPIViewModel: WgerAPIViewModel,
                     workoutViewModel: WorkoutViewModel,
                     workoutComponentViewModel: WorkoutComponentViewModel
){
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = { WorkoutView(navController, workoutState, dayId, muscleId,
            exerciseId, searchedMuscle, frameworkDays, frameworkComponentViewModel,
            wgerAPIViewModel, workoutViewModel, workoutComponentViewModel) }
    )
}
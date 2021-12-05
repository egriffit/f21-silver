package com.example.workout_companion.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavController
import com.example.workout_companion.dao.WorkoutWithComponents
import com.example.workout_companion.entity.FrameworkDayEntity
import com.example.workout_companion.view.exercise.WorkoutView
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.*

@Composable
fun ExerciseOverview(navController: NavController,
                     workoutState: State<WorkoutWithComponents?>,
                    frameworkDays: List<FrameworkDayEntity>,
                     frameworkComponentViewModel: FrameworkComponentViewModel,
                     wgerAPIViewModel: WgerAPIViewModel,
                     workoutViewModel: WorkoutViewModel,
                     workoutComponentViewModel: WorkoutComponentViewModel,
                     workoutComponentSetViewModel: WorkoutComponentSetViewModel,
){
    Scaffold(
    topBar = { TopNavigation(navController) },
    bottomBar = {},
    content = { WorkoutView(navController, workoutState, frameworkDays,
        frameworkComponentViewModel, wgerAPIViewModel, workoutViewModel, workoutComponentViewModel,
        workoutComponentSetViewModel) }
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
                     workoutComponentViewModel: WorkoutComponentViewModel,
                     workoutComponentSetViewModel: WorkoutComponentSetViewModel,
){
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = { WorkoutView(navController, workoutState, dayId, frameworkDays,
            frameworkComponentViewModel, wgerAPIViewModel, workoutViewModel, workoutComponentViewModel,
            workoutComponentSetViewModel) }
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
                     workoutComponentViewModel: WorkoutComponentViewModel,
                     workoutComponentSetViewModel: WorkoutComponentSetViewModel
){
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = { WorkoutView(navController, workoutState, dayId, muscleId,
            exerciseId, searchedMuscle, frameworkDays, frameworkComponentViewModel,
            wgerAPIViewModel, workoutViewModel, workoutComponentViewModel,
            workoutComponentSetViewModel) }
    )
}
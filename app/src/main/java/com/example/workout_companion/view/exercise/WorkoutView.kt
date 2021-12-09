package com.example.workout_companion.view.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.workout_companion.dao.FrameworkDayWithComponents
import com.example.workout_companion.dao.FrameworkWithDays
import com.example.workout_companion.dao.WorkoutWithComponents
import com.example.workout_companion.enumeration.Progress
import com.example.workout_companion.viewmodel.WorkoutViewModel
import kotlinx.coroutines.Job
import kotlin.reflect.KFunction1

@Composable
fun WorkoutView(
    navController: NavController,
    workoutState: State<WorkoutWithComponents?>,
    frameworkWithDays: State<FrameworkWithDays?>,
    workoutViewModel: WorkoutViewModel,
){
    LazyColumn(
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        // If we have not done a workout today, we won't have one
        if (workoutState.value == null) {
            item { WorkoutProgress(Progress.NOT_STARTED) }
            item { FrameworkDaySelector(frameworkWithDays, workoutViewModel::createWorkout) }
        }
        else {
            item { WorkoutProgress(workoutState.value!!.workout.status) }
            items(workoutState.value!!.components) { component ->
                    WorkoutComponentView(navController, component)
            }

            item {
                Row(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SubmitButton(navController, workoutState.value!!, workoutViewModel)
                    CancelButton(workoutState.value!!, workoutViewModel)
                }
            }
        }
    }
}

@Composable
fun WorkoutProgress(progress: Progress) {
    val colorMap = mapOf(
        Progress.NOT_STARTED to Color.Red,
        Progress.IN_PROGRESS to Color.Blue,
        Progress.COMPLETE to Color.Green,
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Workout State:")
        Text(progress.descName, color = colorMap.getOrDefault(progress, Color.Black))
    }
}

@Composable
fun FrameworkDaySelector(
    frameworkWithDays: State<FrameworkWithDays?>, createWorkout: KFunction1<FrameworkDayWithComponents, Job>,
) {
    var expanded by remember { mutableStateOf(false) }
    OutlinedButton(
        onClick = { expanded = !expanded }
    ) {
        Text(text = "Start Workout")
    }
    // If this dude is null, we have issues elsewhere in our app
    if (frameworkWithDays.value != null) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {},
        ) {
            for (dayWithComponents in frameworkWithDays.value!!.days) {
                DropdownMenuItem(
                    onClick = {
                        expanded = !expanded
                        createWorkout (dayWithComponents)
                    }
                ) { Text(dayWithComponents.day.name) }
            }
        }
    }
}

@Composable
fun SubmitButton(navController: NavController, workout: WorkoutWithComponents, workoutViewModel: WorkoutViewModel) {
    OutlinedButton(
        onClick = {
            workout.workout.status = Progress.COMPLETE
            workoutViewModel.updateWorkout(workout.workout)
            navController.navigate("Landing")
        }
    ) { Text("Submit") }
}

@Composable
fun CancelButton(workout: WorkoutWithComponents, workoutViewModel: WorkoutViewModel) {
    OutlinedButton(
        onClick = {
            workoutViewModel.deleteWorkout(workout.workout)
        }
    ) { Text("Cancel") }
}

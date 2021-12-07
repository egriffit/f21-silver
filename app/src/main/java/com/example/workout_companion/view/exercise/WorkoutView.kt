package com.example.workout_companion.view.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.dao.FrameworkDayWithComponents
import com.example.workout_companion.dao.FrameworkWithDays
import com.example.workout_companion.dao.WorkoutWithComponents
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
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        // If we have not done a workout today, we won't have one
        if (workoutState.value == null) {
            item {
                FrameworkDaySelector(frameworkWithDays, workoutViewModel::createWorkout)
            }
        }
        else {
            items(workoutState.value!!.components) { component ->
                    FrameworkComponentItem(navController, component)
            }

            item {
                Row(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SubmitButton()
                    CancelButton()
                }
            }
        }
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
fun SubmitButton() {
    OutlinedButton(
        onClick = {
            // TODO: Set the workout to complete
            // TODO: Update the workout in the database
        }
    ) { Text("Submit") }
}

@Composable
fun CancelButton() {
    OutlinedButton(
        onClick = {
            // TODO: Delete the workout here
        }
    ) { Text("Cancel") }
}

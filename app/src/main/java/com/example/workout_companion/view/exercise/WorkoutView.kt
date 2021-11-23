package com.example.workout_companion.view.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workout_companion.dao.WorkoutWithComponents
import com.example.workout_companion.entity.WorkoutEntity
import com.example.workout_companion.enumeration.Progress
import kotlinx.coroutines.flow.combineTransform
import java.time.LocalDate

@Composable
fun WorkoutView(workoutState: State<WorkoutWithComponents?>) {
    Column(modifier = Modifier
        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        .fillMaxWidth()
        .fillMaxHeight(),
    )
    {
        Row(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (workoutState.value == null) {
                var expanded by remember { mutableStateOf(false) }
                
                OutlinedButton(
                    onClick = { expanded = !expanded }
                ) {
                    Text(text = "Start Workout")
                }
                DropdownMenu(
                    expanded = expanded, 
                    onDismissRequest = { /*TODO*/ }
                ) {
                    for (i in 0..5) {
                        DropdownMenuItem(
                            onClick = { expanded = !expanded }) {
                            Text(text = "Day $i")
                        }
                    }
                }
            }
            else
            {
                // TODO: load the workout here
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoWorkoutViewPreview() {
    val noWorkout: State<WorkoutWithComponents?> = derivedStateOf { null }
    WorkoutView(noWorkout)
}

package com.example.workout_companion.view.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.dao.WorkoutWithComponents
import com.example.workout_companion.entity.FrameworkDayEntity
import com.example.workout_companion.view.FrameworkComponentItem
import com.example.workout_companion.viewmodel.FrameworkComponentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun WorkoutView(navController: NavController,
                workoutState: State<WorkoutWithComponents?>,
                frameworkDays: List<FrameworkDayEntity>,
                frameworkComponentViewModel: FrameworkComponentViewModel
){
    val frameworkComponents = frameworkComponentViewModel.components.observeAsState().value
    var selectedDay by remember { mutableStateOf(0) }
    Column(modifier = Modifier
        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        .fillMaxWidth()
        .fillMaxHeight(),
    )
    {
        if(selectedDay == 0) {
            Row(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //if (workoutState.value == null) {
                var expanded by remember { mutableStateOf(false) }
                OutlinedButton(
                    onClick = { expanded = !expanded }
                ) {
                    Text(text = "Start Workout")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {}
                ) {
                    frameworkDays.forEach { it ->
                        DropdownMenuItem(
                            onClick = {
                                expanded = !expanded
                                selectedDay = it.id
                            }) {
                            Text(text = "${it.name}")
                        }
                    }
                }
            }
        }else{
            runBlocking {
                val job: Job = launch(Dispatchers.IO) {
                    if (selectedDay != 0) {
                        frameworkComponentViewModel.getAllComponentsOfDay(selectedDay)
                    }
                }
            }
            frameworkDays.forEach{it ->
                if(it.id == selectedDay){
                    Text("Selected Day: ${it.name}")
                }
            }
            //} else {
            // TODO: load the workout here
            if(frameworkComponents != null) {
                LazyColumn{
                    frameworkComponents.forEach { m ->
                       item{
                           FrameworkComponentItem(navController, m.muscle_group.name)
                       }
                    }
                }
            }
            OutlinedButton(
                onClick = {
                    val temp = "temp"
                }) {
                Text("Submit")
            }
            Spacer(modifier = Modifier.padding(end = 10.dp))
            OutlinedButton(
                onClick = {
                    val temp = "temp"
                    selectedDay = 0
                }) {
                    Text("Cancel")
                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun NoWorkoutViewPreview() {

    val noWorkout: State<WorkoutWithComponents?> = derivedStateOf { null }
    //WorkoutView(noWorkout, FRAMEWORK_DAYS)
}

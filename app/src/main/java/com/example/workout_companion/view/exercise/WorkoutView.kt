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
import com.example.workout_companion.viewmodel.FrameworkComponentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun WorkoutView(
    navController: NavController,
    workoutState: State<WorkoutWithComponents?>,
    dayId: Int,
    frameworkDays: List<FrameworkDayEntity>,
    frameworkComponentViewModel: FrameworkComponentViewModel
){
    val frameworkComponents = frameworkComponentViewModel.components.observeAsState().value
    var selectedDay = remember { mutableStateOf(dayId) }
    Column(modifier = Modifier
        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        .fillMaxWidth()
        .fillMaxHeight(),
    )
    {
        if( selectedDay.value == 0) {
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
                                selectedDay.value = it.id
                            }) {
                            Text(text = "${it.name}")
                        }
                    }
                }
            }
        }else{
            runBlocking {
                val job: Job = launch(Dispatchers.IO) {
                    if(dayId == 0) {
                        if (selectedDay.value != 0) {
                            frameworkComponentViewModel.getAllComponentsOfDay(selectedDay.value)
                        }
                    }else{
                        frameworkComponentViewModel.getAllComponentsOfDay(dayId)
                    }
                }
            }
            frameworkDays.forEach{it ->
                if(it.id == selectedDay.value){
                    Text("Selected Day: ${it.name}")
                }
            }
            if(frameworkComponents != null) {
                LazyColumn{
                    frameworkComponents.forEach { m ->
                       item{
                           FrameworkComponentItem(navController, selectedDay.value, m.muscle_group.name)
                       }
                    }
                }
            }
            OutlinedButton(
                onClick = {
                    val temp = "temp"
                    selectedDay.value = 0
                }) {
                Text("Submit")
            }
            Spacer(modifier = Modifier.padding(end = 10.dp))
            OutlinedButton(
                onClick = {
                    val temp = "temp"
                    selectedDay.value = 0
                }) {
                    Text("Cancel")
                }
            }
        }
}

@Composable
fun WorkoutView(
    navController: NavController,
    workoutState: State<WorkoutWithComponents?>,
    frameworkDays: List<FrameworkDayEntity>,
    frameworkComponentViewModel: FrameworkComponentViewModel
){
    WorkoutView(navController, workoutState, 0, frameworkDays, frameworkComponentViewModel)
}

@Preview(showBackground = true)
@Composable
fun NoWorkoutViewPreview() {

    val noWorkout: State<WorkoutWithComponents?> = derivedStateOf { null }
    //WorkoutView(noWorkout, FRAMEWORK_DAYS)
}

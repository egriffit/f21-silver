package com.example.workout_companion.view.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.dao.WorkoutWithComponents
import com.example.workout_companion.entity.FrameworkDayEntity
import com.example.workout_companion.entity.WorkoutComponentEntity
import com.example.workout_companion.entity.WorkoutEntity
import com.example.workout_companion.enumeration.Progress
import com.example.workout_companion.viewmodel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

@Composable
fun WorkoutView(
    navController: NavController,
    workoutState: State<WorkoutWithComponents?>,
    dayId: Int,
    muscleId: Int,
    exerciseId: Int,
    searchedMuscle: String,
    frameworkDays: List<FrameworkDayEntity>,
    frameworkComponentViewModel: FrameworkComponentViewModel,
    wgerAPi: WgerAPIViewModel,
    workoutViewModel: WorkoutViewModel,
    workoutComponentViewModel: WorkoutComponentViewModel,
    workoutComponentSetViewModel: WorkoutComponentSetViewModel
    ){
    val frameworkComponents = frameworkComponentViewModel.components.observeAsState().value
    val workoutComponents = workoutComponentViewModel.workoutComponents.observeAsState().value
    var selectedDay = remember { mutableStateOf(dayId) }
    val selectedMuscle = remember{mutableStateOf(muscleId)}
    val selectedExercise = remember{mutableStateOf(exerciseId)}
    var exerciseName = ""
    val focusManager = LocalFocusManager.current

    if(exerciseId != 0){
        LaunchedEffect(key1 = Unit, block = {
            wgerAPi.getExericseInfo(exerciseId )
        })
        exerciseName = wgerAPi.exerciseInfo.value.name
    }
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
                    onClick = { expanded = !expanded  }
                ) {
                    Text(text = "Start Workout")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        focusManager.clearFocus()
                    }
                ) {
                    frameworkDays.forEach {
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
                            frameworkComponentViewModel.getComponentsOfDay(selectedDay.value, 3)
                        }
                    }else{
                        frameworkComponentViewModel.getComponentsOfDay(dayId, 3)
                    }
                }
            }
            frameworkDays.forEach{
                if(it.id == selectedDay.value){
                    Text("Selected Day: ${it.name}")
                }
            }
            //create a workout
            if(frameworkComponents != null) {
                runBlocking{
                    val job1: Job = launch(Dispatchers.IO){
                        //Create a workout
                        var workout = WorkoutEntity(
                            LocalDate.now(),
                            Progress.NOT_STARTED,
                            dayId)
                        if(dayId == 0){
                            workout = WorkoutEntity(
                                LocalDate.now(),
                                Progress.NOT_STARTED,
                                selectedDay.value)
                        }
                        workoutViewModel.createWorkout(workout)
                    }
                    job1.join()
                    //create components
                    val job2: Job = launch(Dispatchers.IO){
                            frameworkComponents.forEach { m ->
                                workoutComponentViewModel.addWorkoutComponent(WorkoutComponentEntity(0, LocalDate.now(), m.id))
                            }
                        }
                    job2.join()
                    val job3: Job = launch(Dispatchers.IO){
                        workoutComponentViewModel.getWorkoutComponentsForDate(LocalDate.now())
                    }

                    }
                //create the components
                LazyColumn{
                    frameworkComponents.forEachIndexed{index, m ->
                        if(workoutComponents != null && workoutComponents.isNotEmpty()){
                                if(workoutComponents[index].component_id == m.id){
                                    item{
                                        FrameworkComponentItem(navController, workoutComponents[index].id, selectedDay.value,
                                            m.id, m.muscle_group.name, searchedMuscle, selectedExercise.value, exerciseName,
                                        workoutComponentSetViewModel)
                                    }
                                }
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
    frameworkComponentViewModel: FrameworkComponentViewModel,
    wgerAPi: WgerAPIViewModel,
    workoutViewModel: WorkoutViewModel,
    workoutComponentViewModel: WorkoutComponentViewModel,
    workoutComponentSetViewModel: WorkoutComponentSetViewModel,
    ){
    WorkoutView(navController, workoutState, 0, 0, 0, "", frameworkDays,
        frameworkComponentViewModel, wgerAPi, workoutViewModel, workoutComponentViewModel,
        workoutComponentSetViewModel)
}

@Composable
fun WorkoutView(
    navController: NavController,
    workoutState: State<WorkoutWithComponents?>,
    dayId: Int,
    frameworkDays: List<FrameworkDayEntity>,
    frameworkComponentViewModel: FrameworkComponentViewModel,
    wgerAPi: WgerAPIViewModel,
    workoutViewModel: WorkoutViewModel,
    workoutComponentViewModel: WorkoutComponentViewModel,
    workoutComponentSetViewModel: WorkoutComponentSetViewModel,
){
    WorkoutView(navController, workoutState, dayId, 0, 0, "", frameworkDays,
        frameworkComponentViewModel, wgerAPi, workoutViewModel, workoutComponentViewModel,
        workoutComponentSetViewModel)
}



@Preview(showBackground = true)
@Composable
fun NoWorkoutViewPreview() {

    val noWorkout: State<WorkoutWithComponents?> = derivedStateOf { null }
    //WorkoutView(noWorkout, FRAMEWORK_DAYS)
}

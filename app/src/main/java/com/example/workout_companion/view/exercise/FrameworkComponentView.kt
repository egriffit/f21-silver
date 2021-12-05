package com.example.workout_companion.view.exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.workout_companion.entity.WorkoutComponentSetEntity
import com.example.workout_companion.enumeration.Progress
import com.example.workout_companion.viewmodel.WorkoutComponentSetViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun FrameworkComponentItem(navController: NavController,
                           workoutId: Int,
                           dayId: Int,
                           muscleId: Int,
                           muscle: String,
                           searchedMuscle: String,
                           exerciseId: Int,
                           exerciseName: String,
                           workoutComponentSetViewModel: WorkoutComponentSetViewModel
                            ) {
    Column {

        val expandedState = remember { mutableStateOf(false) }
        val selectedExercise = remember{ mutableStateOf("")}
        FrameworkComponentHeader(navController, dayId, muscleId, muscle, searchedMuscle, exerciseId, exerciseName, expandedState)
        val foundSets = workoutComponentSetViewModel.workoutComponentSets.observeAsState().value
        if(expandedState.value) {
                runBlocking{
                    launch(Dispatchers.IO){
                        workoutComponentSetViewModel.getSetsFprComponent(workoutId)
                    }
                }
//            if(foundSets != null && foundSets.isNotEmpty()){
//                        foundSets.forEach(){
//                            FrameworkComponentSetRow(
//                                exerciseId,
//                                workoutId,
//                                workoutComponentSetViewModel,
//                                it
//                            )
//                        }
//                if(foundSets.size < 3){
//                    for (i in foundSets.size+1..3) {
//                        FrameworkComponentSetRow(
//                            exerciseId,
//                            workoutId,
//                            workoutComponentSetViewModel,
//                            WorkoutComponentSetEntity(0, muscleId, 0, 0.0, Progress.NOT_STARTED, null)
//                        )
//                    }
//                }
//            }else{
                for (i in 1..3) {
                        FrameworkComponentSetRow(
                            exerciseId,
                            workoutId,
                            workoutComponentSetViewModel,
                            WorkoutComponentSetEntity(0, muscleId, 0, 0.0, Progress.NOT_STARTED, null)
                        )
                    }
//                }
            }
        }
    }

@Composable
fun FrameworkComponentHeader(navController: NavController, dayId: Int,
                             muscleId: Int,
                             muscle: String,
                             searchedMuscle: String,
                             exerciseId: Int,
                             exerciseName: String,
                             expanded: MutableState<Boolean>) {
    val muscleGroupState by remember { mutableStateOf(muscle) }
    val exerciseState by remember { mutableStateOf("Pick an Exercise") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Drop Down Icon
        if(expanded.value == true) {
            Button(onClick = { expanded.value = !expanded.value }) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Localized Description"
                )
            }
        }else {
            Button(onClick = { expanded.value = !expanded.value }) {
                Icon(
                    imageVector = Icons.Filled.ArrowRight,
                    contentDescription = "Localized Description"
                )
            }
        }
            // Muscle Group Name
            Text(text = muscleGroupState)

            // Spacer
            Text(text = " -")

            // Exercise Name
            Column(){
                if(searchedMuscle == muscle){
                    Text("${exerciseName}")
                }
                TextButton(
                    onClick = { navController.navigate("searchExercise/${muscle}/${dayId}/${muscleId}")},
                ) {
                    Text(text = exerciseState)
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun FrameworkComponentItemPreview() {
    //FrameworkComponentItem("Biceps")
}
package com.example.workout_companion.view.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.enumeration.MuscleGroupConverter
import com.example.workout_companion.viewmodel.WgerAPIViewModel

//Displays exercises found from WGER
@Composable
fun FoundExerises(
    navController: NavController,
    muscleId: Int,
    muscle: String?,
    dayID: Int,
    wgerAPi: WgerAPIViewModel
) {
    val selectedId = remember { mutableStateOf(0) }
    val selectedIndex = remember { mutableStateOf(0) }

    Column() {
        Row() {
            Text("Found Exercises for ${muscle}")
        }
        val muscleGroup = MuscleGroupConverter.toMuscleGroup(muscle!!)
        val muscleIDWGER = muscleGroup.wgerMuscles.elementAt(0).id
        Text("${muscleId} - ${muscleGroup.wgerMuscles.elementAt(0).descName}")
        LaunchedEffect(key1 = Unit, block = {
            wgerAPi.getExercisesByMuscleGroup(muscleIDWGER)
        })
        val found = wgerAPi.exerciseInMuscles
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            item {
                if (found.value.results.isNotEmpty()) {
                    ExerciseRadioButtons(navController, found.value, muscleId!!, muscle, dayID, selectedIndex, selectedId)
                }
            }
            item {
                Row() {
                    Button(onClick = {
                            if(selectedId.value != 0) {
                                navController.navigate("ExerciseOverview/d/${dayID}/m/${muscleId}/e/${selectedId.value}")
                            }
                        }
                    )
                    {
                        Text("Submit")
                    }
                    Spacer(modifier = Modifier.padding(start = 20.dp))
                    Button(onClick = { navController.navigate("ExerciseOverview/d/${dayID}") }) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}
package com.example.workout_companion.view.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.workout_companion.dao.ComponentWithSets
import com.example.workout_companion.viewmodel.WgerAPIViewModel
import com.example.workout_companion.viewmodel.WorkoutViewModel

@Composable
fun WorkoutComponentView(navController: NavController, componentWithSets: ComponentWithSets, workoutViewModel: WorkoutViewModel, wgerAPIViewModel: WgerAPIViewModel) {
    Column (
        modifier = Modifier.fillMaxWidth(),
    ) {
        val expandedState = remember { mutableStateOf(false) }
        WorkoutComponentHeader(navController, wgerAPIViewModel, componentWithSets, expandedState)
        if(expandedState.value) {
            for (set in componentWithSets.sets) {
                WorkoutComponentSetView(set, workoutViewModel)
            }
        }
    }
}

@Composable
fun WorkoutComponentHeader(navController: NavController, wgerAPIViewModel: WgerAPIViewModel, componentWithSets: ComponentWithSets, expanded: MutableState<Boolean>) {

    val muscleGroupState by remember { mutableStateOf(componentWithSets.muscleGroup.name) }
    var exerciseState by remember { mutableStateOf("Pick an Exercise") }

    if (exerciseState == "Pick an Exercise" && componentWithSets.component.wger_id != null) {
        wgerAPIViewModel.getExerciseInfo(componentWithSets.component.wger_id!!)
        exerciseState = wgerAPIViewModel.exerciseInfo.value.name
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Drop Down Icon
        val icon = if (expanded.value) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowRight
        OutlinedButton(
            onClick = { expanded.value = !expanded.value },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Localized Description"
            )

            // Muscle Group Name
            Text(text = muscleGroupState)

            // Spacer
            Text(text = "  -")

            // Exercise Name
            // Need ComponentWithSets from Here -> MainNav -> FoundExercises as param
            TextButton(
                onClick = { navController.navigate("searchExercise/${componentWithSets.muscleGroup.name}/${componentWithSets.component.id}") },
            ) {
                Text(text = exerciseState)
            }
        }
    }
}
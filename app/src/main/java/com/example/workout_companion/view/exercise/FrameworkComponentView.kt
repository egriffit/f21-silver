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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.workout_companion.dao.ComponentWithSets

@Composable
fun FrameworkComponentItem(navController: NavController, componentWithSets: ComponentWithSets) {
    Column (
        modifier = Modifier.fillMaxWidth(),
    ) {
        val expandedState = remember { mutableStateOf(false) }
        FrameworkComponentHeader(navController, componentWithSets, expandedState)
        if(expandedState.value) {
            for (set in componentWithSets.sets) {
                FrameworkComponentSetRow(set)
            }
        }
    }
}

@Composable
fun FrameworkComponentHeader(navController: NavController, componentWithSets: ComponentWithSets, expanded: MutableState<Boolean>) {

    val muscleGroupState by remember { mutableStateOf(componentWithSets.muscleGroup.name) }
    val exerciseState by remember { mutableStateOf("Pick an Exercise") }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Drop Down Icon
        val icon = if (expanded.value) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowRight
        Button(onClick = { expanded.value = !expanded.value }) {
            Icon(
                imageVector = icon,
                contentDescription = "Localized Description"
            )

            // Muscle Group Name
            Text(text = muscleGroupState)

            // Spacer
            Text(text = "  -")

            // Exercise Name
            TextButton(
                onClick = { navController.navigate("searchExercise/${componentWithSets.muscleGroup.name}") },
            ) {
                Text(text = exerciseState)
            }
        }
    }
}
package com.example.workout_companion.view

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

@Composable
fun FrameworkComponentItem(navController: NavController, muscle: String) {
    Column {

        var expandedState = remember { mutableStateOf(false) }
        var selectedExercise = remember{ mutableStateOf("")}
        FrameworkComponentHeader(navController, muscle, expandedState)
        if(expandedState.value == true) {
            // TODO: load each set found in the database here
            for (i in 1..3) {
                FrameworkComponentSetRow(selectedExercise)
            }
        }
    }
}

@Composable
fun FrameworkComponentHeader(navController: NavController, muscle: String, expanded: MutableState<Boolean>) {

    val muscleGroupState by remember { mutableStateOf("$muscle") }
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
            Text(text = "  -")

            // Exercise Name
            TextButton(
                onClick = { navController.navigate("searchExercise/${muscle}")},
            ) {
                Text(text = exerciseState)
            }
    }
}

@Preview(showBackground = true)
@Composable
fun FrameworkComponentItemPreview() {
    //FrameworkComponentItem("Biceps")
}
package com.example.workout_companion.view.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.api.wger.entities.wgerExercise

@Composable
fun ExerciseRadioButtons(navController: NavController, exercises: wgerExercise,
                         muscleName: String,
                         dayId: Int,
                         selectedIndex: MutableState<Int>, selectedID: MutableState<Int>){
//create radio buttons for foods from the database
    Column(modifier = Modifier.fillMaxWidth()
        .padding(start = 20.dp)
    ){
        exercises.results.forEachIndexed {index, exercise ->
            Row(modifier = Modifier.padding(bottom= 20.dp),
                horizontalArrangement = Arrangement.Center) {
                RadioButton(
                    selected = (selectedIndex.value == index),
                    onClick = {
                        selectedID.value = exercise.id
                        selectedIndex.value = index
                    },
                    enabled = true,
                    colors = RadioButtonDefaults.colors(selectedColor = Color.Magenta)
                )
                Button(onClick = {
                    navController.navigate("ExerciseView/${muscleName}/${exercise.id}/${dayId}")
                }) {
                    Text("${exercise.name}")
                }
            }
        }
    }
}
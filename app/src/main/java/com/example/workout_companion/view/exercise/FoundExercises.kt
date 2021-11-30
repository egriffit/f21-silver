package com.example.workout_companion.view.exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

//Displays exercises found from WGER
@Composable
fun FoundExerises(navController: NavController, muscle: String?){
    Column(){
        Row(){
            Text("Found Exercises for ${muscle}")
        }
        Row(){
            Button(onClick = {navController.navigate("ExerciseOverview")}){
                Text("Submit")
            }
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Button(onClick = {navController.navigate("ExerciseOverview")}){
                Text("Cancel")
            }
        }
    }
}
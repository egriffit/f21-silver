package com.example.workout_companion.view.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


/***
 * Composable with a textField and a button. The button can be pushed and will retrieve exercises
 * from the wgerAPI that match the string provided
 *
 * It consists of:
 * a label
 * a textField,
 * a button to request data from the wgerAPI
 * a column of text labels with foods found from the API
 *
 *  @param navController, a NavController
 *  @param workout, name of workout
 *   ^^ May need to be muscle group
 */
@Composable
fun ExerciseSearchBox(navController: NavController, workout: String){
    val exercise = remember{ mutableStateOf("") }
    val selectedExerciseName = remember { mutableStateOf("")}

    Column {
        Row(        modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.width(200.dp)
            )
            {
                Text(
                    "Add Exercise:",
                    fontSize = 15.sp
                )
                TextField(value = exercise.value,
                    onValueChange = { exercise.value = it })
            }
            Row(
                modifier = Modifier.width(200.dp)
            )
            {
                Button(onClick = {

                    navController.navigate("searchExercise/${exercise.value}/${workout}")
                    // ^^ still to write, needs to look based on muscle group evidently
                     // also, what is the route param coming from?
                }) {
                    Text(
                        "Search",
                        fontSize = 15.sp
                    )
                }
            }
        }
        /*
        Testing State, delete in production
         */
        Text(selectedExerciseName.value)
    }
}
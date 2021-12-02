package com.example.workout_companion.view.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.api.wger.entities.ExerciseInfo
import com.example.workout_companion.sampleData.emptyFoodTypeEntity
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.view.nutrition.cancel
import com.example.workout_companion.viewmodel.*
import kotlinx.coroutines.*


@Composable
fun ExerciseView(
    navController: NavController,
    exerciseInfo: ExerciseInfo,
    muscleGroupName: String
){
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                //if (exerciseInfo.name != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = exerciseInfo.name,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                //}
                //if (exerciseInfo.category.name != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Category: ")
                        Spacer(modifier = Modifier.padding(start = 30.dp))
                        Text(exerciseInfo.category.name)
                    }
                //}
                //if(exerciseInfo.description!=null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = exerciseInfo.description,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
               // }
                //if(exerciseInfo.equipment!=null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        var expanded by remember { mutableStateOf(false) }  // this might be able to be
                        OutlinedButton(                                             // higher scope, controlled from
                            onClick = { expanded = !expanded }                      // other menus too
                        ) {
                            Text(text = "Equipment")
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {}
                        ) {
                            //exerciseInfo.equipment.forEach {
                            for(j in 1..4){
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ){
                                    Text(
                                        //text = it.name,
                                        text = "equipment",
                                        modifier = Modifier.padding(bottom = 20.dp)
                                    )
                                }
                            }
                        }
                    }
                //}
                //if(exerciseInfo.muscles!=null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        var expanded by remember { mutableStateOf(false) }
                        OutlinedButton(
                            onClick = { expanded = !expanded }
                        ) {
                            Text(text = "Muscles")
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {}
                        ) {
                            //exerciseInfo.muscles.forEach {
                            for (i in 1..5){
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ){
                                    Text(
                                        // text = it.name           TODO : muscle name attributes?
                                        text = "muscle name",
                                        modifier = Modifier.padding(bottom = 20.dp)
                                    )
                                }
                            }
                        }
                    }
                //}
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Button(onClick = { navController.navigate("searchExercise/${muscleGroupName}") }) {
                        Text("Cancel")
                    }
                }
            }
        })
}

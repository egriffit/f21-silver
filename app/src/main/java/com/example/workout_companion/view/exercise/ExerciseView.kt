package com.example.workout_companion.view.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.workout_companion.api.wger.Properties
import com.example.workout_companion.api.wger.entities.ExerciseInfo
import com.example.workout_companion.view.inputfields.HtmlText
import com.example.workout_companion.view.inputfields.TopNavigation


@Composable
fun ExerciseView(
    navController: NavController,
    exerciseInfo: ExerciseInfo,
    muscleGroupName: String,
    exerciseId: Int,
) {
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.padding(start = 30.dp))
                Button(onClick = { navController.navigate("searchExercise/${muscleGroupName}") }) {
                    Text("Done")
                }
            }
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .padding(50.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                //Exercise Name
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = exerciseInfo.name,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                }
                //Exercise ID
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text( text= "WGER ID: ",
                            style = MaterialTheme.typography.subtitle1,
                        )
                        Text(
                            text = "${exerciseId}",
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                }

                //category
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Category: ",
                            style = MaterialTheme.typography.subtitle1)
                        Spacer(modifier = Modifier.padding(start = 30.dp))
                        Text(exerciseInfo.category.name)
                    }
                }

                //Description
                item {
                    Row(modifier = Modifier.fillMaxWidth(),){
                        Text("Description: ",
                            style = MaterialTheme.typography.subtitle1)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        HtmlText(
                            html = exerciseInfo.description,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                }

                //image
                item {
                    if (exerciseInfo.images.isNotEmpty()) {
                        exerciseInfo.images.forEach { image ->
                            Row(modifier = Modifier.padding(bottom=20.dp),
                                horizontalArrangement = Arrangement.Center){
                                Image(
                                    painter = rememberImagePainter("${image.image}"),
                                    contentDescription = null,
                                    modifier = Modifier.size(128.dp)
                                )
                            }


                        }
                    }
                }

                //equipment
                if (exerciseInfo.equipment.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Equipment Needed:",
                                style = MaterialTheme.typography.subtitle1,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                        }
                        Row(modifier = Modifier.padding(start = 10.dp),
                            horizontalArrangement = Arrangement.Center) {
                            Column(modifier = Modifier.padding(bottom = 20.dp)){
                                exerciseInfo.equipment.forEach {
                                    if(it.name !="none (bodyweight exercise)") {
                                        Row(horizontalArrangement = Arrangement.Center){
                                            Text(it.name)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                //muscles
                if (exerciseInfo.muscles.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Primary Muscles: ",
                                style = MaterialTheme.typography.subtitle1,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                        }
                        Row(modifier = Modifier.padding(start = 10.dp),
                            horizontalArrangement = Arrangement.Center) {
                            Column(modifier = Modifier.padding(bottom = 20.dp)) {
                                exerciseInfo.muscles.forEach {
                                    Row(horizontalArrangement = Arrangement.Center){
                                        Text(it.name)
                                    }
                                }
                            }
                        }
                    }
                }

                if (exerciseInfo.muscles_secondary.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Secondary Muscles:",
                                style = MaterialTheme.typography.subtitle1,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                        }
                        Row(modifier = Modifier.padding(start = 10.dp),
                            horizontalArrangement = Arrangement.Center) {
                            Column(modifier = Modifier.padding(bottom = 20.dp)) {
                                exerciseInfo.muscles_secondary.forEach {
                                    Row(horizontalArrangement = Arrangement.Center){
                                        Text(it.name)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })
}

package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.material.Text
import androidx.compose.ui.unit.dp
import com.example.workout_companion.view.inputfields.TopNavigation


@Composable
fun RecipeView(navController: NavController, name: String?) {
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {
                    Row(modifier = Modifier.padding(start = 175.dp, bottom = 50.dp),
                        horizontalArrangement = Arrangement.Center)
                    {
                        Button(onClick = {
                            navController.navigate("nutritionOverview")
                        }){
                            Text("Done")
                        }
                    }
        },
        content = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("$name Recipe")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (name != null) {
                        RecipeSearchBox(navController = navController, recipe = name)
                    }
                }
            }
        })
}
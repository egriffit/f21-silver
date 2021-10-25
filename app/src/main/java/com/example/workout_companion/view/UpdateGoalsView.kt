package com.example.workout_companion.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.view.inputfields.TopNavigation

@Composable
fun UpdateGoalsView(navController: NavController){
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            Column(Modifier.fillMaxWidth())
            {
                Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center){
                    Text("Add/Update Goals Pages")
                }
                Spacer(modifier=Modifier.padding(top= 50.dp))
                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center){
                    Button(onClick ={ navController.navigate("userForm") }){
                        Text("User Form")
                    }
                }
            }
        }
    )
}
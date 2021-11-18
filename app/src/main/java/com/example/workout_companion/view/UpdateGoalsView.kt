package com.example.workout_companion.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.view.inputfields.MacronutrientSelector
import com.example.workout_companion.view.inputfields.TopNavigation
import androidx.compose.ui.unit.dp
import com.example.workout_companion.sampleData.FrameWorkList
import com.example.workout_companion.utility.UnitConverter
import com.example.workout_companion.view.inputfields.RecommendFrameworkView
import com.example.workout_companion.view.inputfields.userIsValid
import java.time.LocalDate

//import com.example.workout_companion.view.inputfields.submit


@Composable
fun UpdateGoalsView(navController: NavController){
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(20.dp))

                Text("Edit Macronutrient Distribution", fontSize = 20.sp)

                Spacer(modifier = Modifier.height(20.dp))

                Row() {
                    var canSubmit = remember { mutableStateOf(true) }
                    MacronutrientSelector(canSubmit)
                }

                Spacer(modifier = Modifier.height(60.dp))

                Text("Select Workout Framework", fontSize = 20.sp)

                Row() {
                    var canSubmit = remember { mutableStateOf("string") }
                    RecommendFrameworkView(
                        RecommendedFrameworks = FrameWorkList,
                        currentRecommendedFramework = canSubmit
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Submit Button
                Button(onClick = { navController.navigate("AddGoals") }) {
                    Text("Submit")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(Modifier.fillMaxWidth())
                {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Add/Update Goals Pages")
                    }
                    Spacer(modifier = Modifier.padding(top = 50.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = { navController.navigate("userForm") }) {
                            Text("User Form")
                        }
                    }
                }
            }
        }
    )
}
package com.example.workout_companion.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.view.goals.MacronutrientSelector
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.sampleData.FrameWorkList
import com.example.workout_companion.view.goals.CalorieIncrementor
import com.example.workout_companion.view.goals.GoalDropdown
import com.example.workout_companion.view.goals.RecommendFrameworkView
import com.example.workout_companion.viewmodel.CurrentUserGoalViewModel
import com.example.workout_companion.viewmodel.FrameworkTypeViewModel
import com.example.workout_companion.viewmodel.GoalTypeViewModel
import com.example.workout_companion.viewmodel.NutritionPlanTypeViewModel


@Composable
fun UpdateGoalsView(navController: NavController,
                    mutritionPlanTypeViewModel: NutritionPlanTypeViewModel,
                    frameworkTypeViewModel: FrameworkTypeViewModel,
                    goalTypeViewModel: GoalTypeViewModel,
                    currentUserGoalViewModel: CurrentUserGoalViewModel
) {
    val goals = goalTypeViewModel.allGoals.observeAsState(listOf()).value
    val selectedGoalID = remember { mutableStateOf(0) }
    val recommendedFrameworkID = remember { mutableStateOf(0) }
    val currentCalories = remember { mutableStateOf(1500) }
    val currentProtein = remember { mutableStateOf(0) }
    val currentCarbohydrates = remember { mutableStateOf(0) }
    val currentFat = remember { mutableStateOf(0) }
    val macronutrientStates = listOf(currentCarbohydrates, currentProtein, currentFat)
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            LazyColumn() {
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(20.dp))
                        //Pick a goal
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            Text("Pick a goal: ")
                            //create a list of goals
                            if (goals.isNotEmpty()) {
                                GoalDropdown(goals, selectedGoalID)
                            }
                        }
                        Spacer(modifier = Modifier.padding(bottom = 20.dp))
                    }
                }
                //Pick Calories
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                    ) {
                        Text("Set Target Calories: ")
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp)
                    ) {
                        CalorieIncrementor(currentCalories)
                    }
                    Spacer(modifier = Modifier.padding(bottom = 20.dp))
                }
                item {

                    //Pick Macronutrient composition
                    Text("Edit Macronutrient Distribution", fontSize = 20.sp)

                    Spacer(modifier = Modifier.height(20.dp))

                    Row() {
                        var canSubmit = remember { mutableStateOf(false) }
                        MacronutrientSelector(canSubmit, macronutrientStates, disabled=true)
                    }

                    Spacer(modifier = Modifier.height(60.dp))
                }
                item {
                    Text("Select Workout Framework", fontSize = 20.sp)

                    Row() {
                        RecommendFrameworkView(
                            RecommendedFrameworks = FrameWorkList,
                            currentRecommendedFramework = recommendedFrameworkID
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    // Submit Button
                    Button(onClick = { navController.navigate("Landing") }) {
                        Text("Submit")
                    }
                }

            }
        }
    )
}
//                    Column(Modifier.fillMaxWidth())
//                    {
//                        Row(
//                            Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.Center
//                        ) {
//                            Text("Add/Update Goals Pages")
//                        }
//                        Spacer(modifier = Modifier.padding(top = 50.dp))
//                        Row(
//                            Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.Center
//                        ) {
//                            Button(onClick = { navController.navigate("userForm") }) {
//                                Text("User Form")
//                            }
//                        }
//                    }

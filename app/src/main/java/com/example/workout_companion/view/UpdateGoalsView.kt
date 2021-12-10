package com.example.workout_companion.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workout_companion.entity.*
import com.example.workout_companion.sampleData.emptyNutritionPlanTypeEntity
import com.example.workout_companion.view.goals.CalorieIncrementor
import com.example.workout_companion.view.goals.GoalDropdown
import com.example.workout_companion.view.goals.MacronutrientSelector
import com.example.workout_companion.view.goals.RecommendFrameworkView
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.*
import kotlinx.coroutines.*


@Composable
fun UpdateGoalsView(navController: NavController,
                    currentUserGoalViewModel: CurrentUserGoalViewModel,
                    nutritionPlanTypeViewModel: NutritionPlanTypeViewModel,
                    frameworkTypeViewModel: FrameworkTypeViewModel,
                    user: UserEntity,
                    goals: List<GoalTypeEntity>,
) {
    val selectedGoalID = remember { mutableStateOf(0) }
    val currentCalories = remember { mutableStateOf(2000) }

    if (goals.isNotEmpty()) {
        selectedGoalID.value = user.goal_id
        val foundGoal = goals.find { it.id == selectedGoalID.value }
        if (foundGoal != null) {
            currentCalories.value = 2000 + foundGoal.caloric_addition
        } else {
            currentCalories.value = 2000 + goals[0].caloric_addition
        }
    }

    val recommendedFrameworkId = remember { mutableStateOf(1) }
    val currentProtein = remember { mutableStateOf(0) }
    val currentCarbohydrates = remember { mutableStateOf(0) }
    val currentFat = remember { mutableStateOf(0) }
    val macronutrientStates = listOf(currentCarbohydrates, currentProtein, currentFat)
    val allFrameworks = frameworkTypeViewModel.readAll.observeAsState(listOf())
    val recommendedFrameworks = frameworkTypeViewModel.getFrameworksWithinMaxWorkouts(user.max_workouts_per_week).observeAsState(listOf())
    if (recommendedFrameworks.value.isNotEmpty()) {
        recommendedFrameworkId.value = recommendedFrameworks.value[0].id
    }

    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            LazyColumn {
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(20.dp))
                        //Pick a goal
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            Button(onClick = { navController.navigate("userForm") }) {
                                Text("Update User")
                            }
                        }
                        Spacer(modifier = Modifier.padding(bottom = 20.dp))
                    }
                }
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

                    Row {
                        val canSubmit = remember { mutableStateOf(false) }
                        MacronutrientSelector(canSubmit, macronutrientStates, disabled = true)
                    }

                    Spacer(modifier = Modifier.height(60.dp))
                }
                item {
                    Text("Select Workout Framework", fontSize = 20.sp)

                    Row {
                            if (allFrameworks.value.isNotEmpty()) {
                                RecommendFrameworkView(
                                    allFrameworks.value,
                                    recommendedFrameworkId,
                                )
                            }
                    }
                    Text("Framework Value: ${recommendedFrameworkId.value}")
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    // Submit Button
                    Button(onClick = {
                        runBlocking {
                            val nutritionPlanTypeId = 1
                            val nutritionPlan = NutritionPlanTypeEntity(
                                nutritionPlanTypeId,
                                selectedGoalID.value,
                                currentCalories.value.toDouble(),
                                macronutrientStates.elementAt(0).value.toDouble(),
                                macronutrientStates.elementAt(1).value.toDouble(),
                                macronutrientStates.elementAt(2).value.toDouble()
                            )
                            val nutritionJob = nutritionPlanTypeViewModel.addPlan(nutritionPlan)

                            // We need the nutrition plan stored before we can create the user goals
                            nutritionJob.join()

                            val userGoal = CurrentUserGoalEntity(
                                1,
                                nutritionPlanTypeId,
                                recommendedFrameworkId.value
                            )
                            val userGoalJob =
                                currentUserGoalViewModel.addCurrentUserGoal(userGoal)

                            // For now, it might slow us down, but let's wait for our user goal
                            // to be made before letting the user run amuck
                            userGoalJob.join()

                            navController.navigate("Landing")
                        }
                    }) {
                        Text("Submit")
                    }
                }

            }
        }
    )
}


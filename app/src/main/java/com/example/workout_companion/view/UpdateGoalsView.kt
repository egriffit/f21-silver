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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workout_companion.entity.CurrentUserGoalEntity
import com.example.workout_companion.entity.NutritionPlanTypeEntity
import com.example.workout_companion.view.goals.CalorieIncrementor
import com.example.workout_companion.view.goals.GoalDropdown
import com.example.workout_companion.view.goals.MacronutrientSelector
import com.example.workout_companion.view.goals.RecommendFrameworkView
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.*
import kotlinx.coroutines.*


@Composable
fun UpdateGoalsView(navController: NavController,
                    frameworkTypeViewModel: FrameworkTypeViewModel,
                    goalTypeViewModel: GoalTypeViewModel,
                    currentUserGoalViewModel: CurrentUserGoalViewModel,
                    userViewModel: UserViewModel,
                    nutritionPlanTypeViewModel: NutritionPlanTypeViewModel
) {
    val goals = goalTypeViewModel.allGoals.observeAsState(listOf()).value
    val user = userViewModel.user.observeAsState().value
    val maxWorkouts = user?.max_workouts_per_week
    val selectedGoalID = remember { mutableStateOf(0) }
    val selectedGoal = remember {mutableStateOf("")}
    if(goals.isNotEmpty()){
        if(selectedGoalID.value != 0){
            selectedGoal.value = goals[selectedGoalID.value - 1].goal
        }
        else{
            selectedGoal.value = goals[0].goal
        }
    }

    val recommendedFrameworkId = remember { mutableStateOf(0) }
    val currentCalories = remember { mutableStateOf(1500) }
    val currentProtein = remember { mutableStateOf(0) }
    val currentCarbohydrates = remember { mutableStateOf(0) }
    val currentFat = remember { mutableStateOf(0) }
    val macronutrientStates = listOf(currentCarbohydrates, currentProtein, currentFat)
    val frameworks = maxWorkouts?.let {
        frameworkTypeViewModel.getFrameworksWithGoalNameWithinMaxWorkouts(selectedGoal.value,
            it
        ).observeAsState(listOf()).value
    }
    var nutritionPlanId = nutritionPlanTypeViewModel.id.observeAsState().value
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            LazyColumn{
                item{
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(20.dp))
                        //Pick a goal
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            Button(onClick = {navController.navigate("userForm")}){
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
                        MacronutrientSelector(canSubmit, macronutrientStates, disabled=true)
                    }

                    Spacer(modifier = Modifier.height(60.dp))
                }
                item {
                    Text("Select Workout Framework", fontSize = 20.sp)

                    Row {
                        if (maxWorkouts != null) {
                            if (frameworks != null) {
                                RecommendFrameworkView(
                                    RecommendedFrameworks = frameworks,
                                    currentRecommendedFramework = recommendedFrameworkId,
                                    frameworkTypeViewModel,
                                    selectedGoal.value,
                                    maxWorkouts
                                )
                            }
                        }
                    }
                    Text("Framework Value: ${recommendedFrameworkId.value}")
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    // Submit Button
                    Button(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            //create a nutrition plan
                            val nutritionPlan = NutritionPlanTypeEntity(
                                1,
                                selectedGoalID.value,
                                currentCalories.value.toDouble(),
                                macronutrientStates.elementAt(0).value.toDouble(),
                                macronutrientStates.elementAt(1).value.toDouble(),
                                macronutrientStates.elementAt(2).value.toDouble()
                            )

                            nutritionPlanTypeViewModel.addPlan(nutritionPlan)

                            val nutritionPlanTypeId: Int? =
                                withContext(Dispatchers.IO) {
                                    nutritionPlanTypeViewModel.getPlanId(nutritionPlan)
                                    nutritionPlanTypeViewModel.id.value
                                }
                            if(nutritionPlanTypeId != null){
                                val userGoal = CurrentUserGoalEntity(9,
                                    nutritionPlanTypeId,
                                    recommendedFrameworkId.value )
                                currentUserGoalViewModel.addCurrentUserGoal(userGoal)
                            }
                        }
                        navController.navigate("Landing")
                    }) {
                        Text("Submit")
                    }
                }

            }
        }
    )
}


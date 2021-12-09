package com.example.workout_companion.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.view.assessment.AdviceList
import com.example.workout_companion.view.assessment.DeloadRecommender
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.AdviceAPIViewModel
import com.example.workout_companion.viewmodel.CurrentUserGoalViewModel
import androidx.compose.material.Text
import androidx.compose.runtime.livedata.observeAsState
import com.example.workout_companion.viewmodel.NutritionStatusViewModel
import com.example.workout_companion.viewmodel.WorkoutViewModel
import kotlinx.coroutines.*
import java.time.LocalDate

@Composable
fun AssessmentView(navController: NavController,
                    currentUserGoalViewModel: CurrentUserGoalViewModel,
                   adviceAPIViewModel: AdviceAPIViewModel,
                   nutritionStatusViewModel: NutritionStatusViewModel,
                   workoutViewModel: WorkoutViewModel
                   ){
    var workoutGoal = ""
    val currentGoals = currentUserGoalViewModel.getCurrentGoals.observeAsState().value
    val nutritionStatus = nutritionStatusViewModel.getStatusByDate(LocalDate.now()).observeAsState().value
    val workoutStatus = workoutViewModel.getWorkoutOnDate(LocalDate.now()).observeAsState().value

    if(currentGoals?.FrameWorkWithGoalEntity?.goal != null){
        workoutGoal = currentGoals.FrameWorkWithGoalEntity.goal
    }


    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            Column{
                Spacer(modifier = Modifier.padding(top=30.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                    Text("Assessment View")
                }
                Spacer(modifier = Modifier.padding(top=30.dp))
                    DeloadRecommender(currentUserGoalViewModel)
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)){
                        Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center){
                            Text("Today's Nutrition Status: ")
                            if(nutritionStatus != null){
                                Text("${nutritionStatus.status.descName}")
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center){
                            Text("Today's Workout Status: ")
                            if(workoutStatus != null){
                                Text("${workoutStatus.status.descName}")
                            }else{
                                Text("Not Started")
                            }
                        }
                    }
                    AdviceList(workoutGoal, adviceAPIViewModel)
                }
        }
    )
}
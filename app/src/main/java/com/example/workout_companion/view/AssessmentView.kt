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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.toLowerCase
import com.example.workout_companion.entity.CurrentNutritionPlanAndFrameworkEntity
import com.example.workout_companion.entity.CurrentUserGoalEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Composable
fun AssessmentView(navController: NavController,
                    currentUserGoalViewModel: CurrentUserGoalViewModel,
                   adviceAPIViewModel: AdviceAPIViewModel
                   ){
    var workoutGoal = ""
    val currentGoals = currentUserGoalViewModel.getCurrentGoals.observeAsState().value
    if(currentGoals?.FrameWorkWIthGoalEntity?.goal != null){
        workoutGoal = currentGoals.FrameWorkWIthGoalEntity.goal
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
                    AdviceList(workoutGoal, adviceAPIViewModel)
                }
        }
    )
}
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

@Composable
fun AssessmentView(navController: NavController,
                    currentUserGoalViewModel: CurrentUserGoalViewModel,
                   adviceAPIViewModel: AdviceAPIViewModel
                   ){
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
                    AdviceList(currentUserGoalViewModel, adviceAPIViewModel)
                }
        }
    )
}
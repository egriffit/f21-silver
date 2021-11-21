package com.example.workout_companion.view.assessment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.workout_companion.viewmodel.AdviceAPIViewModel
import com.example.workout_companion.viewmodel.CurrentUserGoalViewModel
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ExpandMore
import androidx.compose.material.icons.sharp.NavigateNext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text


@Composable
fun AdviceList(currentUserGoalViewModel: CurrentUserGoalViewModel, adviceAPIViewModel: AdviceAPIViewModel){
    val currentGoals  = currentUserGoalViewModel.getCurrentGoals.value
    val showAdvice = remember{ mutableStateOf(false)}
    var workoutGoal = ""
    if(currentGoals != null){
        workoutGoal = currentGoals.FrameWorkWIthGoalEntity.goal
    }
    if(workoutGoal.isNotEmpty()){
        adviceAPIViewModel.getAdviceByType(workoutGoal)
    }
    val foundAdvice = adviceAPIViewModel.advice
    Column(){
        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = { showAdvice.value = !showAdvice.value },
                modifier = Modifier.background(color = Color.LightGray),
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.LightGray)
            ) {
                if (showAdvice.value) {
                    Icon(
                        Icons.Sharp.ExpandMore,
                        contentDescription = "",
                        modifier = Modifier.background(color = Color.LightGray)
                            .size(20.dp),
                        tint = Color.Black
                    )
                } else {
                    Icon(
                        Icons.Sharp.NavigateNext,
                        contentDescription = "",
                        modifier = Modifier.background(color = Color.LightGray)
                            .size(20.dp),
                        tint = Color.Black
                    )
                }
            }
            Text("Advice")
        }
        Row{
            if(showAdvice.value){

                LazyColumn{
                    if(foundAdvice.size >0) {
                        foundAdvice.forEach { f ->
                            item {
                                Text(f.advice)
                            }
                        }
                    }else{
                        item{
                            Text("No advice found")
                        }
                    }
                }
            }
        }
    }
}
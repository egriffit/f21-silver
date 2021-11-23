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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.toLowerCase
import com.example.workout_companion.entity.CurrentNutritionPlanAndFrameworkEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


@Composable
fun AdviceList(workoutGoal: String, adviceAPIViewModel: AdviceAPIViewModel){

    val showAdvice = remember{ mutableStateOf(false)}
    adviceAPIViewModel.getAdviceByType(workoutGoal.lowercase().trim())
    val foundAdvice = adviceAPIViewModel.advice
    Column(){
        Row(modifier = Modifier.fillMaxWidth()
            .padding(start=20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.Start)
        {
            Text("Workout Goal: $workoutGoal")
        }
        Row(modifier = Modifier.fillMaxWidth()
            .padding(start=20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.Start) {
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
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text("Advice")
        }
        Row{
            if(showAdvice.value){

                LazyColumn{
                    if(foundAdvice.size >0) {
                        foundAdvice.forEachIndexed { index, f ->
                            item {
                                Row(modifier = Modifier.fillMaxWidth()
                                    .padding(start=20.dp, end = 20.dp),
                                    horizontalArrangement = Arrangement.Start)
                                {
                                    Text("${index + 1}. ${f.advice}")
                                }
                            }
                        }
                    }else{
                        item(){
                            Text("No advice found")
                        }
                    }
                }
            }
        }
    }
}
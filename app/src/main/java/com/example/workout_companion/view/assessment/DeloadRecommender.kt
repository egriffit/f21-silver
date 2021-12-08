package com.example.workout_companion.view.assessment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.workout_companion.viewmodel.CurrentUserGoalViewModel
import androidx.compose.material.Text

@Composable
fun DeloadRecommender(currentUserGoalViewModel: CurrentUserGoalViewModel){
    val currentGoals  = currentUserGoalViewModel.getCurrentGoals.value
    val showCalAdvice = remember{ mutableStateOf(true) }
    var workoutGoal = ""
    if(currentGoals != null) {
        workoutGoal = currentGoals.FrameWorkWithGoalEntity.goal
    }
    if (workoutGoal === "gain strength") {
            //check if progress being made using view models that do not exist yet
            //if true do nothing
            //if false
            //Check if deload in last 6 weeks
            //if true, show deload button
            //if false
            //check if calories is less than 3000 and date has been more than at least a week
            if (currentGoals != null) {
                if (currentGoals.nutritionPlanType.calorie < 3000 && showCalAdvice.value) {
                    Column() {
                        Text("A slight increase in calories may help with promoting strength.")
                        Row() {
                            Button(onClick = {
                                currentUserGoalViewModel.updateNutritionPlanAndFramework(
                                    currentGoals.FrameWorkWithGoalEntity.name,
                                    currentGoals.nutritionPlanType.calorie + 100.0,
                                    currentGoals.nutritionPlanType.carbohydrate,
                                    currentGoals.nutritionPlanType.protein,
                                    currentGoals.nutritionPlanType.fat
                                )
                                showCalAdvice.value = false
                            })
                                {
                                    Text("Add 100 Calories")
                                }
                                // track the last caloric increase in database
                            }
                        }
                    }
                }
            }
        }
package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import com.example.workout_companion.viewmodel.CurrentUserGoalViewModel
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workout_companion.viewmodel.MealViewModel
import java.time.LocalDate

@Composable
fun NutritionStatus(
    currentUserGoalViewModel: CurrentUserGoalViewModel,
    mealViewModel: MealViewModel
) {
    val currentGoals = currentUserGoalViewModel.getCurrentGoals.value
    var currentCal = 0
    var currentProtein = 0.0
    var currentCarbs = 0.0
    var currentFat = 0.0
    mealViewModel.getMealsByDate(LocalDate.now())?.forEach {
        currentCal += it.calories.toInt()
        currentProtein += it.protein
        currentCarbs += it.carbohydrates
        currentFat += it.fat
    }
    Column(modifier = Modifier.padding(bottom = 30.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Calories:   ")
            Text("$currentCal Cal/")
            if (currentGoals?.nutritionPlanType?.calorie != null)
                Text("$currentGoals.nutritionPlanType.calorie} Cal")
            else {
                Text("--- Cal")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Carbohydrates:   ")
            Text("$currentCarbs g/")
            if (currentGoals?.nutritionPlanType?.carbohydrate != null)
                Text("${currentGoals.nutritionPlanType.carbohydrate} g")
            else {
                Text("--- g")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Protein:   ")
            Text("$currentProtein g/")
            //goal target
            if (currentGoals?.nutritionPlanType?.protein != null)
                Text("${currentGoals.nutritionPlanType.protein} g")
            else {
                Text("--- g")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Fat:   ")
            Text("$currentFat g /")
            if (currentGoals?.nutritionPlanType?.fat != null)
                Text("${currentGoals.nutritionPlanType.fat} g")
            else {
                Text("--- g")
            }
        }
    }
}
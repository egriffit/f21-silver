package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import com.example.workout_companion.viewmodel.CurrentUserGoalViewModel
import androidx.compose.material.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workout_companion.viewmodel.MealViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.text.DecimalFormat

@Composable
fun NutritionStatus(
    currentUserGoalViewModel: CurrentUserGoalViewModel,
    mealViewModel: MealViewModel
) {
    val currentGoals = currentUserGoalViewModel.getCurrentGoals.observeAsState().value
    val foundMeals = mealViewModel.getTodaysMeals.observeAsState(listOf()).value
    var currentCal = 0.0
    var currentProtein = 0.0
    var currentCarbs =  0.0
    var currentFat =  0.0
    var gramsOfFood = 0.0
    var proteinPercentage = "0.0"
    var carbPercentage = "0.0"
    var fatPercentage = "0.0"
    var df: DecimalFormat = DecimalFormat("##.##%")

    foundMeals.forEach {
        currentCal += it.calories
        currentProtein += it.protein
        currentCarbs += it.carbohydrates
        currentFat += it.fat
        gramsOfFood = currentProtein + currentCarbs + currentFat
        carbPercentage = df.format(currentCarbs / gramsOfFood)
        proteinPercentage = df.format(currentProtein/gramsOfFood)
        fatPercentage = df.format(currentFat / gramsOfFood)
    }
    Column(modifier = Modifier.padding(bottom = 30.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Status:   ")
           if(currentGoals != null) {
               if (currentCal > currentGoals?.nutritionPlanType?.calorie!!) {
                   Text("Not Meeting Calorie Goal")
               } else {
                   Text("On Track")
               }
           }
        }
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Calories:   ")
            Text("${currentCal.toInt()} Cal / ")
            if (currentGoals?.nutritionPlanType?.calorie != null)
                Text("${currentGoals.nutritionPlanType.calorie} Cal")
            else {
                Text("--- Cal")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Carbohydrates:   ")
            Text("${currentCarbs.toInt()} g / ")
            if (currentGoals?.nutritionPlanType?.carbohydrate != null) {
                var targetCarbGrams =
                    (currentGoals?.nutritionPlanType?.calorie!! * (currentGoals?.nutritionPlanType?.carbohydrate!!/100)).toInt() / 4.0
                Text("${targetCarbGrams} g ")
                //Text("(${df.format(currentGoals?.nutritionPlanType?.carbohydrate!! /100)})")
            }else {
                Text("--- %")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Protein:   ")
            Text("${currentProtein.toInt()} g / ")
            //goal target
            if (currentGoals?.nutritionPlanType?.protein != null){
                var targetProteinGrams = (currentGoals?.nutritionPlanType?.calorie!! * (currentGoals?.nutritionPlanType?.protein!!/100)).toInt() /4.0
                Text("${targetProteinGrams.toInt()} g")
                //Text("(${df.format(currentGoals?.nutritionPlanType?.protein!! /100)})")

            } else {
                Text("--- %")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Fat:   ")
            Text("${currentFat.toInt()} g/")
            if (currentGoals?.nutritionPlanType?.fat != null) {
                var targetFatGrams = (currentGoals?.nutritionPlanType?.calorie!! * (currentGoals?.nutritionPlanType?.fat!! / 100)).toInt() /9.0
                Text("${targetFatGrams.toInt()} g")
                //Text("(${df.format(currentGoals?.nutritionPlanType?.fat!!/100)})")
            }else {
                Text("--- %")
            }
        }
    }
}
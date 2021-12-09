package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import com.example.workout_companion.viewmodel.CurrentUserGoalViewModel
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.test.core.app.ActivityScenario.launch
import com.example.workout_companion.entity.AllMealsInDay
import com.example.workout_companion.enumeration.NutritionStatusEnum
import com.example.workout_companion.viewmodel.MealViewModel
import com.example.workout_companion.viewmodel.NutritionStatusViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

@Composable
fun NutritionStatus(
    currentUserGoalViewModel: CurrentUserGoalViewModel,
    mealViewModel: MealViewModel,
    nutritionStatusViewModel: NutritionStatusViewModel
) {
    val currentGoals = currentUserGoalViewModel.getCurrentGoals.observeAsState().value
    val currentNutritionStatus = nutritionStatusViewModel.currentStatus.observeAsState().value
    val planStatus = remember { mutableStateOf(NutritionStatusEnum.ON_TRACK) }
    var dailyTotals = AllMealsInDay(0.0, 0.0, 0.0, 0.0, LocalDate.now())

    runBlocking {
        var totalsInDb: AllMealsInDay? = null
        val job1: Job = launch(Dispatchers.IO) {
            totalsInDb = mealViewModel.calcDailyTotal()
        }
        job1.join()
        if(totalsInDb != null) {
            dailyTotals = totalsInDb!!
            launch(Dispatchers.IO) {
                nutritionStatusViewModel.getStatusByDate(LocalDate.now())
                if (currentNutritionStatus != null) {
                    planStatus.value = currentNutritionStatus.status
                }
            }
        }
    }
    if(currentGoals != null) {
        if (dailyTotals.calories.toInt() > currentGoals.nutritionPlanType.calorie) {
            planStatus.value = NutritionStatusEnum.ABOVE_TARGET
            nutritionStatusViewModel.insert(
                        NutritionStatusEnum.ABOVE_TARGET,
                        LocalDate.now()
                    )
        }else{
            nutritionStatusViewModel.insert(NutritionStatusEnum.ON_TRACK, LocalDate.now())
        }
    }

    if (dailyTotals != null) {
        Column(modifier = Modifier.padding(bottom = 30.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Status:${planStatus.value.descName}")
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Calories:   ")
                Text("${dailyTotals.calories.toInt()} Cal / ")
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
                Text("${dailyTotals.carbohydrates.toInt()} g / ")
                if (currentGoals?.nutritionPlanType?.carbohydrate != null) {
                    val targetCarbGrams =
                        (currentGoals.nutritionPlanType.calorie * (currentGoals.nutritionPlanType.carbohydrate / 100)).toInt() / 4.0
                    Text("$targetCarbGrams g ")
                    //Text("(${df.format(currentGoals?.nutritionPlanType?.carbohydrate!! /100)})")
                } else {
                    Text("--- g")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Protein:   ")
                Text("${dailyTotals.protein.toInt()} g / ")
                //goal target
                if (currentGoals?.nutritionPlanType?.protein != null) {
                    val targetProteinGrams =
                        (currentGoals.nutritionPlanType.calorie * (currentGoals.nutritionPlanType.protein / 100)).toInt() / 4.0
                    Text("${targetProteinGrams.toInt()} g")
                    //Text("(${df.format(currentGoals?.nutritionPlanType?.protein!! /100)})")

                } else {
                    Text("--- g")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Fat:   ")
                Text("${dailyTotals.fat.toInt()} g/")
                if (currentGoals?.nutritionPlanType?.fat != null) {
                    val targetFatGrams =
                        (currentGoals.nutritionPlanType.calorie * (currentGoals.nutritionPlanType.fat / 100)).toInt() / 9.0
                    Text(" ${targetFatGrams.toInt()} g")
                    //Text("(${df.format(currentGoals?.nutritionPlanType?.fat!!/100)})")
                } else {
                    Text("--- g")
                }
            }
        }
    }
}
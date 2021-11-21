package com.example.workout_companion.view.goals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.workout_companion.viewmodel.Incrementer

@Composable
fun CalorieIncrementor(currentCalories: MutableState<Int>) {
    var maxVal = 4000
    val minVal = 1000
    val step = 50
    Incrementer(label = "Calories", fieldState = currentCalories, step = step, min = minVal, max = maxVal )
}
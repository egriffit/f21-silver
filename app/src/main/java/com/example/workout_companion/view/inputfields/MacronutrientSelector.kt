package com.example.workout_companion.view.inputfields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.workout_companion.viewmodel.IncrementerGroup

@Composable
fun MacronutrientSelector(canSubmit: MutableState<Boolean>){
    val labels = listOf("Carbohydrate", "Protein", "Fat")
    val startingValues = listOf(40, 35, 25)
    var fieldStates = listOf(
        remember { mutableStateOf(startingValues[0]) },
        remember { mutableStateOf(startingValues[1]) },
        remember { mutableStateOf(startingValues[2]) }
    )
    IncrementerGroup(labels, startingValues, fieldStates, canSubmit)
}
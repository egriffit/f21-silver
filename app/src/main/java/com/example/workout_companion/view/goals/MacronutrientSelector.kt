package com.example.workout_companion.view.goals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.workout_companion.view.inputfields.IncrementerGroup

@Composable
fun MacronutrientSelector(canSubmit: MutableState<Boolean>,
                          fieldStates: List<MutableState<Int>>,
                          disabled: Boolean){
    val labels = listOf("Carbohydrate", "Protein", "Fat")
    val startingValues = listOf(40, 35, 25)
    startingValues.forEachIndexed{index, value ->
        fieldStates.elementAt(index).value = value
    }
//    var fieldStates = listOf(
//        remember { mutableStateOf(startingValues[0]) },
//        remember { mutableStateOf(startingValues[1]) },
//        remember { mutableStateOf(startingValues[2]) }
//    )
    IncrementerGroup(labels, startingValues, fieldStates, canSubmit, disabled)
}
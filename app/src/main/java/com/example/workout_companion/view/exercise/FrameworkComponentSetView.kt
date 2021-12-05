package com.example.workout_companion.view.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.workout_companion.entity.WorkoutComponentSetEntity
import com.example.workout_companion.viewmodel.WorkoutComponentSetViewModel
import com.example.workout_companion.enumeration.Progress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun FrameworkComponentSetRow(
    exerciseId: Int,
    workoutComponentId: Int,
    workoutComponentSetViewModel: WorkoutComponentSetViewModel,
    workoutComponentSetEntity: WorkoutComponentSetEntity
) {

    // NOTE: Keep these items as the string versions here. It makes your life easier
    // Worry about conversion to the proper type when writing stuff to the database
    // TODO: remember the progress state of the set entity
    var checkState = remember { mutableStateOf(false) }
    // TODO: remember the weight of the given set entity
    var weightState = remember { mutableStateOf("0.0") }
    // TODO: remember the reps of the given set entity
    var setState = remember { mutableStateOf("0") }

    val focusManager = LocalFocusManager.current
    if(workoutComponentSetEntity.status != Progress.NOT_STARTED){
        checkState.value = workoutComponentSetEntity.status == Progress.COMPLETE
    }
    // TODO: remember the weight of the given set entity
    if(workoutComponentSetEntity.weight != 0.0){
        weightState.value = workoutComponentSetEntity.weight.toString()
    }
    // TODO: remember the reps of the given set entity
    if(workoutComponentSetEntity.reps != 0){
        setState.value = workoutComponentSetEntity.reps.toString()
    }
    var completionStatus: Progress

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(12.dp)
    ) {
        // Progress state check box
        Checkbox(
            checked = checkState.value,
            // TODO: write the new state to the database with the view model
            onCheckedChange = {
                checkState.value = it
                focusManager.clearFocus()
                if(exerciseId != 0){
                    if(!checkState.value){
                        completionStatus = Progress.IN_PROGRESS
                    }else{
                        completionStatus = Progress.COMPLETE
                    }
                    val set = WorkoutComponentSetEntity(0, workoutComponentId, setState.value.toInt(), weightState.value.toDouble(), completionStatus, exerciseId)
                    runBlocking {
                        launch(Dispatchers.IO){
                            workoutComponentSetViewModel.addSet(set)
                        }
                    }
                }
            },
            modifier = Modifier.weight(1f)
        )

        // Weight Input Field
        OutlinedTextField(
            value = weightState.value,
            // TODO: write the new weight state to the database with the view model
            onValueChange = { newValue ->
                if (newValue.isEmpty() or (newValue.toDoubleOrNull() != null) ) {
                    weightState.value = newValue
                }
            },
            enabled = !checkState.value,
            singleLine = true,
            textStyle = TextStyle(textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.moveFocus(FocusDirection.Right)
            }),
            modifier = Modifier.weight(2f)
        )

        // Weight Units Label
        Text(
            text = "lb",
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(2f)
        )

        // Reps Input Field
        OutlinedTextField(
            value = setState.value,
            onValueChange = { newValue ->
                setState.value = newValue.filter { it.isDigit() }
            },
            enabled = !checkState.value,
            singleLine = true,
            textStyle = TextStyle(textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            modifier = Modifier.weight(2f)
        )

        // Reps label
        Text(
            text = "reps",
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(2f)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TestingPreview() {
//    //val exercise = remember {mutableStateOf("Push-Up")}
//    val exerciseId = 1
//    val frameworkId = 1
//
//    val workoutComponentSetViewModel by lazy { viewModelProvider.get(WorkoutComponentSetViewModel::class.java)}
//
//    FrameworkComponentSetRow(exercise)
//}
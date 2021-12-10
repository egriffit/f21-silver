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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workout_companion.entity.WorkoutComponentSetEntity
import com.example.workout_companion.enumeration.Progress
import com.example.workout_companion.viewmodel.WorkoutViewModel

@Composable
fun WorkoutComponentSetView(set: WorkoutComponentSetEntity, workoutCompleted: Boolean, workoutViewModel: WorkoutViewModel) {

    // NOTE: Keep these items as the string versions here. It makes your life easier
    // Worry about conversion to the proper type when writing stuff to the database
    var checkState by remember { mutableStateOf(set.status == Progress.COMPLETE) }
    var weightState by remember { mutableStateOf(set.weight.toString()) }
    var repState by remember { mutableStateOf(set.reps.toString()) }

    val focusManager = LocalFocusManager.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(12.dp)
    ) {
        // Progress state check box
        Checkbox(
            checked = checkState,
            enabled = !workoutCompleted,
            onCheckedChange = { checked ->
                checkState = checked
                if (checked) {
                    if (repState.toIntOrNull() != null) {
                        set.reps = repState.toInt()
                    }
                    if (weightState.toDoubleOrNull() != null) {
                        set.weight = weightState.toDouble()
                    }
                    workoutViewModel.completeWorkoutSet(set)
                }
                else {
                    workoutViewModel.unlockWorkoutSet(set)
                }

                focusManager.clearFocus()
            },
            modifier = Modifier.weight(1f)
        )

        // Weight Input Field
        OutlinedTextField(
            value = weightState,
            onValueChange = { newValue ->
                if (newValue.isEmpty() or (newValue.toDoubleOrNull() != null) ) {
                    weightState = newValue
                }
            },
            enabled = !checkState && !workoutCompleted,
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
            value = repState,
            onValueChange = { newValue ->
                repState = newValue.filter { it.isDigit() }
            },
            enabled = !checkState && !workoutCompleted,
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
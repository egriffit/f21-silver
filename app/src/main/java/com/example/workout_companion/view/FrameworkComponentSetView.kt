package com.example.workout_companion.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
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

@Composable
fun FrameworkComponentSetRow() {

    // NOTE: Keep these items as the string versions here. It makes your life easier
    // Worry about conversion to the proper type when writing stuff to the database
    // TODO: remember the progress state of the set entity
    var checkState by remember { mutableStateOf(false) }
    // TODO: remember the weight of the given set entity
    var weightState by remember { mutableStateOf("0.0") }
    // TODO: remember the reps of the given set entity
    var setState by remember { mutableStateOf("0") }

    val focusManager = LocalFocusManager.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(12.dp)
    ) {
        // Progress state check box
        Checkbox(
            checked = checkState,
            // TODO: write the new state to the database with the view model
            onCheckedChange = {
                checkState = it
                focusManager.clearFocus()
            },
            modifier = Modifier.weight(1f)
        )

        // Weight Input Field
        OutlinedTextField(
            value = weightState,
            // TODO: write the new weight state to the database with the view model
            onValueChange = { newValue ->
                if (newValue.isEmpty() or (newValue.toDoubleOrNull() != null) ) {
                    weightState = newValue
                }
            },
            enabled = !checkState,
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
            value = setState,
            onValueChange = { newValue ->
                setState = newValue.filter { it.isDigit() }
            },
            enabled = !checkState,
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

@Preview(showBackground = true)
@Composable
fun TestingPreview() {
    FrameworkComponentSetRow()
}
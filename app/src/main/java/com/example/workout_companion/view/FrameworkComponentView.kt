package com.example.workout_companion.view

import android.widget.CheckBox
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FrameworkComponentItem() {
    Column {

        var expandedState by remember { mutableStateOf(true) }

        FrameworkComponentHeader()
        // TODO: load each set found in the database here
        for (i in 1..3) {
            FrameworkComponentSetRow()
        }
    }
}

@Composable
fun FrameworkComponentHeader() {

    val muscleGroupState by remember { mutableStateOf("Sample Muscle") }
    val exerciseState by remember { mutableStateOf("Pick an Exercise") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Drop Down Icon
        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Localized Description")

        // Muscle Group Name
        Text(text = muscleGroupState)
        
        // Spacer
        Text(text = "  -")
        
        // Exercise Name
        TextButton(
            onClick = { /*TODO: Bring up exercise selection page*/ },
            ) {
            Text(text = exerciseState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FrameworkComponentItemPreview() {
    FrameworkComponentItem()
}
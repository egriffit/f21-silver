package com.example.workout_companion.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.workout_companion.view.inputfields.MacronutrientSelector
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.lang.reflect.Modifier


@Composable
fun AddGoalView(navController: NavController) {
    Column {
        Text("Add Goals", textAlign = TextAlign.Right, fontSize = 30.sp)

        Row() {
            var canSubmit =  remember { mutableStateOf(true) }
            MacronutrientSelector(canSubmit)
        }

        Row() {
            var canSubmit =  remember { mutableStateOf(true) }
            MacronutrientSelector(canSubmit)
        }
    }
}
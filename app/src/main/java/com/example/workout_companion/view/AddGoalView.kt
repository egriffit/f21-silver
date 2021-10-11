package com.example.workout_companion.view

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.workout_companion.viewmodel.MacronutrientSelector

@Composable
fun AddGoalView(navController: NavController){
    Row(){
        Text("Add Goals View")
    }

    MacronutrientSelector(canSubmit = true)

}
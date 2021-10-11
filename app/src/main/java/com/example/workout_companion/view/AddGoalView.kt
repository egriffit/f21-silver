package com.example.workout_companion.view

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun AddGoalView(navController: NavController){
    Row(){
        Text("Add Goals View")
    }

    Row(){
        Text("Select Nutrition Plan")
    }

    Row(){
        Text("Select Workout Plan")
    }

}
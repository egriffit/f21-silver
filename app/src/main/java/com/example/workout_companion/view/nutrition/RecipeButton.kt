package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material.Text


@Composable
fun RecipeButton(navController: NavController){
    Row{
        Button(
            onClick={
                navController.navigate("addRecipeForm")
            }
        ){
           Text("Create Recipe")
        }
    }
}
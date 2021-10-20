package com.example.workout_companion.view.inputfields

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import com.example.workout_companion.R
import kotlinx.coroutines.NonDisposableHandle.parent

@Composable
fun TopNavigation(navController: NavController){
    Row(modifier = Modifier.fillMaxWidth()
        .padding(start = 20.dp, end=20.dp)){
        Column(modifier = Modifier.width(100.dp)){
            IconButton(onClick = { navController.navigate("ExerciseOverview")
            }) {
                Icon(Icons.Filled.FitnessCenter, "Exercise Overview")
            }
            Text("Exercise Overview")
        }
        Column(modifier = Modifier.width(100.dp)){
            IconButton(onClick = {navController.navigate("NutritionOverview") }) {
                Icon(Icons.Filled.Restaurant, "Exercise Overview")
            }
            Text("Nutrition Overview")
        }
        Column(modifier = Modifier.width(75.dp)){
            IconButton(onClick = {navController.navigate("UpdateGoals") }) {
                Icon(Icons.Filled.EmojiEvents, "Update Goals")
            }
            Text("Update Goals")
        }
        Column(modifier = Modifier.width(100.dp)){
            IconButton(onClick = {navController.navigate("Assessment") }) {
                Icon(Icons.Filled.Lightbulb, "Assessment")
            }
            Text("Status")
        }

    }
}
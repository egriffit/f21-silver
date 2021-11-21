package com.example.workout_companion.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.view.inputfields.TopNavigation

@Composable
fun LandingPage(navController: NavController){
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            Column(modifier = Modifier.fillMaxHeight()){
            MyButton(navController)
            MyButton2(navController)
            MyButton3(navController)
        } }
    )

}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MaterialTheme {
//        // function to display its preview.
//        MyButton(); MyButton2(); MyButton3();
//    }
//}

@Composable
fun MyButton(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),

// Modifies the button to be aligned properly
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val context = LocalContext.current

        Button(

            onClick = { navController.navigate("UpdateGoals") },
            modifier = Modifier.padding(all = Dp(10F)),
            enabled = true,
            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
            shape = MaterialTheme.shapes.large,
            //Makes the button clickable
        )
        {
            Text(text = "Fitness Goals", color = Color.White)
            //Adds the button text
        }
    }
}

@Composable
fun MyButton2(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val context = LocalContext.current

        // below line is use to create a button.
        Button(

            onClick = { navController.navigate("ExerciseOverview") },
            modifier = Modifier.padding(all = Dp(10F)),
            enabled = true,
            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
            shape = MaterialTheme.shapes.medium,
        )

        {
            Text(text = "Today's Workout", color = Color.White)
        }
    }
}

@Composable
fun MyButton3(navController: NavController) {
    Column(

        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val context = LocalContext.current

        Button(
            onClick = { navController.navigate("NutritionOverview") },

            modifier = Modifier.padding(all = Dp(10F)),
            enabled = true,
            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
            shape = MaterialTheme.shapes.medium,
        )

        {
            Text(text = "Today's Nutrition", color = Color.White)
        }
    }
}

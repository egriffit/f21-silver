package com.example.workout_companion.view.inputfields

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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

@Composable
fun LandingPage(navController: NavController){
    Column(modifier = Modifier.fillMaxHeight()){
        MyButton()
        MyButton2()
        MyButton3()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        // function to display its preview.
        MyButton(); MyButton2(); MyButton3();
    }
}

@Composable
fun MyButton() {

    Column(
        modifier = Modifier
            .fillMaxWidth(),

// Modifies the button to be aligned properly
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val context = LocalContext.current

        Button(

            onClick = {},
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
fun MyButton2() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val context = LocalContext.current

        // below line is use to create a button.
        Button(

            onClick = {

            },

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
fun MyButton3() {
    Column(

        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val context = LocalContext.current

        Button(
            onClick = {
            },

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
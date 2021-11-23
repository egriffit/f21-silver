package com.example.workout_companion.view.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WorkoutView() {
    Column(modifier = Modifier
        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        .fillMaxWidth()
        .fillMaxHeight(),
    )
    {
        Row(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Start Workout")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutViewPreview() {
    WorkoutView()
}

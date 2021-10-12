package com.example.workout_companion.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.workout_companion.view.ui.theme.Workout_companionTheme

class InfoForm : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Workout_companionTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LazyColumnDemo(); submitbutton();
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    Workout_companionTheme {
         LazyColumnDemo(); submitbutton()
    }
}

@Composable
fun LazyColumnDemo(){
    Column(
        Modifier
            .background(Color(0xFFEDEAE0))
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        var name by remember { mutableStateOf(TextFieldValue()) }
        var heightfeet by remember { mutableStateOf(TextFieldValue())}
        var heightinch by remember { mutableStateOf(TextFieldValue())}
        var activitylevel by remember { mutableStateOf(TextFieldValue())}
        var age by remember { mutableStateOf(TextFieldValue())}
        var gender by remember { mutableStateOf(TextFieldValue())}
        var maingoal by remember { mutableStateOf(TextFieldValue())}
        var experience by remember { mutableStateOf(TextFieldValue())}
        var weight by remember { mutableStateOf(TextFieldValue())}

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name?") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        OutlinedTextField(
            value = heightfeet,
            onValueChange = { heightfeet = it },
            label = { Text(text = "Height? (in feet)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        OutlinedTextField(
            value = heightinch,
            onValueChange = { heightinch = it },
            label = { Text(text = "Height? (in inches") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        OutlinedTextField(
            value = activitylevel,
            onValueChange = { activitylevel = it },
            label = { Text(text = "Activity Level?") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text(text = "Age?") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        OutlinedTextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text(text = "Gender?") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )

        )

        OutlinedTextField(
            value = maingoal,
            onValueChange = { maingoal = it },
            label = { Text(text = "Main Goal?") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        OutlinedTextField(
            value = experience,
            onValueChange = { experience = it },
            label = { Text(text = "Experience? (Beginner, expert)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text(text = "Weight? (In pounds)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }
}

@Composable
fun submitbutton(){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),

// Modifies the button to be aligned properly
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    )

    { Button(
        onClick = {},
        modifier = Modifier.padding(all = Dp(18F)),
        enabled = true,
        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Magenta)),
        shape = MaterialTheme.shapes.large,
    )
    {
        Text(text = "Submit?", color = Color.Black)
    }
    }
}

package com.example.workout_companion.view.inputfields

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.utility.ActivityLevel
import com.example.workout_companion.utility.ExperienceLevel
import com.example.workout_companion.utility.Sex
import com.example.workout_companion.viewmodel.UserViewModel

@Composable
fun InfoForm(navController: NavController, userViewModel: UserViewModel){
    LazyColumnDemo(navController, userViewModel);
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    MaterialTheme() {
         //LazyColumnDemo();
    }
}

@Composable
fun LazyColumnDemo(navController: NavController, userViewModel: UserViewModel) {
    var NameState = remember { mutableStateOf("") }
    var feetState = remember { mutableStateOf("") }
    var inchesState = remember { mutableStateOf("") }
    var activityLevelState = remember { mutableStateOf("") }
    var AgeState = remember { mutableStateOf(0) }
    var GenderState = remember { mutableStateOf("") }
    var goalState = remember { mutableStateOf("") }
    var ExperienceLevelState = remember { mutableStateOf("") }
    var WeightState = remember { mutableStateOf("") }
    var birthDateState = remember { mutableStateOf("") }

    var query = remember { mutableStateOf(" ") }
    LazyColumn(
        Modifier
            .background(Color(0xFFEDEAE0))
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        item {
            Row() {
                OutlinedTextField(
                    value = NameState.value,
                    onValueChange = { NameState.value = it },
                    label = { Text(text = "Name?") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
            }
        }
        item{
            Row() {
                OutlinedTextField(
                    value = feetState.value,
                    onValueChange = { feetState.value = it },
                    label = { Text(text = "Feet?") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
                Spacer(modifier = Modifier.padding(end = 10.dp))
                OutlinedTextField(
                    value = inchesState.value,
                    onValueChange = { inchesState.value = it },
                    label = { Text(text = "Inches?") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
            }
        }
        // Activity Level Drop Down
        item{
            Row() {
                var selectedActivityLevel by remember { mutableStateOf(ActivityLevel.SLIGHTLY_ACTIVE) }
                var selectedText by remember { mutableStateOf("") }
                var expanded by remember { mutableStateOf(false) }
                var textFieldSize by remember { mutableStateOf(Size.Zero) }
                val icon = Icons.Filled.ArrowDropDown

                OutlinedTextField(
                    value = selectedActivityLevel.descName,
                    onValueChange = { /* Do nothing because users use the menu to edit */ },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth().
                        onGloballyPositioned { coordinates ->
                            // This value is used to assign to the DropDown the same width
                            textFieldSize = coordinates.size.toSize()
                        },
                    label = { Text(text ="Activity Level") },
                    trailingIcon = {
                        Icon(icon, "contentDescription",
                            Modifier.clickable { expanded = !expanded })
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.
                            width(with(LocalDensity.current){textFieldSize.width.toDp()})
                ) {
                    ActivityLevel.values().forEach { level ->
                        DropdownMenuItem(onClick = {
                            selectedActivityLevel = level
                            expanded = false
                        }) {
                            Text(text = level.descName )
                        }
                    }
                }
            }
        }
        item{
            Row() {
                OutlinedTextField(
                    value = AgeState.value.toString(),
                    onValueChange = { AgeState.value = it.toInt() },
                    label = { Text(text = "Age?") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
        }
        item{
            Row() {
                OutlinedTextField(
                    value = GenderState.value,
                    onValueChange = { GenderState.value = it },
                    label = { Text(text = "Gender?") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
            }
        }
        item{
            Row() {
                OutlinedTextField(
                    value = goalState.value,
                    onValueChange = { goalState.value = it },
                    label = { Text(text = "Main Goal?") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
            }
        }
        item{
            Row() {
                OutlinedTextField(
                    value = ExperienceLevelState.value,
                    onValueChange = { ExperienceLevelState.value = it },
                    label = { Text(text = "Experience? (Beginner, expert)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
            }
        }
        item{
            Row() {
                OutlinedTextField(
                    value = WeightState.value,
                    onValueChange = { WeightState.value = it },
                    label = { Text(text = "Weight? (In pounds)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
        }

        item{
            //Submit Button
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 120.dp, end = 120.dp)
            ) {
                Button(onClick = {
                    submit(
                        navController
                    )
                }) {
                    Text("Submit")
                }
            }
        }
    }
}

fun submit(navController: NavController){
    //We need to make sure our inputs are capable of handling the input we need
    //create user and add to database
//    val formData = UserEntity(name,  ExperienceLevel.BEGINNER,
//        Sex.MALE, birthDate, 2,
//        160.0, ActivityLevel.MODERATELY_ACTIVE)
//    userViewModel.addUser(formData)
    //Navigate to the new view
    navController.navigate("mainView")
}


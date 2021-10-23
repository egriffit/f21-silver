package com.example.workout_companion.view.inputfields

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.utility.*
import com.example.workout_companion.viewmodel.UserViewModel
import java.time.LocalDate

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
    // User entity
    // TODO: load existing user entity if it exists
    val user = UserEntity(
        name = "",
        experience_level = ExperienceLevel.BEGINNER,
        sex = Sex.MALE,
        birth_date = LocalDate.MAX,
        max_workouts_per_week = 0,
        height = 0.0,
        weight = 0.0,
        activity_level = ActivityLevel.SLIGHTLY_ACTIVE
    )

    // Main state variables for the inputs
    var nameState by remember { mutableStateOf(user.name) }
    var ageState by remember { mutableStateOf("") } // TODO: Get rid of this
    var birthDateState by remember { mutableStateOf("") } // TODO: How to input this?
    var feetState by remember { mutableStateOf(UnitConverter.toFeetAndInches(user.height).first.toInt().toString()) }
    var inchesState by remember { mutableStateOf(UnitConverter.toFeetAndInches(user.height).second.toInt().toString()) }
    var weightState by remember { mutableStateOf(UnitConverter.toPounds(user.weight).toString()) }
    var genderState by remember { mutableStateOf(user.sex) }
    var goalState by remember { mutableStateOf(MainGoal.BUILD_MUSCLE) } // TODO: where stored?
    var activityLevelState by remember { mutableStateOf(user.activity_level) }
    var expLevelState by remember { mutableStateOf(user.experience_level) }
    var maxWorkoutsState by remember { mutableStateOf(user.max_workouts_per_week.toString()) }


    LazyColumn(
        Modifier
            .background(Color(0xFFEDEAE0))
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // Name
        item{
            Row() {
                OutlinedTextField(
                    value = nameState,
                    onValueChange = { nameState = it },
                    label = { Text(text = "Name?") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
            }
        }
        // Height (feet and inches)
        item{
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = feetState,
                    onValueChange = { feetState = if (it.toIntOrNull() != null) it else "" },
                    label = { Text(text = "Feet?") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                Spacer(modifier = Modifier.padding(end = 10.dp))
                OutlinedTextField(
                    value = inchesState,
                    onValueChange = { inchesState = if (it.toIntOrNull() != null) it else "" },
                    label = { Text(text = "Inches?") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
        }
        // Age and Weight
        item{
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = ageState,
                    onValueChange = { ageState = if (it.toIntOrNull() != null) it else "" },
                    label = { Text(text = "Age?") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                Spacer(modifier = Modifier.padding(end = 10.dp))
                OutlinedTextField(
                    value = weightState,
                    onValueChange = { weightState = it },
                    label = { Text(text = "Weight? (In pounds)") },
                    modifier = Modifier.weight(2f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
        }
        // Gender
        item{
            Row() {
                var textFieldSize by remember { mutableStateOf(Size.Zero) }
                var expanded by remember { mutableStateOf(false) }
                val icon = Icons.Filled.ArrowDropDown

                OutlinedTextField(
                    value = genderState.descName,
                    onValueChange = { /* Do nothing because users use the menu to edit */ },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to the DropDown the same width
                            textFieldSize = coordinates.size.toSize()
                        },
                    label = { Text(text ="Gender") },
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
                    Sex.values().forEach { gender ->
                        DropdownMenuItem(onClick = {
                            genderState = gender
                            expanded = false
                        }) {
                            Text(text = gender.descName)
                        }
                    }
                }
            }
        }
        // Goal
        item{
            var expanded by remember { mutableStateOf(false) }
            var textFieldSize by remember { mutableStateOf(Size.Zero) }
            val icon = Icons.Default.ArrowDropDown

            OutlinedTextField(
                value = goalState.descName,
                onValueChange = { /* Do nothing because users use the menu to edit */ },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        // This value is used to assign to the DropDown the same width
                        textFieldSize = coordinates.size.toSize()
                    },
                label = { Text(text ="Main Goal") },
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
                MainGoal.values().forEach { goal ->
                    DropdownMenuItem(onClick = {
                        goalState = goal
                        expanded = false
                    }) {
                        Text(text = goal.descName)
                    }
                }
            }
        }
        // Activity Level
        item{
            Row() {
                var expanded by remember { mutableStateOf(false) }
                var textFieldSize by remember { mutableStateOf(Size.Zero) }
                val icon = Icons.Filled.ArrowDropDown

                OutlinedTextField(
                    value = activityLevelState.descName,
                    onValueChange = { /* Do nothing because users use the menu to edit */ },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
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
                            activityLevelState = level
                            expanded = false
                        }) {
                            Text(text = level.descName)
                        }
                    }
                }
            }
        }
        // Experience Level
        item{
            Row() {
                var expanded by remember { mutableStateOf(false) }
                var textFieldSize by remember { mutableStateOf(Size.Zero) }
                val icon = Icons.Filled.ArrowDropDown

                OutlinedTextField(
                    value = expLevelState.descName,
                    onValueChange = { /* Do nothing because users use the menu to edit */ },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
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
                    ExperienceLevel.values().forEach { level ->
                        DropdownMenuItem(onClick = {
                            expLevelState = level
                            expanded = false
                        }) {
                            Text(text = level.descName)
                        }
                    }
                }
            }
        }
        // Max workouts
        item{
            Row(){
                OutlinedTextField(
                    value = maxWorkoutsState,
                    onValueChange = { maxWorkoutsState = if (it.toIntOrNull() != null) it else "" },
                    label = { Text(text = "Max Number of Workouts/Week?") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
        }
        // Submit Button
        item{
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = {
                        user.name = nameState
                        user.experience_level = expLevelState
                        user.sex = genderState
                        user.birth_date = LocalDate.MAX // TODO: Fix!
                        user.height = UnitConverter.toCentimeters(feetState.toDouble(), inchesState.toDouble())
                        user.weight = UnitConverter.toKilograms(weightState.toDouble())
                        user.max_workouts_per_week = maxWorkoutsState.toInt()
                        user.activity_level = activityLevelState

                        if (userIsValid(user)) {
                            //userViewModel.addUser(user)
                            navController.navigate("AddGoals")
                        }
                    }) {
                    Text("Submit")
                }
            }
        }
    }
}

fun heightInputsAreValid(feetString: String, inchesString: String) : Boolean {
    return feetString.toDoubleOrNull() != null
            && inchesString.toDoubleOrNull() != null
}

fun userIsValid(user: UserEntity) : Boolean {
    return nameIsValid(user.name)
            && expLevelIsValid(user.experience_level)
            && sexIsValid(user.sex)
            && birthDateIsValid(user.birth_date)
            && maxWorkoutsIsValid(user.max_workouts_per_week)
            && heightIsValid(user.height)
            && activityLevelIsValid(user.activity_level)
}

fun nameIsValid(name: String) : Boolean {
    return name.isNotBlank()
}

fun expLevelIsValid(level: ExperienceLevel) : Boolean {
    return ExperienceLevel.values().contains(level)
}

fun sexIsValid(sex: Sex) : Boolean {
    return Sex.values().contains(sex)
}

fun birthDateIsValid(date: LocalDate) : Boolean {
    return date < LocalDate.now()
}

fun maxWorkoutsIsValid(maxWorkouts: Int) : Boolean {
    return maxWorkouts in 1..7
}

fun heightIsValid(height: Double) : Boolean {
    return height > 0.0
}

fun activityLevelIsValid(level: ActivityLevel) : Boolean {
    return ActivityLevel.values().contains(level)
}
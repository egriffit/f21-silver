package com.example.workout_companion.view.userform

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.workout_companion.dao.UserWithGoal
import com.example.workout_companion.database.GOALS
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.enumeration.ActivityLevel
import com.example.workout_companion.enumeration.ExperienceLevel
import com.example.workout_companion.enumeration.Sex
import com.example.workout_companion.utility.*
import com.example.workout_companion.viewmodel.UserViewModel
import com.example.workout_companion.viewmodel.UserWithGoalViewModel
import java.time.LocalDate
import java.time.Month
import java.util.Locale as Locale
import java.time.format.TextStyle as TextStyle

@SuppressLint("NewApi")
val DEFAULT_USER = UserEntity(
        name = "",
        experience_level = ExperienceLevel.BEGINNER,
        sex = Sex.MALE,
        birth_date = LocalDate.now(),
        max_workouts_per_week = 0,
        height = 0.0,
        weight = 0.0,
        activity_level = ActivityLevel.SLIGHTLY_ACTIVE,
        goal_id = 0
)
val DEFAULT_GOAL = GOALS.getValue(DEFAULT_USER.goal_id)
val DEFAULT_USER_WITH_GOAL = UserWithGoal(DEFAULT_USER, DEFAULT_GOAL)



@SuppressLint("NewApi")
class UserState(userWithGoal: UserWithGoal) {
    var name by mutableStateOf(userWithGoal.user.name)
    var birthYear by mutableStateOf(userWithGoal.user.birth_date.year.toString())
    var birthMonth by mutableStateOf(userWithGoal.user.birth_date.month.toString())
    var birthDay by mutableStateOf(userWithGoal.user.birth_date.dayOfMonth.toString())
    var feet by mutableStateOf(UnitConverter.toFeetAndInches(userWithGoal.user.height).first.toInt().toString())
    var inches by mutableStateOf(UnitConverter.toFeetAndInches(userWithGoal.user.height).second.toInt().toString())
    var weight by mutableStateOf(UnitConverter.toPounds(userWithGoal.user.weight).toString())
    var gender by mutableStateOf(userWithGoal.user.sex)
    var goal by mutableStateOf(userWithGoal.goal)
    var activityLevel by mutableStateOf(userWithGoal.user.activity_level)
    var expLevel by mutableStateOf(userWithGoal.user.experience_level)
    var maxWorkouts by mutableStateOf(userWithGoal.user.max_workouts_per_week.toString())

    fun getUser(): UserEntity {
        return UserEntity (
            name = name,
            birth_date = LocalDate.of(birthYear.toInt(), Month.valueOf(birthMonth), birthDay.toInt()),
            height = UnitConverter.toCentimeters(feet.toDouble(), inches.toDouble()),
            weight = UnitConverter.toKilograms(weight.toDouble()),
            sex = gender,
            activity_level = activityLevel,
            experience_level = expLevel,
            max_workouts_per_week = maxWorkouts.toInt(),
            goal_id = goal.id
        )
    }
}

@SuppressLint("NewApi")
@Composable
fun InfoForm(navController: NavController, userViewModel: UserViewModel, userWithGoalViewModel: UserWithGoalViewModel) {
    var state = remember { UserState(DEFAULT_USER_WITH_GOAL) }

    val userWithGoalInDB = userWithGoalViewModel.userWithGoal.observeAsState()
    if (userWithGoalInDB.value != null) {
        state = UserState(userWithGoalInDB.value!!)
    }

    val focusManager = LocalFocusManager.current

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // Name
        item{
            Row {
                OutlinedTextField(
                    value = state.name,
                    onValueChange = { state.name = it },
                    label = { Text(text = "Name?") },
                    singleLine = true,
                    // Since our name is our primary key, existing user names can't be changed
                    readOnly = userWithGoalInDB.value != null,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                )
            }
        }
        // Height (feet and inches)
        item{
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.feet,
                    onValueChange = { newValue ->
                        state.feet = newValue.filter { it.isDigit() }
                    },
                    singleLine = true,
                    label = { Text(text = "Feet?") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.moveFocus(FocusDirection.Right)
                    }),
                )
                Spacer(modifier = Modifier.padding(end = 10.dp))
                OutlinedTextField(
                    value = state.inches,
                    onValueChange = { newValue ->
                        state.inches = newValue.filter { it.isDigit() }
                    },
                    singleLine = true,
                    label = { Text(text = "Inches?") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        // Since the birth date selector is next, just clear any focus
                        focusManager.clearFocus()
                    }),
                )
            }
        }
        // Birth Date
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                var monthExpanded by remember { mutableStateOf(false) }
                var dayExpanded by remember { mutableStateOf(false) }
                var yearExpanded by remember { mutableStateOf(false) }
                var monthTextFieldSize by remember { mutableStateOf(Size.Zero) }
                var dayTextFieldSize by remember { mutableStateOf(Size.Zero) }
                var yearTextFieldSize by remember { mutableStateOf(Size.Zero) }
                val icon = Icons.Default.ArrowDropDown

                // Month
                OutlinedTextField(
                    value = Month.valueOf(state.birthMonth)
                        .getDisplayName(TextStyle.SHORT, Locale.US),
                    onValueChange = { /* Do nothing here, the dropdown menu handles it */ },
                    readOnly = true,
                    modifier = Modifier
                        .weight(4f)
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to the DropDown the same width
                            monthTextFieldSize = coordinates.size.toSize()
                        },
                    label = { Text(text = "Month") },
                    trailingIcon = {
                        Icon(icon, "contentDescription",
                            Modifier.clickable { monthExpanded = !monthExpanded })
                    },
                )
                DropdownMenu(
                    expanded = monthExpanded,
                    onDismissRequest = { monthExpanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { monthTextFieldSize.width.toDp() })
                ) {
                    Month.values().forEach { month ->
                        DropdownMenuItem(
                            onClick = {
                                state.birthMonth = month.toString()
                                monthExpanded = false
                            }
                        ) {
                            Text(text = month.getDisplayName(TextStyle.SHORT, Locale.US))
                        }
                    }
                }

                // Day
                OutlinedTextField(
                    value = state.birthDay,
                    onValueChange = { /* Do nothing here, the dropdown menu handles it */ },
                    readOnly = true,
                    modifier = Modifier
                        .weight(4f)
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to the DropDown the same width
                            dayTextFieldSize = coordinates.size.toSize()
                        },
                    label = { Text(text = "Day") },
                    trailingIcon = {
                        Icon(icon, "contentDescription",
                            Modifier.clickable { dayExpanded = !dayExpanded })
                    }
                )
                DropdownMenu(
                    expanded = dayExpanded,
                    onDismissRequest = { dayExpanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { dayTextFieldSize.width.toDp() })
                ) {
                    for (day in 1..Month.valueOf(state.birthMonth).length(true)) {
                        DropdownMenuItem(
                            onClick = {
                                state.birthDay = day.toString()
                                dayExpanded = false
                            }
                        ) {
                            Text(text = day.toString())
                        }
                    }
                }

                // Year
                OutlinedTextField(
                    value = state.birthYear,
                    onValueChange = { /* Do nothing here, the dropdown menu handles it */ },
                    readOnly = true,
                    modifier = Modifier
                        .weight(5f)
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to the DropDown the same width
                            yearTextFieldSize = coordinates.size.toSize()
                        },
                    label = { Text(text = "Year") },
                    trailingIcon = {
                        Icon(icon, "contentDescription",
                            Modifier.clickable { yearExpanded = !yearExpanded })
                    }
                )
                DropdownMenu(
                    expanded = yearExpanded,
                    onDismissRequest = { yearExpanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { yearTextFieldSize.width.toDp() })
                ) {
                    for (year in 2021 downTo 1940)
                        DropdownMenuItem(
                            onClick = {
                                state.birthYear = year.toString()
                                yearExpanded = false
                            }
                        ) {
                            Text(text = year.toString())
                        }
                }
            }
        }
        // Weight
        item{
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.weight,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() or (newValue.toDoubleOrNull() != null) ) {
                            state.weight = newValue
                        }
                    },
                    label = { Text(text = "Weight? (In pounds)") },
                    modifier = Modifier.weight(2f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        // Since the gender selection is below this, clear any focus
                        focusManager.clearFocus()
                    }),
                )
            }
        }
        // Gender
        item{
            Row{
                var textFieldSize by remember { mutableStateOf(Size.Zero) }
                var expanded by remember { mutableStateOf(false) }
                val icon = Icons.Filled.ArrowDropDown

                OutlinedTextField(
                    value = state.gender.descName,
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
                            state.gender = gender
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
                value = state.goal.goal,
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
                GOALS.values.forEach { goal ->
                    DropdownMenuItem(onClick = {
                        state.goal = goal
                        expanded = false
                    }) {
                        Text(text = goal.goal)
                    }
                }
            }
        }
        // Activity Level
        item{
            Row {
                var expanded by remember { mutableStateOf(false) }
                var textFieldSize by remember { mutableStateOf(Size.Zero) }
                val icon = Icons.Filled.ArrowDropDown

                OutlinedTextField(
                    value = state.activityLevel.descName,
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
                            state.activityLevel = level
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
            Row {
                var expanded by remember { mutableStateOf(false) }
                var textFieldSize by remember { mutableStateOf(Size.Zero) }
                val icon = Icons.Filled.ArrowDropDown

                OutlinedTextField(
                    value = state.expLevel.descName,
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
                            state.expLevel = level
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
            Row{
                OutlinedTextField(
                    value = state.maxWorkouts,
                    onValueChange = { newValue ->
                        state.maxWorkouts = newValue.filter { it.isDigit() }
                    },
                    label = { Text(text = "Max Number of Workouts/Week?") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        // Last item so clear focus
                        focusManager.clearFocus()
                    }),
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
                        val user = state.getUser()
                        if (userIsValid(user)) {
                            if (userWithGoalInDB.value == null) {
                                userViewModel.addUser(user)
                            }
                            else {
                                userViewModel.updateUser(user)
                            }
                            navController.navigate("UpdateGoals")
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

@SuppressLint("NewApi")
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
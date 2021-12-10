package com.example.workout_companion.view.goals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.workout_companion.entity.GoalTypeEntity
import androidx.compose.runtime.remember
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.utility.estimateCaloricIntake
import java.time.LocalDate
import java.time.Period


@Composable
fun GoalDropdown(goals: List<GoalTypeEntity>,
                 user: UserEntity,
                 theGoal: MutableState<GoalTypeEntity>,
                 calories: MutableState<Int>,
                 inEdit: MutableState<Boolean>){
    val expanded = remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .wrapContentSize(Alignment.TopStart)
    ) {
        Text(theGoal.value.goal,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded.value = true })
                .background(Color.Gray)
                .border(BorderStroke(2.dp, Color.Black))
        )
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
        ) {
            goals.forEach { s ->
                DropdownMenuItem(onClick = {
                    theGoal.value = s

                    val userAge = Period.between(user.birth_date, LocalDate.now()).years
                    calories.value = estimateCaloricIntake(user.weight, user.height, userAge, user.activity_level, user.sex, theGoal.value)

                    expanded.value = false
                    inEdit.value = true
                }) {
                    Text(text = s.goal)
                }
            }
        }
    }
}



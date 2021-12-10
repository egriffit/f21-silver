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


@Composable
fun GoalDropdown(goals: List<GoalTypeEntity>,
                 currentGoal: MutableState<Int>){
    var expanded = remember { mutableStateOf(false) }
    var selectedIndex =  remember { mutableStateOf(0) }

    var selectedGoal = goals[0]
    val foundGoal = goals.find { it.id == currentGoal.value }
    if (foundGoal != null) {
        selectedGoal = foundGoal
        selectedIndex.value = goals.indexOf(foundGoal)
    }

    Box(modifier = Modifier
        .wrapContentSize(Alignment.TopStart)
    ) {
        Text(selectedGoal.goal,
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
            goals.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex.value = index
                    selectedGoal = s
                    currentGoal.value = s.id
                    expanded.value = false
                }) {
                    Text(text = s.goal)
                }
            }
        }
    }
}



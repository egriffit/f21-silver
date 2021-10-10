package com.example.workout_companion.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.entity.FrameworkWithGoalEntity
import com.example.workout_companion.sampleData.FrameWorkList


@Composable
fun RecommendFrameworkView(UserEntity: UserEntity,
                        FrameWorkWithGoalEntity: List<FrameworkWithGoalEntity>)
{
    val workoutFrequency = UserEntity.max_workouts_per_week
    //use method to get all framework_types joined with the goal_types table

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row{
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "Current Goal: ",
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "Maximum Workouts: ",
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier)
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.End
            ) {
                Text(FrameWorkWithGoalEntity[0].goal)
                Text("$workoutFrequency")
            }
        }
        Spacer(modifier=Modifier.height(20.dp))
        Text("Recommended Frameworks:",
            fontWeight = FontWeight.Bold)
        FrameworkViewDropdown(FrameWorkList)
        Spacer(modifier=Modifier.height(50.dp))
        Button(onClick = {}){
            Text("Confirm View")
        }
    }
}



@Composable
fun FrameworkViewDropdown(FrameWorkWithGoalEntity: List<FrameworkWithGoalEntity>){
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Box(modifier = Modifier
        .wrapContentSize(Alignment.TopStart)
    ) {
        Text(FrameWorkWithGoalEntity[selectedIndex].name,
            fontSize = 18.sp,
            modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { expanded = true })
                        .background(Color.Gray)
                .border(BorderStroke(2.dp, Color.Black))
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
        ) {
            FrameWorkWithGoalEntity.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                }) {
                    Text(text = "${s.name} (${s.workouts_per_week} workouts per week)")
                }
            }
        }
    }
}
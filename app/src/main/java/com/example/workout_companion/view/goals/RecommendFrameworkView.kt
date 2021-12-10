package com.example.workout_companion.view.goals

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
import com.example.workout_companion.entity.FrameworkTypeEntity
import com.example.workout_companion.entity.FrameworkWithGoalEntity
import com.example.workout_companion.viewmodel.FrameworkTypeViewModel


@Composable
fun RecommendFrameworkView(
    frameworks: List<FrameworkTypeEntity>,
    recommendedFrameworkId: MutableState<Int>)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier=Modifier.height(20.dp))
        Text("Recommended Frameworks:",
            fontWeight = FontWeight.Bold)
        if(frameworks.isNotEmpty()){
            FrameworkViewDropdown(frameworks, recommendedFrameworkId)
        }
        else{
            Text("No frameworks were found")
        }
    }
}



@Composable
fun FrameworkViewDropdown(frameworks: List<FrameworkTypeEntity>,
                          recommendedFrameworkId: MutableState<Int>
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Box(modifier = Modifier
        .wrapContentSize(Alignment.TopStart)
    ) {
        Text(frameworks[selectedIndex].name,
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
            frameworks.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    recommendedFrameworkId.value = s.id
                }) {
                    Text(text = "${s.name} (${s.workouts_per_week} workouts per week)")
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewRecommendFrameworkView(){
//    val maxWorkouts = 3
//    val currentRecommendedFramework = remember{ mutableStateOf(0)}
//    val frameworkWithGoals= listOf(
//        FrameworkWithGoalEntity(1, "Framework 0", 2, 0, "Goal 0"),
//        FrameworkWithGoalEntity(2, "Framework 0", 1, 0, "Goal 0")
//    )
//    RecommendFrameworkView(frameworkWithGoals, currentRecommendedFramework)
//}
//
//@Preview
//@Composable
//fun PreviewFrameworkViewDropDown(){
//    val currentRecommendedFramework = remember{ mutableStateOf(0)}
//    val frameworkWithGoals= listOf(
//        FrameworkWithGoalEntity(1, "Framework 0", 2, 0, "Goal 0"),
//        FrameworkWithGoalEntity(2, "Framework 0", 1, 0, "Goal 0")
//    )
//    FrameworkViewDropdown(frameworkWithGoals, currentRecommendedFramework)
//}
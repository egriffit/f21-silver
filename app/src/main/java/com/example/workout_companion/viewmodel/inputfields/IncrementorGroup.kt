package com.example.workout_companion.viewmodel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ChevronRight
import androidx.compose.material.icons.sharp.Lock
import androidx.compose.material.icons.sharp.LockOpen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IncrementerGroup(labels: List<String>, startingValues: List<Int>,
                     fieldStates: List<MutableState<Int>>,
                     canSubmit: MutableState<Boolean>){
    var total = 0
    for(i in labels.indices){
        total += startingValues[i]
    }
    var currentTotal = remember{ mutableStateOf(total)}
    var maxStates: List<MutableState<Int>> = listOf()
    var minStates: List<MutableState<Int>> = listOf()
    var locked = remember{mutableStateOf(false)}
    var canIncrease = remember { mutableStateOf(false)}
    var icon: ImageVector = Icons.Sharp.LockOpen
    if(locked.value){
        icon = Icons.Sharp.Lock
    }
    else{
        icon = Icons.Sharp.LockOpen
    }
    Column(modifier = Modifier.fillMaxWidth()){
        for (i in labels.indices) {
            maxStates += remember { mutableStateOf(startingValues[i] + 1) }
            minStates += remember { mutableStateOf(0) }

            Incrementer(label = labels[i], fieldState = fieldStates[i],
                canIncrease = canIncrease, locked = locked, total = currentTotal)
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
        }
        if(currentTotal.value != total){
            Text("Current macronutrient composition does not equal 100")
            OutlinedTextField(
                value = currentTotal.value.toString(),
                onValueChange = { var temp = 0
                    for( i in labels.indices){
                        temp += fieldStates[i].value
                    }
                    currentTotal.value = temp
                    //if currentTotal != total can't submit
                    if(currentTotal.value != total){
                        canSubmit.value = false
                    }
                }
            )
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center){
            Button(
                onClick = {
                    locked.value = !locked.value
                },
                modifier = Modifier.background(
                    Color.LightGray
                ),
                border= BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.LightGray
                )
            ) {

                Icon(
                    icon,
                    contentDescription = "",
                    modifier = Modifier.background(color = Color.LightGray)
                        .size(38.dp),
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewIncrementerGroup(){
    val labels = listOf("Carbohydrate", "Protein", "Fat")
    val startingValues = listOf(40, 35, 25)
    var fieldStates = listOf(
        remember { mutableStateOf(startingValues[0]) },
        remember { mutableStateOf(startingValues[1]) },
        remember { mutableStateOf(startingValues[2]) }
    )
    var canSubmit =  remember { mutableStateOf(true) }
    IncrementerGroup(labels, startingValues, fieldStates, canSubmit)
}
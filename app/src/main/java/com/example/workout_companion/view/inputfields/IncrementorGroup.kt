package com.example.workout_companion.view.inputfields

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.example.workout_companion.viewmodel.Incrementer

@Composable
fun IncrementerGroup(labels: List<String>, startingValues: List<Int>,
                     fieldStates: List<MutableState<Int>>,
                     canSubmit: MutableState<Boolean>,
                    disabled: Boolean){
    var total = 0
    for(i in labels.indices){
        total += startingValues[i]
    }
    val currentTotal = remember{ mutableStateOf(total)}
    val maxStates: MutableList<MutableState<Int>> = mutableListOf()
    val minStates: MutableList<MutableState<Int>> = mutableListOf()
    val locked = remember{mutableStateOf(disabled)}
    val canIncrease = remember { mutableStateOf(false)}
    val icon: ImageVector = if(locked.value){
        Icons.Sharp.Lock
    }
    else{
        Icons.Sharp.LockOpen
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
        if(!disabled){
            locked.value = true
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
}

@Preview
@Composable
fun PreviewIncrementerGroup(){
    val labels = listOf("Carbohydrate", "Protein", "Fat")
    val startingValues = listOf(40, 35, 25)
    val fieldStates = listOf(
        remember { mutableStateOf(startingValues[0]) },
        remember { mutableStateOf(startingValues[1]) },
        remember { mutableStateOf(startingValues[2]) }
    )
    val canSubmit =  remember { mutableStateOf(true) }
    val disabled = true
    IncrementerGroup(labels, startingValues, fieldStates, canSubmit, disabled)
}
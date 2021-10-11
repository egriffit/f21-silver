package com.example.workout_companion.viewmodel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ChevronLeft
import androidx.compose.material.icons.sharp.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Incrementer(
    label: String, fieldState: MutableState<Int>,
    canIncrease: MutableState<Boolean>,
    locked: MutableState<Boolean>,
    total: MutableState<Int>
) {
    var backgroundColor = Color.Blue
    var textFieldBackgroundColor = Color.White

    var buttonColor = ButtonDefaults.outlinedButtonColors(backgroundColor =  Color.LightGray)
    if (locked.value) {
        backgroundColor = Color.Gray
        textFieldBackgroundColor = Color.LightGray
    }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Column(
                modifier = Modifier.width(100.dp)
            ){
                Text(
                    text = label,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.fillMaxWidth())
            }
            Row() {
                Button(
                    onClick = {
                        if (fieldState.value > 0 && !locked.value) {
                            fieldState.value -= 1
                            total.value -= 1
                            if (total.value <= 100) {
                                canIncrease.value = true
                            }
                            if (total.value >= 100) {
                                canIncrease.value = false
                            }
                        }
                    },
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor =  backgroundColor),
                    border= BorderStroke(1.dp, Color.Black),
                    modifier = Modifier.background(
                        backgroundColor
                    )
                ) {
                    Icon(
                        Icons.Sharp.ChevronLeft,
                        contentDescription = "Localized description",
                        modifier = Modifier.background(
                            backgroundColor
                        ).size(38.dp),
                        tint = Color.Black
                    )
                }
                Box(modifier = Modifier.width(100.dp)) {
                    OutlinedTextField(
                        value = fieldState.value.toString(),
                        onValueChange = { fieldState.value = Integer.parseInt(it) },
                        modifier = Modifier.background(
                            textFieldBackgroundColor
                        )
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .alpha(0f)
                            .clickable(onClick = {})
                    )
                }
            }
            Button(
                onClick = {
                    if (canIncrease.value && fieldState.value >= 0 && !locked.value) {
                        fieldState.value += 1
                        total.value += 1
                        if(total.value <= 100) {
                            canIncrease.value = true
                        }
                        if(total.value >= 100){
                            canIncrease.value = false
                        }
                    }
                },
                modifier = Modifier.background(
                    backgroundColor
                ),
                border= BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor =  backgroundColor)
            ) {

                Icon(
                    Icons.Sharp.ChevronRight,
                    contentDescription = "",
                    modifier = Modifier.background(
                        backgroundColor
                    ).size(38.dp),
                    tint = Color.Black
                )
            }
        }
}

@Preview
@Composable
fun PreviewIncrementer(){
    var maxVal = 40
    val minVal = 10
    var canIncrease = remember{ mutableStateOf(false) }
    var currentTotal = remember{ mutableStateOf(maxVal) }
    var percentage = remember { mutableStateOf(0) }
    var locked = remember{mutableStateOf(true)}
    Incrementer(label = "test", fieldState = percentage, canIncrease = canIncrease,
        locked = locked, total = currentTotal)
}
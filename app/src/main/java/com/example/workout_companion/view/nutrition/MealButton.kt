package com.example.workout_companion.view.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.NutritionApiNinjaViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun mealButton(meal: String, fodInMealViewModel: FoodInMealViewModel
) {
    val open = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth()
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth()){
            Button(onClick = {open.value = !open.value}) {
                if(open.value){
                    Text("-",
                        fontSize = 15.sp)
                }else{
                    Text("+",
                        fontSize = 15.sp)
                }

            }
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Text(meal)
        }
        //FoodList(fodInMealViewModel, meal, open, apiNinjaViewModel)
    }
}
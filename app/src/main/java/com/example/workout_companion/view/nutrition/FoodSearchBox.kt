package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.example.workout_companion.api.utility.FoodData
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.NutritionApiNinjaViewModel

@Composable
fun foodSearchBox(foodInMealViewModel: FoodInMealViewModel, apiNinjaViewModel: NutritionApiNinjaViewModel){
    val food = remember{ mutableStateOf("") }
    var foundFoods =  MutableLiveData<List<FoodData>>().observeAsState(listOf()).value
    Column() {
        Row(        modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.width(200.dp)
            )
            {
                Text(
                    "Add Food:",
                    fontSize = 15.sp
                )
                TextField(value = food.value,
                    onValueChange = { food.value = it })
            }
            Row(
                modifier = Modifier.width(200.dp)
            )
            {
                Button(onClick = {
                    var foundFoods = apiNinjaViewModel.getFood(food.value)
                }) {
                    Text(
                        "Search",
                        fontSize = 15.sp
                    )
                }
            }
        }
        Text("Results")
        Column(){
            for(food in foundFoods){
                Row(){
                    Text(food.name)
                }
            }
        }
    }

}
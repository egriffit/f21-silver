package com.example.workout_companion.view.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.viewmodel.MealViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddMealForm(mealViewModel: MealViewModel){
    val foundMeals = mealViewModel.getAllMeals.observeAsState(listOf()).value
    val mealName = remember{ mutableStateOf("")}
    Column(
        modifier = Modifier.fillMaxSize(),

    ){
        MealList(foundMeals)
        Row(        modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Row(
                modifier = Modifier.width(200.dp)
            )
            {
                Text("Create Meal:",
                    fontSize = 15.sp)
                TextField(value = mealName.value,
                    onValueChange = {mealName.value = it})
            }
            Row(
                modifier = Modifier.width(200.dp)
            )
            {
                Button(onClick = {
                    mealViewModel.insert(mealName.value)
                    mealName.value = ""
                }){
                    Text("Add meal",
                        fontSize = 15.sp)
                }
            }
        }
        Button(onClick = {
            mealViewModel.deleteAll()
        }){
            Text("Reset",
                fontSize = 15.sp)
        }
    }
}

@Composable
fun MealList(meals: List<MealEntity>) {
    Row(modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center){
        Text("Today's Meals")
    }

    LazyColumn(
        Modifier
            .background(Color(0xFFEDEAE0))
            .fillMaxWidth()
            .fillMaxHeight(0.4F)
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (meals != null) {
            for (meal in meals)
            // Name
                item {
                    Row {
                        Text(meal.type)
                    }
                }
        }else{
            item{
                Row{
                    Text("No Meals found")
                }
            }
        }
    }
}


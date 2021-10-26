package com.example.workout_companion.view.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.NutritionApiNinjaViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MealList(meals: List<MealEntity>, fodInMealViewModel: FoodInMealViewModel
) {
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
                    Row(horizontalArrangement =  Arrangement.Center) {
                        mealButton(meal.type, fodInMealViewModel)
                    }
                }
        }else{
            item{
                Row(horizontalArrangement =  Arrangement.Center){
                    Text("No Meals found")
                }
            }
        }
    }
}
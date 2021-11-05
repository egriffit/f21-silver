package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.FoodTypeViewModel
import com.example.workout_companion.viewmodel.MealViewModel
import com.example.workout_companion.viewmodel.NutritionAPIViewModel

@Composable
fun FoodView(
    navController: NavController,
    food: String?,
    servingSize: Double?,
    calories: Double?,
    carbohydrates: Double?,
    protein: Double?,
    fat: Double?,
    foodTypeViewModel: FoodTypeViewModel,
    mealViewModel: MealViewModel,
    foodInMealViewModel: FoodInMealViewModel,
    nutritionAPIViewModel: NutritionAPIViewModel
){
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(100.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (food != null) {
                        Text(text = food,
                        modifier = Modifier.padding(bottom = 20.dp))
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Serving Size: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("${servingSize} g")
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Calories: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("${calories} cal")
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Carbohydrates: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("${carbohydrates} g")
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Protein: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("${protein} g")
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Fat: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("${fat} g")
                }
            }
        }
    )
}
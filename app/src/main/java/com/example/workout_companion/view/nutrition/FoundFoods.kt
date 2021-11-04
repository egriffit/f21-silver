package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.workout_companion.viewmodel.NutritionAPIViewModel

@Composable
fun FoundFoods(navController: NavController, food: String?, meal: String?){
    val context = LocalContext.current
    val nutritionAPIViewModel: NutritionAPIViewModel =  viewModel()
    val selectedFoodIndex = remember { mutableStateOf(0) }
    val foodState = remember{ mutableStateOf("") }
    if (food != null) {
        foodState.value = food
    }
    if (food != null) {
        nutritionAPIViewModel.findFood(food)
    }
    //store the foods
    var foundFoods =  nutritionAPIViewModel.foodResults
    //create the pick food form
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 5.dp, top = 80.dp, bottom = 20.dp, end = 5.dp)
    ){
        foodRadioButtonList(foundFoods, foodState, selectedFoodIndex)
    }
}
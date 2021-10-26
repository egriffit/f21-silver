package com.example.workout_companion.view.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.NutritionApiNinjaViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FoodList(foodInMealViewModel: FoodInMealViewModel, meal: String, open: MutableState<Boolean>,
             apiNinjaViewModel: NutritionApiNinjaViewModel
)
{
    val today = LocalDate.now()
    var foundFoods =  foodInMealViewModel.getFoodsInMeal(meal, today).observeAsState(listOf()).value
    if(open.value){
        Column(){
            Text("Foods")
            //display current foods in meal
            for (food in foundFoods)
                // Name
                        Row {
                            Text("${food.name} - Serving Size: ${food.serving_size} Carbohydrates: ${food.carbohydrates}g  Protein: ${food.protein}g Fat: ${food.fat}")
                        }
        }
                //search box to add food
                foodSearchBox(foodInMealViewModel, apiNinjaViewModel)
    }
}
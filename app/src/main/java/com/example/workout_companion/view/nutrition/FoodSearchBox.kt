package com.example.workout_companion.view.nutrition

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.example.workout_companion.api.nutrition_api_ninja.NutritionAPIViewModel
import com.example.workout_companion.api.utility.FoodData
import com.example.workout_companion.viewmodel.FoodInMealViewModel

/***
 * Composable with a textField and a button. The button can be pushed and will retrieve foods
 * from the NutritionAPI that match the string provided
 *
 * It consists of:
 * a label
 * a textField,
 * a button to request data from the NutritionAPI
 * a column of text labels with foods found from the API
 *
 * @param foundINMealViewModel, a view model to work with the the food_in_meal table
 * @param apiNinjaViewModel, a view model to work with the NutritionAPI by API Ninja
 *
 */
@Composable
fun foodSearchBox(foodInMealViewModel: FoodInMealViewModel, nutritionAPIViewModel: NutritionAPIViewModel){
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val food = remember{ mutableStateOf("") }
    var foundFoods =  nutritionAPIViewModel.foodResults

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
                    focusManager.clearFocus()
                    nutritionAPIViewModel.findFood(food.value)
                    foundFoods = nutritionAPIViewModel.foodResults

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
            if(foundFoods.size > 0){
                for(food in foundFoods){
                    Row(){
                        Text("${food.elementAt(0).name} \r\n" +
                                "Serving Size: ${food.elementAt(0).serving_size_g}g \r\n" +
                                "Carbohydrates: ${food.elementAt(0).carbohydrates_total_g}g \r\n" +
                                "Protein: ${food.elementAt(0).protein_g}g \r\n" +
                                "Fat: ${food.elementAt(0).fat_total_g}g ")
                    }
                }
            }
            else
            {
                Text("No results found for ${food.value}")
            }

        }
    }

}
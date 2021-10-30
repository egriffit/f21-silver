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
import com.example.workout_companion.api.utility.FoodData
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.NutritionApiNinjaViewModel

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
fun foodSearchBox(foodInMealViewModel: FoodInMealViewModel, apiNinjaViewModel: NutritionApiNinjaViewModel){
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
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
                    focusManager.clearFocus()
                    var foundFoods = apiNinjaViewModel.getFood(food.value)
                    Toast.makeText(
                        context,
                        "${foundFoods?.value?.elementAt(0)?.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Text(
                        "Search",
                        fontSize = 15.sp
                    )
                }

            }
        }
        Button(onClick = {

        }) {
            Text(
                "click",
                fontSize = 15.sp
            )
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
package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workout_companion.viewmodel.NutritionAPIViewModel
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutritionItem
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.entity.RecipeEntity
import com.example.workout_companion.entity.RecipeWithFoodsEntity
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.FoodTypeViewModel
import com.example.workout_companion.viewmodel.MealViewModel
import com.vanpra.composematerialdialogs.*

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
 *  @param navController, a NavController
 *  @param meal, name of meal
 */
@Composable
fun FoodSearchBox(navController: NavController, meal: String){
    val food = remember{ mutableStateOf("") }
    val selectedFoodName = remember { mutableStateOf("")}

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
                    navController.navigate("searchFood/${food.value}/${meal}")

                }) {
                    Text(
                        "Search",
                        fontSize = 15.sp
                    )
                }
            }
        }
        Text(selectedFoodName.value)
    }
}


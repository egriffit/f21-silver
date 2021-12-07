package com.example.workout_companion.view.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.workout_companion.entity.MealWithFoodsEntity
import kotlinx.coroutines.withContext

/***
 * Composable to display a column of foods in a meal
 * It consists of:
 * a column of foods names
 * a foodSearchBox
 *
 *
 * @param navController, a NavController to navigate to different view
 * @param meal, string
 * @param open, a mutableStateBoolean to check if foods list is visible
 * @param foundFoods, a list of MealWithFoodsEntity objects
 *
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FoodsInMeals(
    navController: NavController,
    meal: String,
    open: MutableState<Boolean>,
    foundFoods: List<MealWithFoodsEntity>
) {
    if(open.value){
        Column(
            modifier = Modifier.fillMaxHeight()
        ){
            Text("Foods")
            Column {
                //display current foods in meal
                if (foundFoods != null) {
                    if (foundFoods.isNotEmpty()) {
                        foundFoods.forEach { food ->
                            food.foods.forEach { f ->
                                Row {
                                    FoodRow(navController, f, meal, food.food_in_meal.servings)
                                }
                            }
                        }
                    }
                }
            }

        }
        //search box to add food

        FoodSearchBox(navController, meal)
    }
}
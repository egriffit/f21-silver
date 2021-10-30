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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.workout_companion.api.nutrition_api_ninja.NutritionApiNinjaApi
import com.example.workout_companion.api.utility.FoodData
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.MealViewModel
import com.example.workout_companion.viewmodel.NutritionApiNinjaViewModel
import java.time.LocalDate

/***
 * Composable form to add a meal
 * It consists of:
 * a MealList, a lazyColumn of Meals)
 * a button to add a meal
 * a button to remove meals
 *
 * @param navController, a NavController to navigate to different views
 * @param mealViewModel, a  view model to work with meal table
 * @param foundINMealViewModel, a view model to work with the the food_in_meal table
 * @param apiNinjaViewModel, a view model to work with the NutritionAPI by API Ninja
 *
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddMealForm(navController: NavController, mealViewModel: MealViewModel,
                foodInMealViewModel: FoodInMealViewModel, apiNinjaViewModel: NutritionApiNinjaViewModel){
    val foundMeals = mealViewModel.getAllMeals.observeAsState(listOf()).value

    val mealName = remember{ mutableStateOf("")}

    Column(
        modifier = Modifier.fillMaxSize(),

    ){

        //display meals
        if (foundMeals != null) {
            MealList(foundMeals, foodInMealViewModel, apiNinjaViewModel)
        }
        //display form to add meals
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                    addFood(mealName, mealViewModel)
//                    mealViewModel.insert(mealName.value)
//                    mealName.value = ""
                }){
                    Text("Add meal",
                        fontSize = 15.sp)
                }
            }
        }
        Button(onClick = {
            mealViewModel.deleteAll()
        }){
            Text("Remove All Meals",
                fontSize = 15.sp)
        }
    }
}


/***
 * function used to create a meal from a string provided and store it in the meal table
 *
 * @param mealName, a MutableState<String>
 * @param mealViewModel, a  view model to work with meal table
 *
 */
@RequiresApi(Build.VERSION_CODES.O)
fun addFood(mealName: MutableState<String>, mealViewModel: MealViewModel, ){
    if(mealName.value != ""){
        mealViewModel.insert(mealName.value)
        mealName.value = ""
    }
}
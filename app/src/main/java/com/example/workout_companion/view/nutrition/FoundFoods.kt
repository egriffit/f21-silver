package com.example.workout_companion.view.nutrition

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutritionItem
import com.example.workout_companion.entity.FoodInMealEntity
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.FoodTypeViewModel
import com.example.workout_companion.viewmodel.MealViewModel
import com.example.workout_companion.viewmodel.NutritionAPIViewModel
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/***
 * Composable to display a the found foods from the Nutrition API
 *
 *
 * @param navController, a NavController to navigate to different view
 * @param food, string
 * @param meal, a string
 *
 */
@SuppressLint("NewApi")
@Composable
fun FoundFoods(navController: NavController, food: String?, meal: String?,
               foodTypeViewModel: FoodTypeViewModel, mealViewModel: MealViewModel,
               foodInMealViewModel: FoodInMealViewModel, nutritionAPIViewModel: NutritionAPIViewModel
){
    val coroutineScope = rememberCoroutineScope()
    var foundFoods =  nutritionAPIViewModel.foodResults
    val foodState = remember{ mutableStateOf("") }
    val dialogState = rememberMaterialDialogState()
    val selectedFoodIndex = remember { mutableStateOf(0)}
    var selectedFoundFood: ApiNinjaNutritionItem = ApiNinjaNutritionItem(
        0.0, 0.0, 0,
        0.0,0.0,0.0,
        "",0, 0.0,
        0.0, 0, 0.0)
    val selectedFoodName = remember { mutableStateOf("")}
    if (food != null) {
        foodState.value = food
        nutritionAPIViewModel.findFood(food)
    }

    //dialog box called when a food is searched for
    val textState = remember { mutableStateOf(TextFieldValue()) }
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {
            Column(){
                Row(modifier = Modifier.padding(start = 100.dp, end = 20.dp)
                    .fillMaxWidth()){
                    Button(onClick = {
                        selectedFoundFood = foundFoods.elementAt(0).elementAt(selectedFoodIndex.value)
                        selectedFoodName.value = selectedFoundFood.name
                        //store the food in the food table
                        var foodType = FoodTypeEntity(0, selectedFoundFood.name, "-1",
                            selectedFoundFood.serving_size_g,
                            selectedFoundFood.calories, selectedFoundFood.carbohydrates_total_g,
                            selectedFoundFood.protein_g, selectedFoundFood.fat_total_g)
                        coroutineScope.launch(Dispatchers.IO){

                            foodTypeViewModel.addFoodType(foodType)
                            //retrieve the food id and meal id
                            delay(1000L)

                            var foodId = 0
                            var mealId = 0
                            foodTypeViewModel.getId(foodType)
                            delay(1000L)

                            if (meal != null) {
                                mealViewModel.getMealId(meal)
                                delay(1000L)
                            }
                            foodId = foodTypeViewModel.foodID
                            mealId = mealViewModel.mealId

                            //create a food_inMeal_object and add to database
                            if(((mealId !=0)&& (mealId != null) )&&(foodId !=0)&& (foodId != null)){
                                var foodInMeal = FoodInMealEntity(mealId, foodId, 1.0)
                                foodInMealViewModel.insert(foodInMeal)
                                mealViewModel.addToMeal(foodType.name, foodType.calories, foodType.carbohydrates,
                                    foodType.protein, foodType.fat,)
                            }
                        }
                        //remove the found foods from my snapshotstate
                        foundFoods.clear()
                        //navigate back to nutrition overview view
                        navController.navigate("NutritionOverview")

                    }){
                        Text("Add Food")
                    }
                    Spacer(modifier = Modifier.padding(start=50.dp))
                    Button(onClick = {
                        navController.navigate("NutritionOverview")
                    }){
                        Text("Cancel")
                    }
                }
                Spacer(modifier = Modifier.padding(bottom = 50.dp))
            }

        },
        content = {

            //create the pick food form
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 5.dp, top = 80.dp, bottom = 20.dp, end = 5.dp)
            ){
                foodRadioButtonList(navController, foundFoods, foodState, selectedFoodIndex)
            }
        }
    )
}
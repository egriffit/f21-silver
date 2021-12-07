package com.example.workout_companion.view.nutrition

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.entity.*
import com.example.workout_companion.sampleData.emptyFoodTypeEntity
import com.example.workout_companion.sampleData.emptyNutritionAPiItem
import com.example.workout_companion.sampleData.emptyRecipeEntity
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.*
import kotlinx.coroutines.*
import kotlinx.coroutines.withContext
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
import androidx.compose.foundation.layout.*


/**
 * FoodIndex - simple dataclass to keep track of the selected radiobutton for found foods
 * and recipes and ties it to the index for the appropriate list of FoodTypeEntity, APINutrition,
 * or RecipeEntity objects
 * @property type, type of list, DBFood, API, Recipe,
 * @property foodName, name of food,
 * @property index, integer for the index in the appropriate list
 */
data class FoodIndex(
    var type: String,
    var foodName: String,
    var index: Int
)

/***
 * Composable to display a the found foods from the Nutrition API
 *
 *
 * @param navController, a NavController to navigate to different view
 * @param food, name of food being added
 * @param meal, name of meal
 * @param foodTypeViewModel, view model to work with the food_type table
 * @param mealViewModel, view model to work with the meal table
 * @param recipeViewModel, view model to work with the recipe table
 * @param foodInRecipeViewModel, view model to work with the food_in_recipe table
 * @param nutritionAPIViewModel, view model to work with the nutrition api by API Ninja
 *
 */
@SuppressLint("NewApi")
@Composable
fun FoundFoods(
    navController: NavController, food: String?, meal: String?,
    dbFoods: List<FoodTypeEntity>,
    dbRecipes: List<RecipeEntity>,
    foodTypeViewModel: FoodTypeViewModel, mealViewModel: MealViewModel,
    foodInMealViewModel: FoodInMealViewModel,
    recipeViewModel: RecipeViewModel, foodInRecipeViewModel: FoodInRecipeViewModel,
    nutritionAPIViewModel: NutritionAPIViewModel
) {
    val foodState = remember { mutableStateOf("") }
    val mealId = mealViewModel.mealId.observeAsState().value
    val foundMeal = mealViewModel.foundMeal.observeAsState().value

    var foodId = foodTypeViewModel.foodID.observeAsState().value
    val recipe = foodInRecipeViewModel.foodsInRecipe.observeAsState().value
    val foodIndexes = mutableListOf<FoodIndex>()

    //get foods
    if (food != null) {
        foodState.value = food
        LaunchedEffect(key1 = Unit, block = {
                withContext(Dispatchers.IO) {
                    if(meal != null){
                        mealViewModel.getMealId(meal)
                    }
                }
            //get foods from nutrition api
            nutritionAPIViewModel.findFood(food)
        })
    }
    val apiFoods = nutritionAPIViewModel.foodResults

    //dialog box called when a food is searched for
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {
            Column{
                Row(modifier = Modifier
                    .padding(start = 100.dp, end = 20.dp)
                    .fillMaxWidth()) {

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
                if(meal != null){
                     FoodRadioButtonList(navController, meal, dbFoods, dbRecipes, apiFoods)
                }
            }
        }
    )
}
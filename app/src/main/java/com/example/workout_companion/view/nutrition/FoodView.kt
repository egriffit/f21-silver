package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.entity.FoodInMealEntity
import com.example.workout_companion.entity.FoodInRecipeEntity
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.sampleData.emptyFoodTypeEntity
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.*
import kotlinx.coroutines.*

@Composable
fun FoodView(
    navController: NavController,
    food: String?,
    servingSize: Double?,
    calories: Double?,
    carbohydrates: Double?,
    protein: Double?,
    fat: Double?,
    meal: String?,
    foodTypeViewModel: FoodTypeViewModel,
    mealViewModel: MealViewModel,
    foodInMealViewModel: FoodInMealViewModel,
){
    val foodId = foodTypeViewModel.foodID.observeAsState().value
    var foodType: FoodTypeEntity = emptyFoodTypeEntity
    val mealId = mealViewModel.mealId.observeAsState().value
    if(food != null && servingSize != null && calories != null &&
        carbohydrates != null && protein != null && fat != null)
        {
            foodType = FoodTypeEntity(
                0, food, "-1",
                servingSize, calories, carbohydrates,
                protein, fat
            )
            LaunchedEffect(key1 = Unit, block = {
                withContext(Dispatchers.IO) {
                    if (meal != null) {
                        mealViewModel.getMealId(meal)
                    }
                }
                withContext(Dispatchers.IO){
                    foodTypeViewModel.getId(foodType)
                }
            })
        }

    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text("Meal: $meal ")
                Text("ID: $mealId ")
                Text("Food: $food ")
                Text("ID: $foodId ")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (food != null) {
                        Text(
                            text = food,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Serving Size: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("$servingSize g")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Calories: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("$calories cal")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Carbohydrates: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("$carbohydrates g")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Protein: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("$protein g")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Fat: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("$fat g")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {

                            runBlocking {
                                //add food to the food_type table
                                val jobF1: Job = launch(context = Dispatchers.IO){
                                    foodTypeViewModel.addFoodType(foodType)
                                }
                                jobF1.join()
                                //get the foodId
                                val jobF2: Job = launch(context = Dispatchers.IO){
                                    foodTypeViewModel.getId(foodType)
                                }
                                jobF2.join()
                                //add food to food_in_meal table
                                val jobF3: Job = launch(context = Dispatchers.IO) {
                                    //create a food_inMeal_object and add to database
                                    if (mealId != null && foodId != null) {
                                        if ((mealId != 0) && (foodId != 0)) {
                                            val foodInMeal = FoodInMealEntity(mealId, foodId, 1.0)
                                            foodInMealViewModel.insert(foodInMeal)
                                            mealViewModel.addToMeal(
                                                foodType.name,
                                                foodType.calories,
                                                foodType.carbohydrates,
                                                foodType.protein,
                                                foodType.fat
                                            )
                                        }
                                    }
                                }

                                //Wait for record to be inserted before navigating
                                //To Nutrition Overview
                                jobF3.join()
                                navController.navigate("NutritionOverview")
                            }
                        }) {
                        Text("Add Food ")
                    }
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Button(onClick = { cancel(navController) }) {
                        Text("Cancel")
                    }
                }
            }
        })
    }

@Composable
fun FoodView(
    navController: NavController,
    food: String?,
    servingSize: Double?,
    calories: Double?,
    carbohydrates: Double?,
    protein: Double?,
    fat: Double?,
    recipe: String?,
    foodTypeViewModel: FoodTypeViewModel,
    recipeViewModel: RecipeViewModel,
    foodInRecipeViewModel: FoodInRecipeViewModel
){
    val foodId = foodTypeViewModel.foodID.observeAsState().value
    val recipeId = recipeViewModel.recipeID.observeAsState().value

    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (food != null) {
                        Text(
                            text = food,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Serving Size: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("$servingSize g")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Calories: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("$calories cal")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Carbohydrates: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("$carbohydrates g")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Protein: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("$protein g")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Fat: ")
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Text("$fat g")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        //!. add the food to the database
                        if(food != null && servingSize != null && calories != null &&
                            carbohydrates != null && protein != null && fat != null) {
                            val foodType = FoodTypeEntity(
                                0, food, "-1",
                                servingSize, calories, carbohydrates,
                                protein, fat
                            )
                                foodTypeViewModel.addFoodType(foodType)
                                //retrieve the food id and meal id


                                foodTypeViewModel.getId(foodType)

                                if (recipe != null) {
                                    recipeViewModel.getRecipeID(recipe)
                                }

                                //create a food_inMeal_object and add to database
                                if(foodId != null && recipeId != null) {
                                    if ((recipeId != 0) && (foodId != 0)) {
                                        val foodInRecipe = FoodInRecipeEntity(recipeId, foodId, 1.0)
                                        foodInRecipeViewModel.insert(foodInRecipe)
                                    }
                                }
                        }
                        if(recipe != null){
                            navController.navigate("recipe/$recipe")
                        }else{
                            navController.navigate("nutritionOverview")
                        }
                    }) {
                        Text("Add Food ")
                    }
                    Spacer(modifier = Modifier.padding(start = 30.dp))
                    Button(onClick = { cancelRecipe(navController, recipe) }) {
                        Text("Cancel")
                    }
                }
            }
        })
}

fun cancel(navController: NavController){
    navController.navigate("NutritionOverview")
}

fun cancelRecipe(navController: NavController, recipe: String?){
    if(recipe != null){
        navController.navigate("recipe/$recipe")
    }else{
        navController.navigate("NutritionOverview")
    }
}
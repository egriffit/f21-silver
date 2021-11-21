package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.entity.FoodInMealEntity
import com.example.workout_companion.entity.FoodInRecipeEntity
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            Column(
                modifier = Modifier.fillMaxWidth()
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
                        if(food != null && servingSize != null && calories != null &&
                                carbohydrates != null && protein != null && fat != null) {
                            val foodType = FoodTypeEntity(
                                0, food, "-1",
                                servingSize, calories, carbohydrates,
                                protein, fat
                            )
                            coroutineScope.launch(Dispatchers.IO) {

                                foodTypeViewModel.addFoodType(foodType)
                                //retrieve the food id and meal id
                                delay(1000L)


                                foodTypeViewModel.getId(foodType)
                                delay(1000L)

                                if (meal != null) {
                                    mealViewModel.getMealId(meal)
                                    delay(1000L)
                                }
                                val foodId = foodTypeViewModel.foodID
                                val mealId = mealViewModel.mealId

                                //create a food_inMeal_object and add to database
                                if ((mealId != 0) && (foodId != 0)) {
                                    val foodInMeal = FoodInMealEntity(mealId, foodId, 1.0)
                                    foodInMealViewModel.insert(foodInMeal)
                                    mealViewModel.addToMeal(
                                        foodType.name, foodType.calories, foodType.carbohydrates,
                                        foodType.protein, foodType.fat,
                                    )
                                }
                            }
                        }
                        navController.navigate("NutritionOverview")
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
    recipeView: RecipeViewModel,
    foodInRecipeViewModel: FoodInRecipeViewModel
){
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            Column(
                modifier = Modifier.fillMaxWidth()
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
                        if(food != null && servingSize != null && calories != null &&
                            carbohydrates != null && protein != null && fat != null) {
                            val foodType = FoodTypeEntity(
                                0, food, "-1",
                                servingSize, calories, carbohydrates,
                                protein, fat
                            )
                            coroutineScope.launch(Dispatchers.IO) {

                                foodTypeViewModel.addFoodType(foodType)
                                //retrieve the food id and meal id
                                delay(1000L)


                                foodTypeViewModel.getId(foodType)
                                delay(1000L)

                                if (recipe != null) {
                                    recipeView.getRecipeID(recipe)
                                    delay(1000L)
                                }
                                val foodId = foodTypeViewModel.foodID
                                val recipeId = recipeView.recipeID

                                //create a food_inMeal_object and add to database
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
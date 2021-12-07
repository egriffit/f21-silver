package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType



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
    val foundMeal = mealViewModel.foundMeal.observeAsState(listOf()).value
    val foodsInMeal = foodInMealViewModel.foundFoods.observeAsState(listOf()).value
    val servingState = remember{ mutableStateOf(1.0)}

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
                withContext(Dispatchers.IO){
                    if(meal != null){
                        foodInMealViewModel.getFoodInMeal(meal)
                    }
                }
            })
        }
    if(foodsInMeal.isNotEmpty()){
        foodsInMeal.forEach{ f ->
            if (f.foods.elementAt(0).name == food){
                servingState.value = f.food_in_meal.servings
            }
        }
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
                    OutlinedTextField(
                        value = servingState.value.toString(),
                        onValueChange = {
                            servingState.value = it.toDouble()
                        },
                        label = { Text(text = "Servings") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {

                            runBlocking {
                                //get the foodId
                                val jobF1: Job = launch(context = Dispatchers.IO){
                                    foodTypeViewModel.getId(foodType)
                                    mealViewModel.getMealsByName(meal!!)
                                }
                                jobF1.join()
                                //add food to food_in_meal table
                                val jobF2: Job = launch(context = Dispatchers.IO) {
                                    //create a food_inMeal_object and add to database
                                    if (mealId != null && foodId != null) {
                                        if ((mealId != 0) && (foodId != 0)) {
                                            val foodInMeal = FoodInMealEntity(mealId, foodId, servingState.value)
                                            if(servingState.value != 0.0){
                                                foodInMealViewModel.insert(foodInMeal)
                                            }else{
                                                foodInMealViewModel.delete(foodInMeal)
                                            }
//                                            if (meal != null && foundMeal.isNotEmpty()) {
//                                             foodInMealViewModel.calcDailyTotal(mealId)
//                                            }
                                        }
                                    }
                                }

                                //Wait for record to be inserted before navigating
                                //To Nutrition Overview
                                jobF2.join()
                                val jobF3: Job = launch(Dispatchers.IO){
                                    foodInMealViewModel.calcDailyTotal(mealId!!)
                                }
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

    var foodType: FoodTypeEntity = emptyFoodTypeEntity
    val foundRecipe = recipeViewModel.foundRecipes.observeAsState(listOf()).value
    val foodsInRecipe = foodInRecipeViewModel.foodsInRecipe.observeAsState(listOf()).value
    val servingState = remember{ mutableStateOf(1.0)}

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
                if (recipe != null) {
                    recipeViewModel.getRecipeID(recipe)
                }
            }
            withContext(Dispatchers.IO){
                foodTypeViewModel.getId(foodType)
            }
            withContext(Dispatchers.IO){
                if(recipe != null){
                    foodInRecipeViewModel.getFoodInRecipe(recipe)
                }
            }
        })
    }
    if(foodsInRecipe.isNotEmpty()){
        foodsInRecipe.forEach{ f ->
            if (f.foods.elementAt(0).name == food){
                servingState.value = f.food_in_recipe.servings
            }
        }
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
                    OutlinedTextField(
                        value = servingState.value.toString(),
                        onValueChange = {
                            servingState.value = it.toDouble()
                        },
                        label = { Text(text = "Servings") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {

                        runBlocking {
                            //get the foodId
                            val jobF1: Job = launch(context = Dispatchers.IO){
                                foodTypeViewModel.getId(foodType)
                                if (recipe != null) {
                                    recipeViewModel.getRecipeID(recipe)
                                }
                            }
                            jobF1.join()
                            //add food to food_in_meal table
                            val jobF2: Job = launch(context = Dispatchers.IO) {
                                if (foodId != null && recipeId != null) {
                                    if ((recipeId != 0) && (foodId != 0)) {
                                        val foodInRecipe = FoodInRecipeEntity(recipeId, foodId, servingState.value)
                                        if(servingState.value != 0.0){
                                            foodInRecipeViewModel.insert(foodInRecipe)
                                        }else{
                                            foodInRecipeViewModel.delete(foodInRecipe)
                                        }
                                    }
                                }
                            }

                            //Wait for record to be inserted before navigating
                            //To Nutrition Overview
                            jobF2.join()
                            if(recipe != null){
                                navController.navigate("recipe/$recipe")
                            }else{
                                navController.navigate("nutritionOverview")
                            }
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
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
    foodTypeViewModel: FoodTypeViewModel, mealViewModel: MealViewModel,
    foodInMealViewModel: FoodInMealViewModel,
    recipeViewModel: RecipeViewModel, foodInRecipeViewModel: FoodInRecipeViewModel,
    nutritionAPIViewModel: NutritionAPIViewModel
) {
    val foodState = remember { mutableStateOf("") }
    val dbRecipes = recipeViewModel.foundRecipes.observeAsState(listOf()).value
    val dbFoods = foodTypeViewModel.foodResults.observeAsState(listOf()).value
    val mealId = mealViewModel.mealId.observeAsState().value
    var foodId = foodTypeViewModel.foodID.observeAsState().value
    val recipe = foodInRecipeViewModel.foodsInRecipe.observeAsState().value

    //get foods
    if (food != null) {
        foodState.value = food
        LaunchedEffect(key1 = Unit, block = {
                withContext(Dispatchers.IO) {
                    if(meal != null){
                        mealViewModel.getMealId(meal)
                    }
                }
            //1. get existing foods that match the search term from database
                withContext(Dispatchers.IO){
                    foodTypeViewModel.getFood(food)
                }

            //2. get recipes from database that match the search term
                withContext(Dispatchers.IO) {
                     recipeViewModel.getRecipe(food)
                }
            //3. get foods from nutrition api
            nutritionAPIViewModel.findFood(food)
        })
    }

    val apiFoods = nutritionAPIViewModel.foodResults
    //Store foods names in an array with type
    val foodIndexes = mutableListOf<FoodIndex>()
    dbFoods?.forEachIndexed{ index, f ->
        foodIndexes.add(FoodIndex(f.name, "DBFood", index))
    }
    dbRecipes?.forEachIndexed{ index, r ->
        foodIndexes.add(FoodIndex(r.name, "Recipe", index))
    }
    if(apiFoods.size != 0){
        apiFoods.elementAt(0).forEachIndexed{index, apiFood ->
            foodIndexes.add(FoodIndex(apiFood.name, "API", index))
        }
    }


    //create a list of pairs with type and relative index
    //keeps track of indexes, states and entities
    val selectedFoodIndex = remember{mutableStateOf(FoodIndex("", "", 0))}
    val selectedFoundAPIFood = remember {mutableStateOf(emptyNutritionAPiItem)}
    val selectedFoodName = remember { mutableStateOf("")}
    val selectedFoundRecipe = remember { mutableStateOf(emptyRecipeEntity)}
    val selectedFoundFood = remember { mutableStateOf(emptyFoodTypeEntity)}


    //dialog box called when a food is searched for
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {
            Column{
                    Text("Meal: $meal ")
                    Text("ID: $mealId ")
                Text("Food: ${selectedFoodName.value} ")
                Text("FoodId: $foodId ")
                Row(modifier = Modifier
                    .padding(start = 100.dp, end = 20.dp)
                    .fillMaxWidth()) {
                    Button(onClick = {
                        //figure out which food was selected
                        if (selectedFoodIndex.value.type == "DBFood") {
                            if (dbFoods != null) {
                                selectedFoundFood.value = dbFoods[selectedFoodIndex.value.index]
                                selectedFoundRecipe.value = emptyRecipeEntity
                                selectedFoundAPIFood.value = emptyNutritionAPiItem
                                selectedFoodName.value = selectedFoundRecipe.value.name
                            }
                        }
                        if (selectedFoodIndex.value.type == "Recipe") {
                            if (dbRecipes != null) {
                                selectedFoundRecipe.value = dbRecipes[selectedFoodIndex.value.index]
                                selectedFoundFood.value = emptyFoodTypeEntity
                                selectedFoundAPIFood.value = emptyNutritionAPiItem
                                selectedFoodName.value = selectedFoundRecipe.value.name
                            }
                        }
                        if (selectedFoodIndex.value.type == "API") {
                            selectedFoundAPIFood.value =
                                apiFoods.elementAt(0)[selectedFoodIndex.value.index]
                            selectedFoundRecipe.value = emptyRecipeEntity
                            selectedFoundFood.value = emptyFoodTypeEntity
                            selectedFoodName.value = selectedFoundAPIFood.value.name
                        }
                        if (selectedFoodIndex.value.type == "") {
                            selectedFoundRecipe.value = emptyRecipeEntity
                            selectedFoundFood.value = emptyFoodTypeEntity
                            selectedFoundAPIFood.value = emptyNutritionAPiItem
                            selectedFoodName.value = ""
                        }
                        //Case 1: Add Food
                        if(selectedFoodIndex.value.type == "DBFood"){
                            runBlocking {
                                //add food to meal
                                val jobF1: Job = launch(context = Dispatchers.IO) {

                                    if (selectedFoundFood.value.name != "") {
                                        //retrieve the meal id
                                        if (meal != null) {
                                            mealViewModel.getMealId(meal)
                                        }
                                        foodId = selectedFoundFood.value.id
                                    }
                                }
                                //wait for the ids to be found before adding to FoodInMeal Table
                                jobF1.join()
                                val jobF2: Job = launch(context = Dispatchers.IO) {
                                    //create a food_inMeal_object and add to database
                                    if (mealId != null && foodId != null) {
                                        if ((mealId != 0) && (foodId != 0)) {
                                            val foodInMeal = FoodInMealEntity(mealId, foodId!!, 1.0)
                                            foodInMealViewModel.insert(foodInMeal)
                                            mealViewModel.addToMeal(
                                                selectedFoundFood.value.name,
                                                selectedFoundFood.value.calories,
                                                selectedFoundFood.value.carbohydrates,
                                                selectedFoundFood.value.protein,
                                                selectedFoundFood.value.fat
                                            )
                                        }
                                    }
                                }
                                //Wait for record to be inserted before navigating
                                //To Nutrition Overview
                                jobF2.join()
                                navController.navigate("NutritionOverview")
                            }
                        }

                        //case 2 add recipe foods to meal
                        if(selectedFoodIndex.value.type == "Recipe"){
                            runBlocking {
                                //add food to meal
                                val jobR1: Job = launch(context = Dispatchers.IO) {
                                    if (selectedFoundRecipe.value.name != "") {

                                        if (meal != null) {
                                            mealViewModel.getMealId(meal)
                                        }
                                        //retrieve the foods in the recipe
                                        foodInRecipeViewModel.getFoodInRecipe(selectedFoundRecipe.value.name)
                                    }
                                }
                                //Wait for ids to be found first
                                jobR1.join()
                                val jobR2: Job = launch(context= Dispatchers.IO) {
                                    if (recipe != null) {
                                        val recipeFoods: List<FoodTypeEntity> =
                                            recipe.elementAt(0).foods
                                        recipeFoods.forEach {
                                            if ((mealId != null) && (it.id != 0)) {
                                                val foodInMeal =
                                                    FoodInMealEntity(mealId, it.id, 1.0)
                                                foodInMealViewModel.insert(foodInMeal)
                                                mealViewModel.addToMeal(
                                                    it.name,
                                                    it.calories,
                                                    it.carbohydrates,
                                                    it.protein,
                                                    it.fat
                                                )
                                            }
                                        }
                                    }
                                }
                                jobR2.join()
                                navController.navigate("NutritionOverview")
                            }
                        }

                        //case 3 add API foods to meal
                        if(selectedFoodIndex.value.type == "API") {
                            runBlocking {
                                //add found api food to meal
                                if (selectedFoundAPIFood.value.name != "") {
                                    //store the food in the food table
                                    val foodType = FoodTypeEntity(
                                        0,
                                        selectedFoundAPIFood.value.name,
                                        "-1",
                                        selectedFoundAPIFood.value.serving_size_g,
                                        selectedFoundAPIFood.value.calories,
                                        selectedFoundAPIFood.value.carbohydrates_total_g,
                                        selectedFoundAPIFood.value.protein_g,
                                        selectedFoundAPIFood.value.fat_total_g
                                    )
                                    //add food to database
                                    val job: Job = launch(context = Dispatchers.IO) {
                                        foodTypeViewModel.addFoodType(foodType)
                                    }
                                    //wait for food to be inserted before getting id
                                    job.join()
                                    val job2: Job = launch(context = Dispatchers.IO) {
                                        foodTypeViewModel.getId(foodType)
                                    }
                                    //Wait for the ids before add to foodInMealEntity
                                    job2.join()
                                    val job3: Job = launch {
                                        //create a food_inMeal_object and add to database
                                        if (mealId != null && foodId != null) {
                                            if ((mealId != 0) && (foodId != 0)) {
                                                val foodInMeal =
                                                    FoodInMealEntity(mealId, foodId!!, 1.0)
                                                foodInMealViewModel.insert(foodInMeal)
                                                mealViewModel.addToMeal(
                                                    foodType.name,
                                                    foodType.calories,
                                                    foodType.carbohydrates,
                                                    foodType.protein,
                                                    foodType.fat,
                                                )
                                            }
                                        }
                                    }
                                    //wait for food to be added to foodInMeal table before
                                    // navigating to nutrition overview view

                                    job3.join()

                                    //remove the found foods from my snapshot state
                                    apiFoods.clear()
                                    //navigate back to nutrition overview view
                                    navController.navigate("NutritionOverview")
                                }
                            }
                        }
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
                if(meal != null){
                     FoodRadioButtonList(navController, meal, dbFoods, dbRecipes, apiFoods, selectedFoodIndex)
                }
            }
        }
    )
}
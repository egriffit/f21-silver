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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.entity.*
import com.example.workout_companion.sampleData.emptyFoodTypeEntity
import com.example.workout_companion.sampleData.emptyNutritionAPiItem
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/***
 * Composable to display a the found foods from the Nutrition API
 *
 *
 * @param navController, a NavController to navigate to different view
 * @param food, name of food being added
 * @param recipe, name of recipe
 * @param recipeViewModel, view model to work with the recipe table
 * @param foodInRecipeViewModel, view model to work with the food_in_recipe table
 * @param nutritionAPIViewModel, view model to work with the nutrition api by API Ninja
 *
 */
@SuppressLint("NewApi")
@Composable
fun FoundRecipeFoods(
    navController: NavController, food: String?, recipe: String?,
    foodTypeViewModel: FoodTypeViewModel,
    recipeViewModel: RecipeViewModel, foodInRecipeViewModel: FoodInRecipeViewModel,
    nutritionAPIViewModel: NutritionAPIViewModel
) {
    val foodState = remember { mutableStateOf("") }
    var dbFoods: List<FoodTypeEntity>? = listOf()
    //get foods
    if (food != null) {
        foodState.value = food
        //1. get existing foods that match the search term from database
        dbFoods = foodTypeViewModel.getFood(food)
        //2. get foods from nutrition api
        nutritionAPIViewModel.findFood(food)
    }
    val apiFoods = nutritionAPIViewModel.foodResults

    //Store foods names in an array with type
    val foodIndexes = mutableListOf<FoodIndex>()
    dbFoods?.forEachIndexed{ index, f ->
        foodIndexes.add(FoodIndex(f.name, "DBFood", index))
    }
    if(apiFoods.size != 0){
        apiFoods.elementAt(0).forEachIndexed{index, apiFood ->
            foodIndexes.add(FoodIndex(apiFood.name, "API", index))
        }
    }


    //create a list of pairs with type and relative index
    //keeps track of indexes, states and entities
    val selectedFoodIndex = remember{mutableStateOf(FoodIndex("", "", 0))}
    val coroutineScope = rememberCoroutineScope()
    val selectedFoundAPIFood = remember {mutableStateOf(emptyNutritionAPiItem)}
    val selectedFoodName = remember { mutableStateOf("")}
    val selectedFoundFood = remember { mutableStateOf(emptyFoodTypeEntity)}


    //dialog box called when a food is searched for
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {
            Column{
                Row(modifier = Modifier.padding(start = 100.dp, end = 20.dp)
                    .fillMaxWidth()){
                    Button(onClick = {
                        //figure out which food was selected
                        if(selectedFoodIndex.value.type == "DBFood")
                        {
                            if(dbFoods != null) {
                                selectedFoundFood.value = dbFoods[selectedFoodIndex.value.index]
                                selectedFoundAPIFood.value = emptyNutritionAPiItem
                                selectedFoodName.value = selectedFoundFood.value.name
                            }
                        }

                        if(selectedFoodIndex.value.type == "API")
                        {
                            selectedFoundAPIFood.value = apiFoods.elementAt(0)[selectedFoodIndex.value.index]
                            selectedFoundFood.value = emptyFoodTypeEntity
                            selectedFoodName.value = selectedFoundAPIFood.value.name
                        }
                        if(selectedFoodIndex.value.type == ""){
                            selectedFoundFood.value = emptyFoodTypeEntity
                            selectedFoundAPIFood.value = emptyNutritionAPiItem
                            selectedFoodName.value = ""
                        }
                        //add food to recipe
                        if(selectedFoundFood.value.name != "") {
                            //retrieve the meal id
                            if (recipe != null) {
                                recipeViewModel.getRecipeID(recipe)
                            }
                            val foodId = selectedFoundFood.value.id
                            val recipeId = recipeViewModel.recipeID

                            //create a food_inMeal_object and add to database
                            if ((recipeId != 0) && (foodId != 0)) {
                                val foodInRecipe = FoodInRecipeEntity(recipeId, foodId, 1.0)
                                foodInRecipeViewModel.insert(foodInRecipe)
                            }

                            //add found api food to recipe
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
                                coroutineScope.launch(Dispatchers.IO) {

                                    foodTypeViewModel.addFoodType(foodType)
                                    //retrieve the food id and meal id
                                    delay(1000L)

                                    foodTypeViewModel.getId(foodType)
                                    delay(1000L)

                                    if (recipe != null) {
                                        recipeViewModel.getRecipeID(recipe)
                                        delay(1000L)
                                    }
                                    val foodId = foodTypeViewModel.foodID
                                    val recipeId = recipeViewModel.recipeID

                                    //create a Food_in_recipe object and add to database
                                    if ((recipeId != 0) && (foodId != 0)) {
                                        val foodInRecipe = FoodInRecipeEntity(recipeId, foodId, 1.0)
                                        foodInRecipeViewModel.insert(foodInRecipe)
                                    }
                                }

                                //remove the found foods from my snapshot state
                                apiFoods.clear()
                                //navigate back to nutrition overview view
                            }
                        }
                        if(recipe != null){
                            navController.navigate("recipe/$recipe")
                        }else{
                            navController.navigate("nutritionOverview")
                        }
                    }){
                        Text("Add Food")
                    }
                    Spacer(modifier = Modifier.padding(start=50.dp))
                    Button(onClick = {
                        if(recipe != null) {
                            navController.navigate("recipe/$recipe")
                        }else{
                            navController.navigate("nutritionOverview")
                        }
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
                if(recipe != null){
                     FoodRadioButtonList(navController, recipe, dbFoods, apiFoods, selectedFoodIndex)
                }
            }
        }
    )
}
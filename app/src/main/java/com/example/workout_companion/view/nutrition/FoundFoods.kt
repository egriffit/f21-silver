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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutritionItem
import com.example.workout_companion.entity.*
import com.example.workout_companion.sampleData.emptyFoodTypeEntity
import com.example.workout_companion.sampleData.emptyNutritionAPiItem
import com.example.workout_companion.sampleData.emptyRecipeEntity
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.*
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/***
 * Composable to display a the found foods from the Nutrition API
 *
 *
 * @param navController, a NavController to navigate to different view
 * @param food, string
 * @param meal, a string
 *
 */
data class FoodIndex(
    var type: String,
    var foodName: String,
    var index: Int
)
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
    var dbFoods: List<FoodTypeEntity>? = listOf()
    var dbRecipes: List<RecipeEntity>? = listOf()
    //get foods
    if (food != null) {
        foodState.value = food
        //1. get existing foods that match the search term from database
        dbFoods = foodTypeViewModel.getFood(food)
        //2. get recipes from database that match the search term
        dbRecipes = recipeViewModel.getRecipe(food)
        //3. get foods from nutrition api
        nutritionAPIViewModel.findFood(food)
    }
    var apiFoods = nutritionAPIViewModel.foodResults

    //Store foods names in an array with type
    var foodIndexes = mutableListOf<FoodIndex>()
    if(dbFoods != null){
        dbFoods.forEachIndexed{index, food ->
            foodIndexes.add(FoodIndex(food.name, "DBFood", index))
        }
    }
    if(dbRecipes != null){
        dbRecipes.forEachIndexed{index, recipe ->
            foodIndexes.add(FoodIndex(recipe.name, "Recipe", index))
        }
    }
    if(apiFoods != null && apiFoods.size != 0){
        apiFoods.elementAt(0).forEachIndexed{index, apiFood ->
            foodIndexes.add(FoodIndex(apiFood.name, "API", index))
        }
    }


    //create a list of pairs with type and relative index
    //keek track of indexes, states and entities
    val selectedFoodIndex = remember{mutableStateOf(FoodIndex("", "", 0))}
    val coroutineScope = rememberCoroutineScope()
    var selectedFoundAPIFood = remember {mutableStateOf(emptyNutritionAPiItem)}
    val selectedFoodName = remember { mutableStateOf("")}
    var selectedFoundRecipe = remember { mutableStateOf(emptyRecipeEntity)}
    var selectedFoundFood = remember { mutableStateOf(emptyFoodTypeEntity)}


    //dialog box called when a food is searched for
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {
            Column(){
                Row(modifier = Modifier.padding(start = 100.dp, end = 20.dp)
                    .fillMaxWidth()){
                    Button(onClick = {
                        //figure out which food was selected
                        if(selectedFoodIndex.value.type == "DBFood")
                        {
                            if(dbFoods != null) {
                                selectedFoundFood.value = dbFoods[selectedFoodIndex.value.index]
                                selectedFoundRecipe.value = emptyRecipeEntity
                                selectedFoundAPIFood.value = emptyNutritionAPiItem
                                selectedFoodName.value = selectedFoundRecipe.value.name
                            }
                        }
                        if(selectedFoodIndex.value.type == "Recipe")
                        {
                            if(dbRecipes != null) {
                                selectedFoundRecipe.value = dbRecipes[selectedFoodIndex.value.index]
                                selectedFoundFood.value = emptyFoodTypeEntity
                                selectedFoundAPIFood.value = emptyNutritionAPiItem
                                selectedFoodName.value = selectedFoundRecipe.value.name
                            }
                        }
                        if(selectedFoodIndex.value.type == "API")
                        {
                            selectedFoundAPIFood.value = apiFoods.elementAt(0)[selectedFoodIndex.value.index]
                            selectedFoundRecipe.value = emptyRecipeEntity
                            selectedFoundFood.value = emptyFoodTypeEntity
                            selectedFoodName.value = selectedFoundAPIFood.value.name
                        }
                        if(selectedFoodIndex.value.type == ""){
                            selectedFoundRecipe.value = emptyRecipeEntity
                            selectedFoundFood.value = emptyFoodTypeEntity
                            selectedFoundAPIFood.value = emptyNutritionAPiItem
                            selectedFoodName.value = ""
                        }
                        //add food to meal
                        if(selectedFoundFood.value.name != "")
                        {
                            //retrieve the meal id
                            if (meal != null) {
                                mealViewModel.getMealId(meal)
                            }
                            val foodId = selectedFoundFood.value.id
                            val mealId = mealViewModel.mealId

                            //create a food_inMeal_object and add to database
                            if((mealId !=0) &&(foodId !=0)){
                                val foodInMeal = FoodInMealEntity(mealId, foodId, 1.0)
                                foodInMealViewModel.insert(foodInMeal)
                                mealViewModel.addToMeal(selectedFoundFood.value.name,
                                    selectedFoundFood.value.calories,
                                    selectedFoundFood.value.carbohydrates,
                                    selectedFoundFood.value.protein,
                                    selectedFoundFood.value.fat)
                            }
                        }
                        //add found recipe to meal
                        if(selectedFoundRecipe.value.name != "")
                        {

                            if (meal != null) {
                                mealViewModel.getMealId(meal)
                            }
                            //retrieve the foods in the recipe
                            var recipe: List<RecipeWithFoodsEntity>? = foodInRecipeViewModel.getFoodInRecipe(selectedFoundRecipe.value.name)
                            if(recipe != null){
                                var recipeFoods: List<FoodTypeEntity> = recipe.elementAt(0).foods
                                var mealId = mealViewModel.mealId
                                if(recipeFoods != null){
                                    recipeFoods.forEach{
                                        if((mealId !=0) &&(it.id !=0)){
                                            val foodInMeal = FoodInMealEntity(mealId, it.id, 1.0)
                                            foodInMealViewModel.insert(foodInMeal)
                                            mealViewModel.addToMeal(it.name,
                                                it.calories,
                                                it.carbohydrates,
                                                it.protein,
                                                it.fat)
                                        }
                                    }
                                }
                            }
                        }

                        //add found api food to meal
                        if(selectedFoundAPIFood.value.name != "")
                        {
                        //store the food in the food table
                        val foodType = FoodTypeEntity(0, selectedFoundAPIFood.value.name, "-1",
                            selectedFoundAPIFood.value.serving_size_g,
                            selectedFoundAPIFood.value.calories, selectedFoundAPIFood.value.carbohydrates_total_g,
                            selectedFoundAPIFood.value.protein_g, selectedFoundAPIFood.value.fat_total_g)
                        coroutineScope.launch(Dispatchers.IO){

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
                            if((mealId !=0) &&(foodId !=0)){
                                val foodInMeal = FoodInMealEntity(mealId, foodId, 1.0)
                                foodInMealViewModel.insert(foodInMeal)
                                mealViewModel.addToMeal(foodType.name, foodType.calories, foodType.carbohydrates,
                                    foodType.protein, foodType.fat,)
                            }
                        }

                        //remove the found foods from my snapshotstate
                        apiFoods.clear()
                        //navigate back to nutrition overview view
                        navController.navigate("NutritionOverview")

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
                    Text("Don't crash")
                //      FoodRadioButtonList(navController, meal, selectedFoodName.value, dbFoods, dbRecipes, apiFoods, selectedFoodIndex)
                }
            }
        }
    )
}
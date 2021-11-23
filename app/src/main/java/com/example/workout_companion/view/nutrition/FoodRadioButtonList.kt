package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.entity.RecipeEntity

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.ui.graphics.Color

/**
 * Composable to display a list of radio buttons for lists of FoodType Entities, RecipeEntities
 * and APINinjaNutrition entities.
 * It checks if the provided lists are null or empty and if not, creates a list of radio buttons
 * The selected index is tracked using a MutableState<FoodIndex> which has a foodName, foodType and
 * index relative to the appropriate list of entities
 * @param navController, NavControlled
 * @param meal, name of meal
 * @param dbFoods, list of FoodTypeEntities,
 * @param dbRecipes, list of RecipeEntity,
 * @param apiFoods, SnapshotStateList of APINinjaNutrition objects
 * @param selectedFoodIndex, mutable state to keep track of the currently selected radio button
 */
@Composable
fun FoodRadioButtonList(navController: NavController,
                        meal: String,
                        dbFoods: SnapshotStateList<FoodTypeEntity>?,
                        dbRecipes: List<RecipeEntity>?,
                        apiFoods: SnapshotStateList<ApiNinjaNutrition>,
                        selectedFoodIndex: MutableState<FoodIndex>
) {
    //create radio buttons for foods from the database
    Column(modifier = Modifier.fillMaxWidth()){
        Text("Results")
        if(dbFoods != null){
            if(dbFoods.isEmpty()){
                Text("Food not found in database.")
            } else{
                Text("Foods from the database")
                dbFoods.forEachIndexed { index, it ->
                    RadioButton(
                        selected = (index == selectedFoodIndex.value.index) && (selectedFoodIndex.value.type == "DBFood"),
                        onClick = {
                            selectedFoodIndex.value.foodName = it.name
                            selectedFoodIndex.value.type = "DBFood"
                            selectedFoodIndex.value.index = index
                        },
                        enabled = true,
                        colors = RadioButtonDefaults.colors(selectedColor = Color.Magenta)
                    )
                FoodRadioButton(navController, meal, "Food", it)
                }
            }
        }else{
            Text("DBFoods list is null")
        }
        //create radio buttons for foods from API
        if(apiFoods.isEmpty()){
            Text("Food was not found in the api")
        }else{
            Text("Foods from the Nutrition API")
            apiFoods.elementAt(0).forEachIndexed { index, it ->
                Row {
                    RadioButton(
                        selected = (index == selectedFoodIndex.value.index) && (selectedFoodIndex.value.type == "API"),
                        onClick = {
                            selectedFoodIndex.value.foodName = it.name
                            selectedFoodIndex.value.type = "API"
                            selectedFoodIndex.value.index = index
                        },
                        enabled = true,
                        colors = RadioButtonDefaults.colors(selectedColor = Color.Magenta)
                    )
                    // Text(it.name)
                    FoodRadioButton(navController, meal, "Food", it)
                }
            }
        }
        //create radio buttons for recipes from the database
        if(dbRecipes != null){
            if(dbRecipes.isEmpty()){
                Text("No recipes with this name were found in the database")
            }else{
                dbRecipes.forEachIndexed { index, it ->
                    Row {
                        RadioButton(
                            selected = (index == selectedFoodIndex.value.index) && (selectedFoodIndex.value.type == "Recipe"),
                            onClick = {
                                selectedFoodIndex.value.foodName = it.name
                                selectedFoodIndex.value.type = "Recipe"
                                selectedFoodIndex.value.index = index
                            },
                            enabled = true,
                            colors = RadioButtonDefaults.colors(selectedColor = Color.Magenta)
                        )
                        // Text(it.name)
                        FoodRadioButton(navController, meal, "Recipe", it)
                    }
                }
            }
        } else{
            Text("Recipe list from database was null")
        }
    }

}

@Composable
fun FoodRadioButtonList(navController: NavController,
                        recipe: String,
                        dbFoods: List<FoodTypeEntity>?,
                        apiFoods: SnapshotStateList<ApiNinjaNutrition>,
                        selectedFoodIndex: MutableState<FoodIndex>
) {
    //create radio buttons for foods from the database
    Column(modifier = Modifier.fillMaxWidth()){
        Text("Results")
        if(dbFoods != null){
            if(dbFoods.isEmpty()){
                Text("Food not found in database.")
            } else{
                Text("Foods from the database")
                dbFoods.forEachIndexed { index, it ->
                    RadioButton(
                        selected = (index == selectedFoodIndex.value.index) && (selectedFoodIndex.value.type == "DBFood"),
                        onClick = {
                            selectedFoodIndex.value.foodName = it.name
                            selectedFoodIndex.value.type = "DBFood"
                            selectedFoodIndex.value.index = index
                        },
                        enabled = true,
                        colors = RadioButtonDefaults.colors(selectedColor = Color.Magenta)
                    )
                    RecipeRadioButton(navController, recipe, "Food", it)
                }
            }
        }else{
            Text("DBFoods list is null")
        }
        //create radio buttons for foods from API
        if(apiFoods.isEmpty()){
            Text("Food was not found in the api")
        }else{
            Text("Foods from the Nutrition API")
            apiFoods.elementAt(0).forEachIndexed { index, it ->
                Row {
                    RadioButton(
                        selected = (index == selectedFoodIndex.value.index) && (selectedFoodIndex.value.type == "API"),
                        onClick = {
                            selectedFoodIndex.value.foodName = it.name
                            selectedFoodIndex.value.type = "API"
                            selectedFoodIndex.value.index = index
                        },
                        enabled = true,
                        colors = RadioButtonDefaults.colors(selectedColor = Color.Magenta)
                    )
                    // Text(it.name)
                    RecipeRadioButton(navController, recipe, "Food", it)
                }
            }
        }
    }

}
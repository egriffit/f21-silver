package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutritionItem
import com.example.workout_companion.entity.FoodTypeEntity

/**
 * RecipeRadioButton composable to display food information to the left of the radio button
 * Consists of:
 * Button to navigate to FoodView
 * Text label for food nameFoodRadioButton,.kt
 * Text label for the type of food (recipe or food)
 * @param navController, navController,
 * @param recipe, name of meal
 * @param type, type of food, food or recipe,
 * @param food, APINinjaNutritionItem
 */
@Composable
fun RecipeRadioButton(navController: NavController, recipe: String, type: String, food: ApiNinjaNutritionItem){
    Row{
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.Center){
            Button(onClick = {
                navController.navigate("foodView/${food.name}/${food.serving_size_g}/${food.calories}/${food.carbohydrates_total_g}/${food.protein_g}/${food.fat_total_g}/r/${recipe}")
            })
            {
                Text(text = food.name)
            }
        }
        Column(modifier = Modifier.padding(start = 30.dp)){
//            Text("Serving Size: ${food.serving_size_g}g")
//            Text("Carbohydrate: ${food.carbohydrates_total_g}g")
//            Text("Protein: ${food.protein_g}g")
//            Text("Fat: ${food.fat_total_g}g")
            Text(type)
        }
    }
}

/**
* RecipeRadioButton composable to display food information to the left of the radio button
* Consists of:
* Button to navigate to FoodView
* Text label for food name
* Text label for the type of food (recipe or food)
* @param navController, navController,
* @param recipe, name of recipe
* @param type, type of food, food or recipe,
* @param food, FoodTypeEntity
*/
@Composable
fun RecipeRadioButton(navController: NavController, recipe: String, type: String, food: FoodTypeEntity){
    Row{
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.Center){
            Button(onClick = {
                navController.navigate("foodView/${food.name}/${food.serving_size}/${food.calories}/${food.carbohydrates}/${food.protein}/${food.fat}/r/${recipe}")
            })
            {
                Text(text = food.name)
            }
        }
        Column{
//            Text("Serving Size: ${food.serving_size_g}g")
//            Text("Carbohydrate: ${food.carbohydrates_total_g}g")
//            Text("Protein: ${food.protein_g}g")
//            Text("Fat: ${food.fat_total_g}g")
            Text(type)
        }
    }
}

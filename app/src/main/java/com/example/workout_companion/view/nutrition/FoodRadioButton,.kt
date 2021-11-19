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
import com.example.workout_companion.entity.RecipeEntity

/**
 * FoodRadioButton composable to display food information to the left of the radio button
 * Consists of:
 * Button to navigate to FoodView
 * Text label for food name
 * Text label for the type of food (recipe or food)
 * @param navController, navController,
 * @param meal, name of meal
 * @param type, type of food, food or recipe,
 * @param food, APINinjaNutritionItem
 */
@Composable
fun FoodRadioButton(navController: NavController, meal: String, type: String, food: ApiNinjaNutritionItem){
    Row{
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.Center){
            Button(onClick = {
                navController.navigate("foodView/${food.name}/${food.serving_size_g}/${food.calories}/${food.carbohydrates_total_g}/${food.protein_g}/${food.fat_total_g}/${meal}")
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
* FoodRadioButton composable to display food information to the left of the radio button
* Consists of:
* Button to navigate to FoodView
* Text label for food name
* Text label for the type of food (recipe or food)
* @param navController, navController,
* @param meal, name of meal
* @param type, type of food, food or recipe,
* @param food, FoodTypeEntity
*/
@Composable
fun FoodRadioButton(navController: NavController, meal: String, type: String, food: FoodTypeEntity){
    Row{
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.Center){
            Button(onClick = {
                navController.navigate("foodView/${food.name}/${food.serving_size}/${food.calories}/${food.carbohydrates}/${food.protein}/${food.fat}/${meal}")
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

/**
 * FoodRadioButton composable to display food information to the left of the radio button
 * Consists of:
 * Button to navigate to FoodView
 * Text label for food name
 * Text label for the type of food (recipe or food)
 * @param navController, navController,
 * @param meal, name of meal
 * @param type, type of food, food or recipe,
 * @param recipe, RecipeWithFoodsEntity
 */
@Composable
fun FoodRadioButton(navController: NavController, meal: String, type: String, recipe: RecipeEntity){
    Row{
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.Center){
            Button(onClick = {
                navController.navigate("recipeView/${recipe.name}/${meal}")
            })
            {
                Text(text = recipe.name)
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
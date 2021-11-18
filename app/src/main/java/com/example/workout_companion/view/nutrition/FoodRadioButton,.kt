package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutritionItem
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.entity.RecipeEntity
import com.example.workout_companion.entity.RecipeWithFoodsEntity

@Composable
fun FoodRadioButtons(navController: NavController, meal: String, found: ApiNinjaNutrition,
                     foundRecipes: List<RecipeEntity>, selectedFoodIndex: MutableState<Int>,
                     selectedRecipeIndex: MutableState<Int>
) {

    Row(
        Modifier
        .fillMaxWidth()) {

        foundRecipes.forEachIndexed { index, recipe ->
            RadioButton(
                selected = selectedFoodIndex.value ==  index,
                onClick = { selectedFoodIndex.value = index },
                enabled = true,
                colors = RadioButtonDefaults.colors(selectedColor = Color.Magenta)
            )
        }
        found.forEachIndexed  { index, food ->
            RadioButton(
                selected = selectedFoodIndex.value ==  index,
                onClick = { selectedFoodIndex.value = index },
                enabled = true,
                colors = RadioButtonDefaults.colors(selectedColor = Color.Magenta)
            )
            FoodRadioButton(navController, meal, "food", food)
        }

    }
}

@Composable
fun FoodRadioButton(navController: NavController, meal: String, type: String, food: ApiNinjaNutritionItem){
    Row(){
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.Center){
            Button(onClick = {
                navController.navigate("foodView/${food.name}/${food.serving_size_g}/${food.calories}/${food.carbohydrates_total_g}/${food.protein_g}/${food.fat_total_g}/${meal}")
            })
            {
                Text(text = food.name)
            }
        }
        Column(){
//            Text("Serving Size: ${food.serving_size_g}g")
//            Text("Carbohydrate: ${food.carbohydrates_total_g}g")
//            Text("Protein: ${food.protein_g}g")
//            Text("Fat: ${food.fat_total_g}g")
            Text(type)
        }
    }
}

@Composable
fun FoodRadioButton(navController: NavController, meal: String, type: String, food: FoodTypeEntity){
    Row(){
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.Center){
            Button(onClick = {
                navController.navigate("foodView/${food.name}/${food.serving_size}/${food.calories}/${food.carbohydrates}/${food.protein}/${food.fat}/${meal}")
            })
            {
                Text(text = food.name)
            }
        }
        Column(){
//            Text("Serving Size: ${food.serving_size_g}g")
//            Text("Carbohydrate: ${food.carbohydrates_total_g}g")
//            Text("Protein: ${food.protein_g}g")
//            Text("Fat: ${food.fat_total_g}g")
            Text(type)
        }
    }
}

@Composable
fun FoodRadioButton(navController: NavController, meal: String, type: String, recipe: RecipeWithFoodsEntity){
    Row(){
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.Center){
            Button(onClick = {
                navController.navigate("recipeView/${recipe.recipe.name}")
            })
            {
                Text(text = recipe.recipe.name)
            }
        }
        Column(){
//            Text("Serving Size: ${food.serving_size_g}g")
//            Text("Carbohydrate: ${food.carbohydrates_total_g}g")
//            Text("Protein: ${food.protein_g}g")
//            Text("Fat: ${food.fat_total_g}g")
            Text(type)
        }
    }
}
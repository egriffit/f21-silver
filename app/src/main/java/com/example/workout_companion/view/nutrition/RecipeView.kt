package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.material.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.viewmodel.FoodInRecipeViewModel
import com.example.workout_companion.viewmodel.RecipeViewModel


@Composable
fun RecipeView(navController: NavController, name: String?,
               recipeViewModel: RecipeViewModel,
               foodInRecipeViewModel: FoodInRecipeViewModel) {
    val recipeId = recipeViewModel.recipeID.observeAsState().value

    val recipeFoods = foodInRecipeViewModel.foodsInRecipe.observeAsState().value
    if(name != null){
        recipeViewModel.getRecipeID(name)
        foodInRecipeViewModel.getFoodInRecipe(name)
    }
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {
                    Row(modifier = Modifier.padding(start = 175.dp, bottom = 50.dp),
                        horizontalArrangement = Arrangement.Center)
                    {
                        Button(onClick = {
                            navController.navigate("nutritionOverview")
                        }){
                            Text("Done")
                        }
                    }
        },
        content = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("$name Recipe")
                }
                //show the recipe's id
                Text("Recipe ID: $recipeId")
                //Show a list of foods in the recipe
                if(recipeFoods != null && recipeFoods.isNotEmpty()){
                    recipeFoods.elementAt(0).foods.forEach{ f ->
                        LazyColumn{
                            item{
                                Text(f.name)
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (name != null) {
                        RecipeSearchBox(navController = navController, recipe = name)
                    }
                }
            }
        })
}

@Composable
fun RecipeView(navController: NavController, name: String?,
               meal: String?,
               recipeViewModel: RecipeViewModel,
               foodInRecipeViewModel: FoodInRecipeViewModel) {
    val recipeId = recipeViewModel.recipeID.observeAsState().value

    val recipeFoods = foodInRecipeViewModel.foodsInRecipe.observeAsState().value
    if(name != null){
        recipeViewModel.getRecipeID(name)
        foodInRecipeViewModel.getFoodInRecipe(name)
    }
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {
            Row(modifier = Modifier.padding(start = 175.dp, bottom = 50.dp),
                horizontalArrangement = Arrangement.Center)
            {
                Button(onClick = {
                    navController.navigate("nutritionOverview")
                }){
                    Text("Done")
                }
            }
        },
        content = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("$name Recipe")
                }
                //show the recipe's id
                Text("Recipe ID: $recipeId")
                //Show a list of foods in the recipe
                if(recipeFoods != null && recipeFoods.isNotEmpty()){
                    recipeFoods.elementAt(0).foods.forEach{ f ->
                        LazyColumn{
                            item{
                                Text(f.name)
                            }
                        }
                    }
                }
            }
        })
}
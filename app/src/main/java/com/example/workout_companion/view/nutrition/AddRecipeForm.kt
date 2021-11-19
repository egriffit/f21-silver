package com.example.workout_companion.view.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.viewmodel.FoodInRecipeViewModel
import com.example.workout_companion.viewmodel.FoodTypeViewModel
import com.example.workout_companion.viewmodel.NutritionAPIViewModel
import com.example.workout_companion.viewmodel.RecipeViewModel


@Composable
fun AddRecipeForm(navController: NavController, recipeViewModel: RecipeViewModel)
{
    //val foundMeals = mealViewModel.getAllMeals.observeAsState(listOf()).value
    val recipeName = remember{ mutableStateOf("") }

    //check if name of recipe is in database
    //if so display current foods
    //if not create recipe
    Column(modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center){
            Text("Add Recipe Form")
        }
//        if (foundMeals != null) {
//            MealList(navController, foundMeals, foodTypeViewModel, mealViewModel, foodInMealViewModel, nutritionAPIViewModel, )
//        }
        //display form to add meals
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.width(200.dp)
            )
            {
                Text(
                    "Create Recipe:",
                    fontSize = 15.sp
                )
                TextField(value = recipeName.value,
                    onValueChange = { recipeName.value = it })
            }
            Row(
                modifier = Modifier.width(200.dp)
            )
            {
                Button(onClick = {
                    //check if recipe exists
                    var found = recipeViewModel.getRecipe(recipeName.value)
                    if(found != null){
                        if(found.isNotEmpty()){
                            navController.navigate("recipe/${found.elementAt(0).name}")
                        }else{
                            //create a recipe
                            recipeViewModel.insert(recipeName.value)
                            navController.navigate("recipe/${recipeName.value}")
                        }
                    }else{
                        //create a recipe
                        recipeViewModel.insert(recipeName.value)
                        navController.navigate("recipe/${recipeName.value}")
                    }



                }) {
                    Text(
                        "Add Recipe",
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
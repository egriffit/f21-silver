package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workout_companion.viewmodel.RecipeViewModel


@Composable
fun AddRecipeForm(navController: NavController, recipeViewModel: RecipeViewModel)
{
    val recipeName = remember{ mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()
    ) {
            Row(
                modifier = Modifier.width(200.dp),
                horizontalArrangement = Arrangement.Center
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
                if(recipeName.value.isNotEmpty()) {
                    //check if recipe exists
                    val found = recipeViewModel.getRecipe(recipeName.value)
                    if (found != null) {
                        if (found.isNotEmpty()) {
                            navController.navigate("recipe/${found.elementAt(0).name}")
                        } else {
                            //create a recipe
                            recipeViewModel.insert(recipeName.value)
                            navController.navigate("recipe/${recipeName.value}")
                        }
                    } else {
                        //create a recipe
                        recipeViewModel.insert(recipeName.value)
                        navController.navigate("recipe/${recipeName.value}")
                    }
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
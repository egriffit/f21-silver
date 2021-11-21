package com.example.workout_companion.view.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ExpandMore
import androidx.compose.material.icons.sharp.NavigateNext
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workout_companion.viewmodel.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.message
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.vanpra.composematerialdialogs.title

/***
 * Composable form to add a meal
 * It consists of:
 * a MealList, a lazyColumn of Meals)
 * a button to add a meal
 * a button to remove meals
 *
 * @param navController, a NavController to navigate to different view
 * @param foodTypeViewModel, a  view model to work with food_type table
 * @param mealViewModel, a view model to work with the the meal table
 * @param foodInMealViewModel, a view model to work with the the food_in_meal table
 * @param nutritionAPIViewModel, a view model to work with the NutritionAPI by API Ninja
 * @param recipeViewModel, a view model to work with the recipe table
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddMealForm(navController: NavController, foodTypeViewModel: FoodTypeViewModel,
                mealViewModel: MealViewModel, foodInMealViewModel: FoodInMealViewModel,
                nutritionAPIViewModel: NutritionAPIViewModel, recipeViewModel: RecipeViewModel
){
    val foundMeals = mealViewModel.getAllMeals.observeAsState(listOf()).value
    val confirmRemove = rememberMaterialDialogState()
    val showRecipeForm = remember { mutableStateOf(false)}
    val showAddMealForm = remember { mutableStateOf(false)}

    val mealName = remember{ mutableStateOf("")}
    //dialog to confirm to remove all records
    MaterialDialog(dialogState = confirmRemove,
        buttons = {
            positiveButton("Ok", onClick = {
                //remove all meals in a day
                mealViewModel.deleteAll()
            })
            negativeButton("Cancel", onClick = {
                //do nothing
            })
        }){
        title(text = "Remove all meals?")
        message("Do you want to remove all of today's meals")
    }
    //display add meal form
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(300.dp)
    ){

        //display meals
        MealList(navController, foundMeals, foodTypeViewModel, mealViewModel, foodInMealViewModel, nutritionAPIViewModel )

        //show /hide form to add a meal
        Row{
            Button(onClick = {showAddMealForm.value = !showAddMealForm.value},
                modifier = Modifier.background(color = Color.LightGray),
                border= BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.LightGray)
            ){
                if(showAddMealForm.value){
                    Icon(
                        Icons.Sharp.ExpandMore,
                        contentDescription = "",
                        modifier = Modifier.background(color = Color.LightGray)
                            .size(20.dp),
                        tint = Color.Black
                    )
                }else{
                    Icon(
                        Icons.Sharp.NavigateNext,
                        contentDescription = "",
                        modifier = Modifier.background(color = Color.LightGray)
                            .size(20.dp),
                        tint = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.padding(start = 30.dp))
            Text("Create Meal")
        }
        if(showAddMealForm.value) {
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
                        "Create Meal:",
                        fontSize = 15.sp
                    )
                    TextField(value = mealName.value,
                        onValueChange = { mealName.value = it })
                }
                Row(
                    modifier = Modifier.width(200.dp)
                )
                {
                    Button(onClick = {
                        addFood(mealName, mealViewModel)
                        //                    mealViewModel.insert(mealName.value)
                        //                    mealName.value = ""
                    }) {
                        Text(
                            "Add meal",
                            fontSize = 15.sp
                        )
                    }
                }
            }
            Button(onClick = {
                //load confirm dialog box
                confirmRemove.show()
            }) {
                Text(
                    "Remove All Meals",
                    fontSize = 15.sp
                )
            }
        }
        //show hide recipe form
        Row{
            Button(onClick = {showRecipeForm.value = !showRecipeForm.value},
                modifier = Modifier.background(color = Color.LightGray),
                border= BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.LightGray)
            ){
                if(showRecipeForm.value){
                        Icon(
                            Icons.Sharp.ExpandMore,
                            contentDescription = "",
                            modifier = Modifier.background(color = Color.LightGray)
                                .size(20.dp),
                            tint = Color.Black
                        )
                    }else{
                        Icon(
                            Icons.Sharp.NavigateNext,
                            contentDescription = "",
                            modifier = Modifier.background(color = Color.LightGray)
                                .size(20.dp),
                            tint = Color.Black
                        )
                }
            }
            Spacer(modifier = Modifier.padding(start = 30.dp))
            Text("Create Recipe")
        }
        if(showRecipeForm.value){
            AddRecipeForm(navController, recipeViewModel)
        }
    }
}


/***
 * function used to create a meal from a string provided and store it in the meal table
 *
 * @param mealName, a MutableState<String>
 * @param mealViewModel, a  view model to work with meal table
 *
 */
@RequiresApi(Build.VERSION_CODES.O)
fun addFood(mealName: MutableState<String>, mealViewModel: MealViewModel ){
    if(mealName.value != ""){
        mealViewModel.insert(mealName.value)
        mealName.value = ""
    }
}
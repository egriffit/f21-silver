package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workout_companion.viewmodel.NutritionAPIViewModel
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutritionItem
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.FoodTypeViewModel
import com.example.workout_companion.viewmodel.MealViewModel
import com.vanpra.composematerialdialogs.*

/***
 * Composable with a textField and a button. The button can be pushed and will retrieve foods
 * from the NutritionAPI that match the string provided
 *
 * It consists of:
 * a label
 * a textField,
 * a button to request data from the NutritionAPI
 * a column of text labels with foods found from the API
 *
 *  @param navController, a NavController
 *  @param meal, name of meal
 *  @param foodTypeViewModel, a view model to work with the food_type table
 *  @param mealViewModel, a view model to work with the meal table
 * @param foodInMealViewModel, a view model to work with the the food_in_meal table
 * @param nutritionAPIViewModel, a view model to work with the NutritionAPI by API Ninja
 *
 */
@Composable
fun FoodSearchBox(navController: NavController, meal: String, foodTypeViewModel: FoodTypeViewModel,
                  mealViewModel: MealViewModel, foodInMealViewModel: FoodInMealViewModel,
                  nutritionAPIViewModel: NutritionAPIViewModel
){
    val food = remember{ mutableStateOf("") }
    val foundFoods =  nutritionAPIViewModel.foodResults
    val dialogState = rememberMaterialDialogState()
    val selectedFoodIndex = remember { mutableStateOf(0)}
    var selectedFoundFood: ApiNinjaNutritionItem
    val selectedFoodName = remember { mutableStateOf("")}

    //dialog box called when a food is searched for
    MaterialDialog(dialogState = dialogState,
       buttons = {
            positiveButton("Ok", onClick = {
                //get the selected index and find the food
                selectedFoundFood = foundFoods.elementAt(0).elementAt(selectedFoodIndex.value)
                selectedFoodName.value = selectedFoundFood.name
                //store the food in the food table
                val foodType = FoodTypeEntity(0, selectedFoundFood.name, "-1",
                    selectedFoundFood.serving_size_g,
                    selectedFoundFood.calories, selectedFoundFood.carbohydrates_total_g,
                    selectedFoundFood.protein_g, selectedFoundFood.fat_total_g)

                foodTypeViewModel.addFoodType(foodType)
                //add the food to the food in meals table
                //need a function to get the last insert id or to get the record in food_type Table

                //remove the found foods from my snapshotstate

                foundFoods.clear()

            })
            negativeButton("Cancel", onClick = {foundFoods.clear()})
        }
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 5.dp, top = 80.dp, bottom = 20.dp, end = 5.dp)
            ){
                FoodRadioButtonList(navController, meal, foundFoods, food, selectedFoodIndex)
           }
        }
    Column() {
        Row(        modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.width(200.dp)
            )
            {
                Text(
                    "Add Food:",
                    fontSize = 15.sp
                )
                TextField(value = food.value,
                    onValueChange = { food.value = it })
            }
            Row(
                modifier = Modifier.width(200.dp)
            )
            {
                Button(onClick = {
                    //should check to see if the food exists in the table

//                    //make an api call for the search term
//                    nutritionAPIViewModel.findFood(food.value)
//                    //store the foods
//                    foundFoods = nutritionAPIViewModel.foodResults
//                    //create the pick food form
//                    dialogState.show()
                    navController.navigate("searchFood/${food.value}/${meal}")

                }) {
                    Text(
                        "Search",
                        fontSize = 15.sp
                    )
                }
            }
        }
        Text(selectedFoodName.value)
    }
}


@Composable
fun FoodRadioButtonList(navController: NavController, meal: String, foundFoods: SnapshotStateList<ApiNinjaNutrition>,
                        food: MutableState<String>, selectedFoodIndex: MutableState<Int>){
    Text("Results")
    Column(){
        if(foundFoods.size > 0){
            for(found in foundFoods){
                FoodRadioButtons(navController, meal, found, selectedFoodIndex)
            }
        }
        else
        {
            Text("No results found for ${food.value}")
        }
    }
}


@Composable
fun FoodRadioButtons(navController: NavController, meal: String, found: ApiNinjaNutrition, selectedFoodIndex: MutableState<Int>) {

    Row(Modifier
        .fillMaxWidth()) {
        found.forEachIndexed  { index, food ->
            RadioButton(
                selected = selectedFoodIndex.value ==  index,
                onClick = { selectedFoodIndex.value = index },
                enabled = true,
                colors = RadioButtonDefaults.colors(selectedColor = Color.Magenta)
            )
            FoodRadioButton(navController, meal, food)
        }
    }
}

@Composable
fun FoodRadioButton(navController: NavController, meal: String, food: ApiNinjaNutritionItem){
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
            Text("Serving Size: ${food.serving_size_g}g")
            Text("Carbohydrate: ${food.carbohydrates_total_g}g")
            Text("Protein: ${food.protein_g}g")
            Text("Fat: ${food.fat_total_g}g")
        }
    }
}

package com.example.workout_companion.view.nutrition

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.workout_companion.viewmodel.NutritionAPIViewModel
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutritionItem
import com.example.workout_companion.entity.FoodInMealEntity
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
 * @param foundINMealViewModel, a view model to work with the the food_in_meal table
 * @param apiNinjaViewModel, a view model to work with the NutritionAPI by API Ninja
 *
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun foodSearchBox(meal: String,foodTypeViewModel: FoodTypeViewModel,
                  mealViewModel: MealViewModel,foodInMealViewModel: FoodInMealViewModel,
                  nutritionAPIViewModel: NutritionAPIViewModel){
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val food = remember{ mutableStateOf("") }
    var foundFoods =  nutritionAPIViewModel.foodResults
    val dialogState = rememberMaterialDialogState()
    val selectedFoodIndex = remember { mutableStateOf(0)}
    var selectedFoundFood: ApiNinjaNutritionItem = ApiNinjaNutritionItem(
        0.0, 0.0, 0,
        0.0,0.0,0.0,
        "",0, 0.0,
        0.0, 0, 0.0)
    val selectedFoodName = remember { mutableStateOf("")}

    //dialog box called when a food is searched for
    val textState = remember { mutableStateOf(TextFieldValue()) }
    MaterialDialog(dialogState = dialogState,
       buttons = {
            positiveButton("Ok", onClick = {
                //get the selected index and find the food
                selectedFoundFood = foundFoods.elementAt(0).elementAt(selectedFoodIndex.value)
                selectedFoodName.value = selectedFoundFood.name
                //store the food in the food table
                var foodType = FoodTypeEntity(0, selectedFoundFood.name, "-1",
                    selectedFoundFood.serving_size_g,
                    selectedFoundFood.calories, selectedFoundFood.carbohydrates_total_g,
                    selectedFoundFood.protein_g, selectedFoundFood.fat_total_g)

                foodTypeViewModel.addFoodType(foodType)
                //get id from food
                mealViewModel.getMealId(meal)
                foodTypeViewModel.getId(foodType)
                var foodId = foodTypeViewModel.foodID
                //get id from meal
                var mealId = mealViewModel.mealId

                //add the food to the food in meals table
                val foodInMealEntity = FoodInMealEntity(mealId, foodId, 1.0)

                if((mealId != 0 && mealId != null) && (foodId != 0 && foodId != null) ){
                    foodInMealViewModel.insert(foodInMealEntity)

                }
                //remove the found foods from my snapshotstate
                foundFoods.clear()

            })
            negativeButton("Cancel", onClick = {foundFoods.clear()})
        }
        ) {
            LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .height(400.dp)
                        .padding(start = 5.dp, top = 80.dp, bottom = 20.dp, end = 5.dp)
            ){
                item{
                    foodRadioButtonList(foundFoods, food, selectedFoodIndex)
                }
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

                    //make an api call for the search term
                    nutritionAPIViewModel.findFood(food.value)
                    //store the foods
                    foundFoods = nutritionAPIViewModel.foodResults
                    //create the pick food form
                    dialogState.show()
                }) {
                    Text(
                        "Search",
                        fontSize = 15.sp
                    )
                }
            }
        }
        Text(text = "${selectedFoodName.value}")
    }
}


@Composable
fun foodRadioButtonList(foundFoods: SnapshotStateList<ApiNinjaNutrition>,
                        food: MutableState<String>, selectedFoodIndex: MutableState<Int>){
    Text("Results")
    Column(){
        if(foundFoods.size > 0){
            for(found in foundFoods){
                FoodRadioButtons(found, selectedFoodIndex)
            }
        }
        else
        {
            Text("No results found for ${food.value}")
        }
    }
}


@Composable
fun FoodRadioButtons(found: ApiNinjaNutrition, selectedFoodIndex: MutableState<Int>) {

    Row(Modifier
        .fillMaxWidth()) {
        found.forEachIndexed  { index, food ->
            RadioButton(
                selected = selectedFoodIndex.value ==  index,
                onClick = { selectedFoodIndex.value = index },
                enabled = true,
                colors = RadioButtonDefaults.colors(selectedColor = Color.Magenta)
            )
            foodRadioButton(food)
        }
    }
}

@Composable
fun foodRadioButton(food: ApiNinjaNutritionItem){
    Row(){
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.Center){
            Text("${food.name}")
        }
        Column(){
            Text("Serving Size: ${food.serving_size_g}g")
            Text("Carbohydrate: ${food.carbohydrates_total_g}g")
            Text("Protein: ${food.protein_g}g")
            Text("Fat: ${food.fat_total_g}g")
        }
    }
}

package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.sampleData.sampleFoodTypeList
import com.example.workout_companion.viewmodel.FoodInMealViewModel

/***
 * Composable to show a lazy column of FoodListItems.
 * Consists of:
 * FoodListItem which consists of the food name, serving size, macronutrient and calorie composition,
 * and a text field to specify the number of servings,
 *
 * @param foods, a list of FoodTypeEntity
 * @param servings, a list of MutableStates to track te serving quantity
 */
@Composable
fun FoodList(foods: List<FoodTypeEntity>, servings: List<MutableState<Double>>){
  LazyColumn(){
      for((index, food) in foods.withIndex())
      {
          item{
              FoodListItem(food, servings[index])
          }
      }
    }

}

/***
 * Composable to show display the servings, calories and macronutrient information for a food
 * Consists of:
 * food name,
 * serving size,
 * macronutrient and calorie composition,
 * a text field to specify the number of servings,
 *
 * @param foods, a FoodTypeEntity
 * @param servings, a  MutableStates to track te serving quantity
 */
@Composable
fun FoodListItem(food: FoodTypeEntity, serving: MutableState<Double>) {
    Row() {
        Column(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("${food.name}")
        }
        Column() {
            Text("Serving Size: ${food.serving_size}g")
            Text("Carbohydrate: ${food.carbohydrates}g")
            Text("Protein: ${food.protein}g")
            Text("Fat: ${food.fat}g")
        }
        Column(){
            OutlinedTextField(
                value = serving.value.toString(),
                onValueChange = { serving.value = it.toDouble() },
                label = { Text(text = "Servings") },
                readOnly = false,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}

@Preview
@Composable
fun previewFoodItem() {
    var servings: MutableList<MutableState<Double>> = arrayListOf()
    var i = 0
    while(i < sampleFoodTypeList.size){
        servings.add( remember{ mutableStateOf(0.0) })
        i++
    }
    FoodListItem(sampleFoodTypeList[0], servings[0])
}

@Preview
@Composable
fun previewFoodList() {
    var servings: MutableList<MutableState<Double>> = arrayListOf()
    var i = 0
    while(i < sampleFoodTypeList.size){
        servings.add( remember{ mutableStateOf(0.0) })
        i++
    }
    FoodList(sampleFoodTypeList, servings)
}

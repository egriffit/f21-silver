package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.entity.FoodTypeEntity

/***
 * Composable to show a list of foods with their name and calories
 *
 * @param food, a FoodTypeEntity
 */
@Composable
fun FoodRow(navController: NavController, food: FoodTypeEntity){
    Row(modifier = Modifier.fillMaxWidth()
        .padding(start=20.dp, end = 20.dp)
    ){

        Button(onClick = {
            navController.navigate("foodView/${food.name}/${food.serving_size}/${food.calories}/${food.carbohydrates}/${food.protein}/${food.fat}")
        })
        {
            Text(text = food.name)
        }
        Spacer(modifier = Modifier.padding(start = 50.dp))
        Text(text = "${food.calories} cal")
    }
}

//@Preview
//@Composable
//fun previewFoodRow(){
//    FoodRow(sampleFoodTypeList[0])
//}
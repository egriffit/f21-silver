package com.example.workout_companion.view.nutrition

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.sampleData.sampleFoodTypeList

/***
 * Composable to show a list of foods with their name and calories
 *
 * @param food, a FoodTypeEntity
 */
@Composable
fun FoodRow(food: FoodTypeEntity){
    Row(modifier = Modifier.fillMaxWidth()
        .padding(start=20.dp, end = 20.dp)
    ){
        Text(text = food.name)
        Spacer(modifier = Modifier.padding(start = 50.dp))
        Text(text = "${food.calories} cal")
    }
}

@Preview
@Composable
fun previewFoodRow(){
    FoodRow(sampleFoodTypeList[0])
}
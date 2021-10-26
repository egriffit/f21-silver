package com.example.workout_companion.view.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workout_companion.entity.MealEntity
import com.example.workout_companion.viewmodel.FoodInMealViewModel
import com.example.workout_companion.viewmodel.MealViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddMealForm(navController: NavController, mealViewModel: MealViewModel, fodInMealViewModel: FoodInMealViewModel){
    val foundMeals = mealViewModel.getAllMeals.observeAsState(listOf()).value
    val mealName = remember{ mutableStateOf("")}
//    val submitFunction = addMeal(mealName, mealViewModel)
    Column(
        modifier = Modifier.fillMaxSize(),

    ){
        MealList(foundMeals, fodInMealViewModel)
        Row(        modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Row(
                modifier = Modifier.width(200.dp)
            )
            {
                Text("Create Meal:",
                    fontSize = 15.sp)
                TextField(value = mealName.value,
                    onValueChange = {mealName.value = it})
            }
            Row(
                modifier = Modifier.width(200.dp)
            )
            {
                Button(onClick = {
                    addFood(mealName, mealViewModel)
//                    mealViewModel.insert(mealName.value)
//                    mealName.value = ""
                }){
                    Text("Add meal",
                        fontSize = 15.sp)
                }
            }
        }
        Button(onClick = {
            mealViewModel.deleteAll()
        }){
            Text("Remove All Meals",
                fontSize = 15.sp)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MealList(meals: List<MealEntity>, fodInMealViewModel: FoodInMealViewModel) {
    Row(modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center){
        Text("Today's Meals")
    }

    LazyColumn(
        Modifier
            .background(Color(0xFFEDEAE0))
            .fillMaxWidth()
            .fillMaxHeight(0.4F)
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (meals != null) {
            for (meal in meals)
            // Name
                item {
                    Row {
                        mealButton(meal.type, fodInMealViewModel)
                    }
                }
        }else{
            item{
                Row{
                    Text("No Meals found")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun mealButton(meal: String, fodInMealViewModel: FoodInMealViewModel) {
    val open = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth()
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth()){
            Button(onClick = {open.value = !open.value}) {
                if(open.value){
                    Text("-",
                        fontSize = 15.sp)
                }else{
                    Text("+",
                        fontSize = 15.sp)
                }

            }
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Text(meal)
        }
        FoodList(fodInMealViewModel, meal, open)
    }


}

@RequiresApi(Build.VERSION_CODES.O)
fun addFood(mealName: MutableState<String>, mealViewModel: MealViewModel){
    mealViewModel.insert(mealName.value)
    mealName.value = ""
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FoodList(foodInMealViewModel: FoodInMealViewModel, meal: String, open: MutableState<Boolean>)
{
    val today = LocalDate.now()
    var foundFoods =  foodInMealViewModel.getFoodsInMeal(meal, today).observeAsState(listOf()).value
    if(open.value){
        Column(){
            Text("Foods")
            //display current foods in meal
            for (food in foundFoods)
                // Name
                        Row {
                            Text("${food.name} - Serving Size: ${food.serving_size} Carbohydrates: ${food.carbohydrates}g  Protein: ${food.protein}g Fat: ${food.fat}")
                        }
                }
                //search box to add food
                foodSearchBox()
            }
        }

@Composable
fun foodSearchBox(){
    val food = remember{ mutableStateOf("")}
    Row(        modifier = Modifier.fillMaxWidth(),
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
            TextField(value = food.value,
                onValueChange = { food.value = it })
        }
        Row(
            modifier = Modifier.width(200.dp)
        )
        {
            Button(onClick = {

//                    mealViewModel.insert(mealName.value)
//                    mealName.value = ""
            }) {
                Text(
                    "Search",
                    fontSize = 15.sp
                )
            }
        }
    }
}
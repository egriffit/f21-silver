package com.example.workout_companion.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.viewmodel.NutritionAPIViewModel
import com.example.workout_companion.view.inputfields.TopNavigation
import com.example.workout_companion.view.nutrition.AddMealForm
import com.example.workout_companion.view.nutrition.NutritionStatus
import com.example.workout_companion.viewmodel.*

@RequiresApi(Build.VERSION_CODES.O)
/***
 * Composable form to display current nutrition and a form to create and update meals
 *
 * @param navController, a NavController to navigate to different view
 * @param foodTypeViewModel, a  view model to work with food_type table
 * @param mealViewModel, a view model to work with the the meal table
 * @param foodInMealViewModel, a view model to work with the the food_in_meal table
 * @param nutritionAPIViewModel, a view model to work with the NutritionAPI by API Ninja
 * @param recipeViewModel, a view to work with recipe table
 * @param currentUserGoalViewModel, a view to work with the current_user_goals table
 * @param nutritionStatusViewModel ,a view model to work with the nutrition_status table
 */
@Composable
fun NutritionOverview(navController: NavController,
                      foodTypeViewModel: FoodTypeViewModel, mealViewModel: MealViewModel,
                      foodInMealViewModel: FoodInMealViewModel,
                      nutritionAPIViewModel: NutritionAPIViewModel,
                      recipeViewModel: RecipeViewModel,
                      currentUserGoalViewModel: CurrentUserGoalViewModel,
                      nutritionStatusViewModel: NutritionStatusViewModel){
    Scaffold(
        topBar = { TopNavigation(navController) },
        bottomBar = {},
        content = {
            Column(modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            )
            {
                Row(modifier=Modifier.padding(bottom=10.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center){
                    Text("Nutrition Overview Page")
                }
                NutritionStatus(currentUserGoalViewModel, mealViewModel, nutritionStatusViewModel)
                AddMealForm(navController, foodTypeViewModel, mealViewModel,
                    foodInMealViewModel, nutritionAPIViewModel, recipeViewModel)
            }
        }
    )
}
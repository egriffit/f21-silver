package com.example.workout_companion.view

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.example.workout_companion.view.inputfields.LandingPage
import com.example.workout_companion.view.nutrition.AddRecipeForm
import com.example.workout_companion.view.nutrition.FoodView
import com.example.workout_companion.view.nutrition.FoundFoods
import com.example.workout_companion.view.nutrition.RecipeView
import com.example.workout_companion.viewmodel.*


@SuppressLint("NewApi")
@Composable
fun MainNavigation(viewModelProvider: ViewModelProvider) {
    // Put the view models you need here
    val goalTypeViewModel by lazy { viewModelProvider.get(GoalTypeViewModel::class.java) }
    val frameworkTypeViewModel by lazy { viewModelProvider.get(FrameworkTypeViewModel::class.java) }
    val frameworkDayViewModel by lazy { viewModelProvider.get(FrameworkDayViewModel::class.java) }
    val frameworkComponentViewModel by lazy { viewModelProvider.get(FrameworkComponentViewModel::class.java) }
    val userViewModel by lazy { viewModelProvider.get(UserViewModel::class.java) }
    val userWithGoalViewModel by lazy { viewModelProvider.get(UserWithGoalViewModel::class.java) }
    val mealViewModel by lazy { viewModelProvider.get(MealViewModel::class.java) }
    val foodInMealViewModel by lazy { viewModelProvider.get(FoodInMealViewModel::class.java) }
    val foodTypeViewModel by lazy { viewModelProvider.get(FoodTypeViewModel::class.java) }
    val nutritionAPIViewModel: NutritionAPIViewModel =  viewModel()
    val recipeViewModel by lazy { viewModelProvider.get(RecipeViewModel::class.java) }
    val foodInRecipeViewModel by lazy { viewModelProvider.get(FoodInRecipeViewModel::class.java) }

    val navController = rememberNavController()
    NavHost(navController, startDestination = "splashScreen") {
        composable (route = "splashScreen") {
            SplashScreen(navController)
        }
        composable (route = "userForm" ) {
            UserForm(navController, userViewModel, userWithGoalViewModel)
        }
        composable (route = "mainView") {
            LandingPage(navController)
        }
        composable (route = "ExerciseOverview") {
            ExerciseOverview(navController)
        }
        composable (route = "NutritionOverview") {
            NutritionOverview(navController, foodTypeViewModel, mealViewModel,
                foodInMealViewModel, nutritionAPIViewModel, recipeViewModel)
        }
        composable (route = "searchFood/{foodName}/{meal}",
            arguments = listOf(
                navArgument("foodName") { type = NavType.StringType } ,
                navArgument("meal") { type = NavType.StringType }
            )
        ){ backStackEntry ->
            FoundFoods(navController, backStackEntry.arguments?.getString("foodName"),
                 backStackEntry.arguments?.getString("meal"),
                foodTypeViewModel, mealViewModel, foodInMealViewModel,
                recipeViewModel, foodInRecipeViewModel, nutritionAPIViewModel
                    )
        }
        composable (route = "foodView/{foodName}/{servingSize}/{calories}/{carbohydrates}/{protein}/{fat}/{meal}",
            arguments = listOf(
                navArgument("foodName") { type = NavType.StringType } ,
                navArgument("servingSize") { type = NavType.StringType },
                navArgument("calories") { type = NavType.StringType },
                navArgument("carbohydrates") { type = NavType.StringType },
                navArgument("protein") { type = NavType.StringType },
                navArgument("fat") { type = NavType.StringType },
                navArgument("meal") { type = NavType.StringType },
                )
        ){ backStackEntry ->
            FoodView(navController, backStackEntry.arguments?.getString("foodName"),
                backStackEntry.arguments?.getString("servingSize")?.toDouble(),
                backStackEntry.arguments?.getString("calories")?.toDouble(),
                backStackEntry.arguments?.getString("carbohydrates")?.toDouble(),
                backStackEntry.arguments?.getString("protein")?.toDouble(),
                backStackEntry.arguments?.getString("fat")?.toDouble(),
                backStackEntry.arguments?.getString("meal"),
                foodTypeViewModel, mealViewModel, foodInMealViewModel
            )
        }
        composable(route = "addRecipeForm"){
            AddRecipeForm(navController, recipeViewModel)
        }
        composable(route = "recipe/{name}",
                arguments = listOf(
                    navArgument("name") { type = NavType.StringType }
                )
            ){ backStackEntry ->
            RecipeView(navController,
                backStackEntry.arguments?.getString("name")
            )
        }
        composable (route = "UpdateGoals") {
            UpdateGoalsView(navController)
        }
        composable (route = "Assessment") {
            AssessmentView(navController)
        }
        // Other routes go here
    }
}
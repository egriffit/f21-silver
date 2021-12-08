package com.example.workout_companion.view

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.example.workout_companion.entity.FoodTypeEntity
import com.example.workout_companion.view.exercise.ExerciseView
import com.example.workout_companion.view.exercise.FoundExerises
import com.example.workout_companion.view.nutrition.*
import com.example.workout_companion.viewmodel.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.*


@SuppressLint("NewApi")
@Composable
fun MainNavigation(viewModelProvider: ViewModelProvider) {
    val coroutineScope = rememberCoroutineScope()

    // Put the view models you need here
    val goalTypeViewModel by lazy { viewModelProvider.get(GoalTypeViewModel::class.java) }
    val nutritionPlanTypeViewModel by lazy {viewModelProvider.get(NutritionPlanTypeViewModel::class.java) }
    val frameworkTypeViewModel by lazy { viewModelProvider.get(FrameworkTypeViewModel::class.java) }
    val userViewModel by lazy { viewModelProvider.get(UserViewModel::class.java) }
    val userWithGoalViewModel by lazy { viewModelProvider.get(UserWithGoalViewModel::class.java) }
    val mealViewModel by lazy { viewModelProvider.get(MealViewModel::class.java) }
    val foodInMealViewModel by lazy { viewModelProvider.get(FoodInMealViewModel::class.java) }
    val foodTypeViewModel by lazy { viewModelProvider.get(FoodTypeViewModel::class.java) }
    val nutritionAPIViewModel: NutritionAPIViewModel =  viewModel()
    val recipeViewModel by lazy { viewModelProvider.get(RecipeViewModel::class.java) }
    val foodInRecipeViewModel by lazy { viewModelProvider.get(FoodInRecipeViewModel::class.java) }
    val currentUserGoalViewModel by lazy { viewModelProvider.get(CurrentUserGoalViewModel::class.java) }
    val workoutViewModel by lazy { viewModelProvider.get(WorkoutViewModel::class.java) }
    val adviceAPIViewModel: AdviceAPIViewModel =  viewModel()
    val wgerApiViewModel by lazy {viewModelProvider.get(WgerAPIViewModel::class.java)}
    val completeFrameworkViewModel by lazy { viewModelProvider.get(CompleteFrameworkViewModel::class.java) }
    val nutritionStatusViewModel by lazy { viewModelProvider.get(NutritionStatusViewModel::class.java) }

    LaunchedEffect(coroutineScope) {
        val job = goalTypeViewModel.loadGoals()
        job.join() // Wait for the goals to be made before making the frameworks
        frameworkTypeViewModel.loadFrameworks()
    }

    val workoutState = workoutViewModel.getTodaysWorkoutWithComponents().observeAsState()
    val userGoalState = currentUserGoalViewModel.getCurrentGoals.observeAsState()

    val navController = rememberNavController()
    NavHost(navController, startDestination = "splashScreen") {
        composable (route = "splashScreen") {
            SplashScreen(navController, currentUserGoalViewModel)
        }
        composable (route = "userForm" ) {
            UserForm(navController, userViewModel, userWithGoalViewModel)
        }
        composable (route = "mainView") {
            LandingPage(navController)
        }
        composable (route = "ExerciseOverview") {
            // Don't let the user get access to our overview unless they have a goal set
            if (userGoalState.value != null) {
                val frameworkId = userGoalState.value!!.FrameWorkWithGoalEntity.id
                val currentFramework = completeFrameworkViewModel.getFrameworkWithDaysById(frameworkId).observeAsState()
                ExerciseOverview(navController, workoutState, currentFramework, workoutViewModel)
            }
        }
        composable (route = "NutritionOverview") {
            NutritionOverview(navController, foodTypeViewModel, mealViewModel,
                foodInMealViewModel, nutritionAPIViewModel, recipeViewModel,
                currentUserGoalViewModel, nutritionStatusViewModel)
        }
        composable (route = "searchFood/{foodName}/{meal}",
            arguments = listOf(
                navArgument("foodName") { type = NavType.StringType } ,
                navArgument("meal") { type = NavType.StringType }
            )
        ){ backStackEntry ->
            val dbRecipes = recipeViewModel.foundRecipes.observeAsState(listOf()).value
            val dbFoods = foodTypeViewModel.foodResults.observeAsState(listOf()).value
            val food = backStackEntry.arguments?.getString("foodName")
            val meal = backStackEntry.arguments?.getString("meal")
            //get foods
            if (food != null) {
                runBlocking{
                    val job1: Job = launch(Dispatchers.IO){
                        foodTypeViewModel.getFood(food)
                    }
                    job1.join()
                    val job2: Job = launch(Dispatchers.IO){
                        recipeViewModel.getRecipe(food)
                    }
                    job2.join()
                }

            }
            FoundFoods(navController, food,
                 meal,
                dbFoods, dbRecipes,
                foodTypeViewModel, mealViewModel, foodInMealViewModel,
                recipeViewModel, foodInRecipeViewModel, nutritionAPIViewModel
                    )
        }

        composable (route = "searchRecipeFood/{foodName}/{recipe}",
            arguments = listOf(
                navArgument("foodName") { type = NavType.StringType } ,
                navArgument("recipe") { type = NavType.StringType }
            )
        ){ backStackEntry ->
            FoundRecipeFoods(navController,
                backStackEntry.arguments?.getString("foodName"),
                backStackEntry.arguments?.getString("recipe"),
                foodTypeViewModel,
                recipeViewModel,
                foodInRecipeViewModel,
                nutritionAPIViewModel
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
            val food: String = backStackEntry.arguments?.getString("foodName")!!
            val servingSize: Double = backStackEntry.arguments?.getString("servingSize")?.toDouble()!!
            val calories: Double = backStackEntry.arguments?.getString("calories")?.toDouble()!!
            val carbohydrates: Double = backStackEntry.arguments?.getString("carbohydrates")?.toDouble()!!
            val protein: Double = backStackEntry.arguments?.getString("protein")?.toDouble()!!
            val fat: Double = backStackEntry.arguments?.getString("fat")?.toDouble()!!
            val foodType = FoodTypeEntity(
                    0, food, "-1",
                    servingSize, calories, carbohydrates,
                    protein, fat
                )
            runBlocking{
                val jobF1: Job = launch(context = Dispatchers.IO){
                    foodTypeViewModel.addFoodType(foodType)
                }
                jobF1.join()
            }


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
        composable (route = "foodView/{foodName}/{servingSize}/{calories}/{carbohydrates}/{protein}/{fat}/r/{recipe}",
            arguments = listOf(
                navArgument("foodName") { type = NavType.StringType } ,
                navArgument("servingSize") { type = NavType.StringType },
                navArgument("calories") { type = NavType.StringType },
                navArgument("carbohydrates") { type = NavType.StringType },
                navArgument("protein") { type = NavType.StringType },
                navArgument("fat") { type = NavType.StringType },
                navArgument("recipe") { type = NavType.StringType },
            )
        ){ backStackEntry ->
            val food: String = backStackEntry.arguments?.getString("foodName")!!
            val servingSize: Double = backStackEntry.arguments?.getString("servingSize")?.toDouble()!!
            val calories: Double = backStackEntry.arguments?.getString("calories")?.toDouble()!!
            val carbohydrates: Double = backStackEntry.arguments?.getString("carbohydrates")?.toDouble()!!
            val protein: Double = backStackEntry.arguments?.getString("protein")?.toDouble()!!
            val fat: Double = backStackEntry.arguments?.getString("fat")?.toDouble()!!
            val foodType = FoodTypeEntity(
                0, food, "-1",
                servingSize, calories, carbohydrates,
                protein, fat
            )
            runBlocking{
                val jobF1: Job = launch(context = Dispatchers.IO){
                    foodTypeViewModel.addFoodType(foodType)
                }
                jobF1.join()
            }
            FoodView(navController, backStackEntry.arguments?.getString("foodName"),
                backStackEntry.arguments?.getString("servingSize")?.toDouble(),
                backStackEntry.arguments?.getString("calories")?.toDouble(),
                backStackEntry.arguments?.getString("carbohydrates")?.toDouble(),
                backStackEntry.arguments?.getString("protein")?.toDouble(),
                backStackEntry.arguments?.getString("fat")?.toDouble(),
                backStackEntry.arguments?.getString("recipe"),
                foodTypeViewModel, recipeViewModel, foodInRecipeViewModel
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
                backStackEntry.arguments?.getString("name"),
                recipeViewModel,
                foodInRecipeViewModel
            )
        }
        composable(route = "addRecipeFoods/{name}/{meal}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("meal") { type = NavType.StringType }

            )
        ){ backStackEntry ->
            RecipeView(navController,
                backStackEntry.arguments?.getString("name"),
                backStackEntry.arguments?.getString("meal"),
                recipeViewModel,
                foodInRecipeViewModel
            )
        }
        composable (route = "UpdateGoals") {
            UpdateGoalsView(navController,
                frameworkTypeViewModel,
                goalTypeViewModel,
                currentUserGoalViewModel,
                userViewModel,
                nutritionPlanTypeViewModel
            )
        }
        composable (route = "Assessment") {
            AssessmentView(navController, currentUserGoalViewModel, adviceAPIViewModel, nutritionStatusViewModel, workoutViewModel)
        }
        composable (route = "Landing") {
            LandingPage(navController)
        }
        composable (route = "searchExercise/{muscle}",
                arguments = listOf(
                    navArgument("muscle") { type = NavType.StringType },
                )
        ){ backStackEntry ->
            val m  = backStackEntry.arguments?.getString("muscle")!!
            FoundExerises(navController,
                m,
            wgerApiViewModel)
        }

        composable (route = "ExerciseView/{muscle}/{exerciseId}",
            arguments = listOf(
                navArgument("exerciseId") { type = NavType.StringType },
                navArgument("muscle") { type = NavType.StringType },

                )
        ){ backStackEntry ->
            val exerciseId  = backStackEntry.arguments?.getString("exerciseId")!!.toInt()
            val muscleName  = backStackEntry.arguments?.getString("muscle")!!
            var exerciseInfo = wgerApiViewModel.exerciseInfo
            runBlocking{
                val exerciseJob: Job = launch(Dispatchers.IO){
                    wgerApiViewModel.getExericseInfo(exerciseId)
                }
                exerciseJob.join()
                exerciseInfo = wgerApiViewModel.exerciseInfo
            }
            if(exerciseInfo.value != null) {
                ExerciseView(navController, exerciseInfo.value, muscleName, exerciseId)
            }
        }
        // Other routes go here
    }
}
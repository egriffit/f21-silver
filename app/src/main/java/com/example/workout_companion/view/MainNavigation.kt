package com.example.workout_companion.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.ViewModelProvider
import com.example.workout_companion.database.FRAMEWORK_COMPONENTS
import com.example.workout_companion.database.FRAMEWORK_DAYS
import com.example.workout_companion.database.FRAMEWORK_TYPES
import com.example.workout_companion.database.GOALS
import com.example.workout_companion.view.inputfields.LandingPage
import com.example.workout_companion.viewmodel.*


@Composable
fun MainNavigation(viewModelProvider: ViewModelProvider) {
    // Put the view models you need here
    val goalTypeViewModel by lazy { viewModelProvider.get(GoalTypeViewModel::class.java) }
    val frameworkTypeViewModel by lazy { viewModelProvider.get(FrameworkTypeViewModel::class.java) }
    val frameworkDayViewModel by lazy { viewModelProvider.get(FrameworkDayViewModel::class.java) }
    val frameworkComponentViewModel by lazy { viewModelProvider.get(FrameworkComponentViewModel::class.java) }
    val userViewModel by lazy { viewModelProvider.get(UserViewModel::class.java) }
    val userWithGoalViewModel by lazy { viewModelProvider.get(UserWithGoalViewModel::class.java) }


    createDefaultGoals(goalTypeViewModel)
    createDefaultFrameworks(frameworkTypeViewModel, frameworkDayViewModel, frameworkComponentViewModel)


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
            NutritionOverview(navController)
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

/**
 * Create any default values if necessary
 *
 * @property goalViewModel The view model to add goals with.
 */
fun createDefaultGoals(goalViewModel: GoalTypeViewModel) {
    for (goal in GOALS.values) {
        // We are not guarding this statement because our database policy
        // is to ignore any conflicting adds.
        // Probably not the best solution, but it works for now
        goalViewModel.addGoal(goal)
    }
}

/**
 * Create any default frameworks if necessary
 *
 * @property frameworkTypeViewModel The view model to add framework types.
 * @property frameworkDayViewModel The view model to add framework days.
 * @property frameworkComponentViewModel The view model to add framework components.
 */
fun createDefaultFrameworks(frameworkTypeViewModel: FrameworkTypeViewModel, frameworkDayViewModel: FrameworkDayViewModel,
    frameworkComponentViewModel: FrameworkComponentViewModel) {
    for (framework in FRAMEWORK_TYPES) {
        frameworkTypeViewModel.addFramework(framework)
    }
    for (day in FRAMEWORK_DAYS) {
        frameworkDayViewModel.addFrameworkDay(day)
    }
    for (component in FRAMEWORK_COMPONENTS) {
        frameworkComponentViewModel.addFrameworkComponent(component)
    }
}
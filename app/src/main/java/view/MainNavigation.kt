package view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun appNavController() {
    val navController = rememberNavController()
    NavHost (navController, startDestination = "workouts") {
        composable (route = "workouts") {
            WorkoutsView(navController)
        }
        composable (route = "<workoutProgress>") {
            secondView(navController)
        }
        composable (route = "<meals>") {
            thirdView(navController)
        }
        composable (route = "<dietProgress>") {
            fourthView(navController)
        }
        composable (route = "<profile>") {
            fifthView(navController)
        }
    }
}

@Composable
fun WorkoutsView(navController: NavController) {
    Column() {
        Text (
            text = "Workouts View"
        )
    }
}
package com.example.workout_companion.view

import android.app.Application
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.workout_companion.R
import com.example.workout_companion.viewmodel.CurrentUserGoalViewModel
import com.example.workout_companion.viewmodel.GoalTypeViewModel
import kotlinx.coroutines.*


@Composable
    fun SplashScreen(navController: NavController, currentUserGoalViewModel: CurrentUserGoalViewModel) {
        val context = LocalContext.current

//        var currentGoals: CurrentNutritionPlanAndFrameworkEntity? = currentUserGoalViewModel.getCurrentGoals.value
//        var currentGoals: CurrentNutritionPlanAndFrameworkEntity =
//            CurrentNutritionPlanAndFrameworkEntity(nutritionPlanType = NutritionPlanTypeEntity(1, 1, 2500.0, .40, .35, .25),
//                                                   currentUserGoalEntity = CurrentUserGoalEntity(1, 1),
//                                                   FrameWorkWIthGoalEntity = FrameworkWithGoalEntity(1, "Framework_1", 1, 1, "Gain Strength")
//            )



//        val goalsExist: Boolean? = currentUserGoalViewModel.currentGoalExists.observeAsState().value
        //var testGoalsExist: Boolean = true

        val scale = remember {
            androidx.compose.animation.core.Animatable(.1f)
        }

        // AnimationEffect
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 2f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = {
                        OvershootInterpolator(4f).getInterpolation(it)
                    })
            )
            val goalsExist: Boolean? =
                withContext(Dispatchers.IO) {
                    currentUserGoalViewModel.checkIfExists()
                    delay(3000L)
                    currentUserGoalViewModel.currentGoalExists.value
                }
            if (goalsExist == true) {
                navController.navigate("mainView")
            } else {
                navController.navigate("userForm")
            }
        }

        // Image
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.scale(scale.value))
        }
    }
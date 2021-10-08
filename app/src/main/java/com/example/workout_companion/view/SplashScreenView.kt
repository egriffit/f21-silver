package com.example.workout_companion.view

import android.app.Application
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.workout_companion.R
import com.example.workout_companion.entity.CurrentNutritionPlanAndFrameworkEntity
import com.example.workout_companion.viewmodel.CurrentUserGoalViewModel
import com.example.workout_companion.viewmodel.CurrentUserGoalViewModelFactory
import kotlinx.coroutines.delay


    @Composable
    fun SplashScreen(navController: NavController) {
        val context = LocalContext.current

        val currentUserGoalViewModel: CurrentUserGoalViewModel = viewModel(
            factory = CurrentUserGoalViewModelFactory(context.applicationContext as Application)
        )

        var currentGoals: CurrentNutritionPlanAndFrameworkEntity? = currentUserGoalViewModel.getCurrentGoals.value
//        var currentGoals: CurrentNutritionPlanAndFrameworkEntity =
//            CurrentNutritionPlanAndFrameworkEntity(nutritionplanType = NutritionPlanTypeEntity(1, 1, 2500.0, .40, .35, .25),
//                                                   currentUserGoalEntity = CurrentUserGoalEntity(1, 1),
//                                                   FrameWorkWIthGoalEntity = FrameworkWithGoalEntity(1, "Framework_1", 1, 1, "Gain Strength")
//            )
        var goalsExist = false

        if((currentGoals?.currentUserGoalEntity?.framework_type_id != null)|| (currentGoals?.currentUserGoalEntity?.nutrition_plan_type_id != null))
        {
            goalsExist = true
        }
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
            delay(3000L)
            if(goalsExist){
               navController.navigate("mainView")
            }
            else
            {
                navController.navigate("addGoals")
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
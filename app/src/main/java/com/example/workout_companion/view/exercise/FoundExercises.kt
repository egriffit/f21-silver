package com.example.workout_companion.view.exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workout_companion.api.wger.WgerApi
import com.example.workout_companion.api.wger.entities.wgerExercise
import com.example.workout_companion.enumeration.MuscleGroupConverter
import com.example.workout_companion.viewmodel.WgerAPIViewModel
import kotlinx.coroutines.*

//Displays exercises found from WGER
@Composable
fun FoundExerises(
    navController: NavController,
    muscle: String?,
    wgerAPi: WgerAPIViewModel
){
    Column(){
        Row(){
            Text("Found Exercises for ${muscle}")
        }
        var ExerciseList: MutableList<wgerExercise> = mutableListOf()
        val muscle = MuscleGroupConverter.toMuscleGroup(muscle!!)
        Text("${muscle.wgerMuscles.elementAt(0).id}")
        LaunchedEffect(key1 = Unit, block = {
                wgerAPi.getExercisesByMuscleGroup(9)
        })
        var found = wgerAPi.exerciseInMuscles

            found.forEach { f ->
                f.results.forEach { exercise ->
                    Text("${exercise.name}")
                }
            }


        Row(){
            Button(onClick = {navController.navigate("ExerciseOverview")}){
                Text("Submit")
            }
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Button(onClick = {navController.navigate("ExerciseOverview")}){
                Text("Cancel")
            }
        }
    }
}
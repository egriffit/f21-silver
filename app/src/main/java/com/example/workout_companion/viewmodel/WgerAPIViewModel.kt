package com.example.workout_companion.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
import com.example.workout_companion.api.utility.ExerciseData
import com.example.workout_companion.api.utility.FoodData
import com.example.workout_companion.api.wger.Properties.base_url
import com.example.workout_companion.api.wger.WgerApi
import com.example.workout_companion.api.wger.entities.ExerciseInfo
import com.example.workout_companion.api.wger.entities.wgerExercise
import com.example.workout_companion.api.wger.utility.muscleNameConverter.fromMuscleName
import com.example.workout_companion.api.wger.utility.muscleNameConverter.toMuscleName
import com.example.workout_companion.api.wger.wgerApi
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WgerAPIViewModel: ViewModel() {
    val exerciseInMuscles = mutableStateListOf<wgerExercise>()
    val exerciseInfo = mutableStateListOf<ExerciseInfo>()

    fun getExercisesByMuscleGroup(muscleId: Int) {
        viewModelScope.launch {
            val call = wgerApi()
                .create(WgerApi::class.java)

            val response = try {
                val exercises = call.getExerciseByMuscle(2, muscleId)
                if(exerciseInMuscles.size > 0){
                    exerciseInMuscles.clear()
                    exerciseInMuscles.add(exercises)
                }else{
                    exerciseInMuscles.clear()
                }
            } catch (e: Exception) {
                exerciseInMuscles.clear()
            }
        }

    }

    fun getExericseInfo(exerciseId: Int) {
        viewModelScope.launch {
            val call = wgerApi()
                .create(WgerApi::class.java)

            val response = try {
                val exercise = call.getExerciseById(exerciseId, 2)
                if(exerciseInfo.size > 0){
                    exerciseInfo.clear()
                    exerciseInfo.add(exercise)
                }else{
                    exerciseInfo.clear()
                }
            } catch (e: Exception) {
                exerciseInfo.clear()
            }
        }
    }

    fun getExercisesFromResponse(response: wgerExercise?): List<ExerciseData>{
        var wgerId: Int = 0
        var name: String = ""
        var description:  String = ""
        var image: String = "";
        var muscles: MutableList<String> = mutableListOf()
        var equipment: MutableList<Int> = mutableListOf()
        var exercises: MutableList<ExerciseData> = mutableListOf()
        var images: MutableList<String> = mutableListOf()
        response?.results?.forEach{
            wgerId = it.id
            name = it.name
            description = it.description
            it.muscles.forEach{ m ->
                muscles.add(fromMuscleName(m))
            }
            it.equipment.forEach{ e->
                equipment.add(e)
            }
            exercises.add(ExerciseData(wgerId, name, description, muscles, equipment, images))
            muscles.clear()
            equipment.clear()
            images.clear()
        }
        return exercises
    }

    fun getExercisesFromResponse(response: ExerciseInfo?): ExerciseData{
        var wgerId: Int = 0
        var name: String = ""
        var description:  String = ""
        var image: String = "";
        var muscles: MutableList<String> = mutableListOf()
        var equipment: MutableList<Int> = mutableListOf()
        var images: MutableList<String> = mutableListOf()
            wgerId = response?.id!!
            name = response?.name
            description = response?.description
        response?.muscles?.forEach{ m ->
                var muscle = fromMuscleName(m as Int)
                muscles.add(muscle)
            }
        response?.equipment?.forEach{ e->
                    equipment.add(e.id)
            }
        response?.images?.forEach { i ->
            images.add(i as String)
        }
            return ExerciseData(wgerId, name, description, muscles, equipment, images)
        }
    }
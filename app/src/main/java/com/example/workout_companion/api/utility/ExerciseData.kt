package com.example.workout_companion.api.utility

data class ExerciseData (
            val wgerId: Int,
            val name: String,
            val description:  String,
            val muscles: List<String>,
            val equipment: List<Int>,
            val images: List<String>
        )
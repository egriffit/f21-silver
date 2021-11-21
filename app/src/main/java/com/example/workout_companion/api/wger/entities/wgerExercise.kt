package com.example.workout_companion.api.wger.entities

data class wgerExercise(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)
package com.example.workout_companion.api.edamam.entities

data class Measure(
    val label: String,
    val qualified: List<Qualified>,
    val uri: String
)
package com.example.workout_companion.api.edamam.entities

data class EdamamFood(
    val _links: Links,
    val hints: List<Hint>,
    val parsed: List<Parsed>,
    val text: String
)
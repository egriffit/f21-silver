package com.example.workout_companion.api.edamam.entities

data class Hint(
    val food: Food,
    val measures: List<Measure>
)
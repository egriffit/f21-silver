package com.example.workout_companion.api.wger.entities

data class Image(
    val id: Int,
    val uuid: String,
    val exercise_base: Int,
    val image: String,
    val is_main: Boolean,
    val status: String,
    val style: String
)

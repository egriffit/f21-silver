package com.example.workout_companion.api.wger.entities

data class Muscle(
    val id: Int,
    val name: String,
    val is_front: Boolean,
    val image_url_main: String,
    val image_url_secondary: String
)

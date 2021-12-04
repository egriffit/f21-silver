package com.example.workout_companion.api.wger.entities

data class ExerciseInfo(
    val category: Category,
    val comments: List<Comment>,
    val creation_date: String,
    val description: String,
    val equipment: List<Equipment>,
    val id: Int,
    val images: List<Image>,
    val language: Language,
    val license: License,
    val license_author: String,
    val muscles: List<Muscle>,
    val muscles_secondary: List<Muscle>,
    val name: String,
    val uuid: String,
    val variations: List<Int>
)
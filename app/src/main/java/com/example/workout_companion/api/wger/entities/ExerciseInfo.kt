package com.example.workout_companion.api.wger.entities

data class ExerciseInfo(
    val category: Category,
    val comments: List<Any>,
    val creation_date: String,
    val description: String,
    val equipment: List<Equipment>,
    val id: Int,
    val images: List<Any>,
    val language: Language,
    val license: License,
    val license_author: String,
    val muscles: List<Any>,
    val muscles_secondary: List<Any>,
    val name: String,
    val uuid: String,
    val variations: List<Any>
)
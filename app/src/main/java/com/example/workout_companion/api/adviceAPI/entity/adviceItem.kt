package com.example.workout_companion.api.adviceAPI.entity

data class adviceItem(
    val __v: Int,
    val _id: String,
    val advice: String,
    val adviceType: String,
    val sourceId: String
)
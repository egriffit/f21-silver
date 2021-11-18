package com.example.workout_companion.entity

import java.time.LocalDate
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


/**
 * An entity for the meal table
 *
 * @property recipe_id                  id for recipe in recipe table
 * @property food_id                    id for food in food_type table
 * @property servings                   number of servings of food in meal
 */

@Entity(tableName = "food_in_recipe",
        primaryKeys = ["recipe_id", "food_id"],
        foreignKeys = [
            ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("recipe_id"),
            onDelete = ForeignKey.CASCADE
             ),
            ForeignKey(
                entity = FoodTypeEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("food_id"),
                onDelete = ForeignKey.CASCADE
            ),
        ]
)
data class FoodInRecipeEntity(
    @ColumnInfo(name = "recipe_id")
    var recipe_id: Int,

    @ColumnInfo(name = "food_id")
    var food_id: Int,

    @ColumnInfo(name = "servings")
    var servings: Double
)

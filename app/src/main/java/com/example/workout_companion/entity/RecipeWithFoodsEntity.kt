package com.example.workout_companion.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * An entity joining the food_in_meal, meal, and food_type tables in the database
 *
 * @property recipe             Entity for the FoodInRecipe view
 * @property foods               Entity for the food_type table
 */
data class RecipeWithFoodsEntity (
    @Embedded val food_in_recipe: FoodInRecipeEntity,
    @Relation(
        parentColumn = "recipe_id",
        entityColumn = "id"
    )
    val recipe: RecipeEntity,
    @Relation(
        parentColumn = "food_id",
         entityColumn = "id"
    )
    val foods: List<FoodTypeEntity>
)


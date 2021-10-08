package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.workout_companion.entity.CurrentUserGoalEntity
import com.example.workout_companion.entity.CurrentNutritionPlanAndFrameworkEntity

@Dao
interface CurrentUserGoalDao {

    @Insert
    fun addCurrentUserGoal(item: CurrentUserGoalEntity)

    /**
     * Retrieve the number of rows with nutrition plans where goal_name is equal to the string
     * provided.
     *
     * @param String name of goal
     * @return Int total of rows
     */
    @Transaction
    @Query("""SELECT nutrition_plan_type_id, calorie, carbohydrate, 
                    protein, fat, framework_type_id,name, workouts_per_week,
                    framework_type.goal_id, goal
                    FROM current_user_goal 
                    INNER JOIN (SELECT * FROM nutrition_plan_type
                        INNER JOIN goal_type
                        ON nutrition_plan_type.goal_id = goal_type.id) a,
                        framework_type
                    ON current_user_goal.nutrition_plan_type_id = a.id
                    AND framework_type.id = current_user_goal.framework_type_id
                        LIMit 1""")
    fun getCurrentGoals(): LiveData<CurrentNutritionPlanAndFrameworkEntity>
}
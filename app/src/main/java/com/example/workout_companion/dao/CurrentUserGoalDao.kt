package com.example.workout_companion.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workout_companion.entity.CurrentUserGoalEntity
import com.example.workout_companion.entity.CurrentNutritionPlanAndFrameworkEntity

/**
 * The Data Abstraction Object for the CurrentUserGoalEntity
 *
 * Provides methods for SQL queries using the CurrentUserGoalEntity
 */
@Dao
interface CurrentUserGoalDao {

    /**
     * Retrieves a List of CurrentUserGoalEntity objects from the current_user_goal table
     *
     * @return LiveData<List<UserEntity> a list of UserEntity objects
     */
    @Query("SELECT * FROM current_user_goal")
    fun getCurrentGoalIds(): LiveData<CurrentUserGoalEntity>


    /**
     * Check if there is a record in the current_user_goals table with an id of 1
     *
     * @return Boolean
     */
    @Query("""SELECT EXISTS(SELECT 1 FROM 
        current_user_goal WHERE id = 1) """)
    suspend fun currentGoalExists(): Boolean


    /**
     * Retrieves a CurrentNutritionPlanAndFrameworkEntity object joining all fields in the goal_type,
     * framework_type, nutrition_plan_Type and current_goal_type tables.
     *
     * @return CurrentNutritionPlanAndFrameworkEntity objects
     */
    @Transaction
    @Query("""SELECT current_user_goal.id, nutrition_plan_type_id, calorie, carbohydrate, 
                    protein, fat, framework_type_id,name, workouts_per_week,
                    framework_type.goal_id, goal
                    FROM current_user_goal 
                    INNER JOIN (SELECT * FROM nutrition_plan_type
                        INNER JOIN goal_type
                        ON nutrition_plan_type.goal_id = goal_type.id) a,
                        framework_type
                    ON current_user_goal.nutrition_plan_type_id = a.id
                    AND framework_type.id = current_user_goal.framework_type_id
                    WHERE current_user_goal.id = 1""")
    fun getCurrentGoals(): LiveData<CurrentNutritionPlanAndFrameworkEntity>

    /**
     * Adds a CurrentUserGoalEntity to the current_user_goal table
     *
     * @param item, a CurrentUserGoalEntity
     * @return void
     */
    @Insert
    suspend fun addCurrentUserGoal(item: CurrentUserGoalEntity)

    /**
     * Update a the current_user_goal record with the name of the nutrition_plan_type
     *
     * @param calorie, a double
     * @param protein, a double
     * @param fat, a double
     * @param carbohydrate, a double
     * @return void
     */
    @Transaction
    @Query("""UPDATE current_user_goal
                    SET nutrition_plan_type_id = (SELECT CAST(id as INTEGER)
                                            FROM nutrition_plan_type
                                            WHERE nutrition_plan_type.calorie = :calorie
                                            AND nutrition_plan_type.protein = :protein
                                            AND nutrition_plan_type.fat = :fat
                                            AND nutrition_plan_type.carbohydrate = :carbohydrate)
                    WHERE current_user_goal.id = 1
                                            """)
    suspend fun updateNutritionPlan(calorie: Double, carbohydrate: Double, protein: Double, fat: Double)

    /**
     * Update a the current_user_goal record with the nutrition_plan_type_id
     *
     * @param nutrition_plan_type_id, a int
     * @return void
     */
    @Query("""UPDATE current_user_goal SET nutrition_plan_type_id = :nutrition_plan_type_id""")
    suspend fun updateNutritionPlanID(nutrition_plan_type_id: Int )

    /**
     * Update a the current_user_goal record with the name of the framework_type
     *
     * @param framework_type, a String
     * @return void
     */
    @Transaction
    @Query("""UPDATE current_user_goal
                    SET framework_type_id = (SELECT id
                                            FROM framework_type
                                            WHERE name = :framework_type)
                    WHERE current_user_goal.id = 1
                                            """)
    suspend fun updateFrameworkType(framework_type: String)

    /**
     * Update a the current_user_goal record with the id of the framework_type
     *
     * @param framework_type_id, a int
     * @return void
     */
    @Query("""UPDATE current_user_goal 
                    SET framework_type_id = :framework_type_id  
                    WHERE id = 1""")
    suspend fun updateFrameworkTypeID(framework_type_id: Int )

    /**
     * Update a the current_user_goal record with the name of the framework_type, calorie, carbohydrate, fat, and protein
     *
     * @param framework_type, a String
     * @param calorie, a double
     * @param protein, a double
     * @param fat, a double
     * @param carbohydrate, a double
     * @return void
     */
    @Transaction
    @Query("""UPDATE current_user_goal
                    SET framework_type_id = (SELECT id
                                            FROM framework_type
                                            WHERE name = :framework_type),
                    nutrition_plan_type_id = (SELECT id
                                            FROM nutrition_plan_type
                                            WHERE calorie = :calorie
                                            AND protein = :protein
                                            AND fat = :fat
                                            AND carbohydrate = :carbohydrate)
                     WHERE current_user_goal.id = 1
                                            """)
    suspend fun updateNutritionPlanAndFramework(framework_type: String, calorie: Double, carbohydrate: Double, protein: Double, fat: Double)

    /**
     * Update a the current_user_goal record with the provided nutrition_plan_type and framework_type ids
     *
     * @param framework_type_id, a int
     * @param nutrition_plan_type_id, a int
     * @return void
     */
    @Transaction
    @Query("""UPDATE current_user_goal
                    SET framework_type_id = :framework_type_id,
                    nutrition_plan_type_id = :nutrition_plan_type_id
                     WHERE current_user_goal.id = 1
                                            """)
    suspend fun updateNutritionPlanAndFrameworkID(framework_type_id: Int, nutrition_plan_type_id: Int)

    /**
     * A basic update function
     * @param item, a CurrentUserGoalEntity
     */
    @Update
    suspend fun update(item: CurrentUserGoalEntity)

    /**
     * Delete current_user_goals record to clear user goals
     *
     * @return void
     */
    @Transaction
    @Query("""DELETE FROM current_user_goal
                     WHERE current_user_goal.id = 1
                                            """)
    suspend fun delete()
}
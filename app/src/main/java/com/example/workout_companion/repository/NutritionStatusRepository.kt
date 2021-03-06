package com.example.workout_companion.repository

import androidx.lifecycle.LiveData
import androidx.room.Transaction
import com.example.workout_companion.dao.NutritionStatusDao
import com.example.workout_companion.entity.NutritionStatusEntity
import com.example.workout_companion.enumeration.NutritionStatusEnum
import java.time.LocalDate

/**
 * NutritionStatusRepository class that abstracts the methods in the NutritionStatusDao
 *
 */
class NutritionStatusRepository(private val nutritionStatusDao: NutritionStatusDao) {
    /**
     * Retrieves a nutrition status object from nutrition status table for given date
     *
     * @return LiveData<NutritionStatusEntity>
     */
    fun getStatusByDate(date: LocalDate): LiveData<NutritionStatusEntity> {
        return nutritionStatusDao.getStatusByDate(date)
    }

    @Transaction
    suspend fun getTodaysNutritionStatus(): NutritionStatusEntity{
        return nutritionStatusDao.getTodaysStatus()
    }
    /**
     * Retrieves all nutrition status from nutrition status table
     *
     * @return LiveData<List<NutritionStatusEntity>>
     */
    val getAllStatuses: LiveData<List<NutritionStatusEntity>> = nutritionStatusDao.getAllStatuses()

    /**
     * Retrieves a nutrition status object from nutrition status table for given id
     *
     * @return LiveData<NutritionStatusEntity>
     */
    fun getCurrentStatus(id: Int): LiveData<NutritionStatusEntity> {
        return nutritionStatusDao.getCurrentStatus(id)
    }

    /**
     * Add a status to the nutrition status table
     *
     *@param item NutritionStatusEntity
     * @return void
     */
    @Transaction
    suspend fun insert(item: NutritionStatusEntity){
        if(nutritionStatusDao.getCount(item.date) > 0){
            nutritionStatusDao.update2(item.status, item.date)
        }else{
            nutritionStatusDao.insert(item)
        }
    }

    /**
     * Retrieves number of nutrition statuses in the nutrition status table for the current int
     *
     * @return Int
     */
    suspend fun getCount( date: LocalDate): Int{
        return nutritionStatusDao.getCount(date)
    }

    /**
     * update a nutrition status in the nutrition status table
     *
     * @param item NutritionStatusEntity
     * @return void
     */
    suspend fun update(item: NutritionStatusEntity){
        return nutritionStatusDao.update(item)
    }

}
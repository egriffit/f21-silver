package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/***
 * ViewModel class for the UserEntity object
 * @parm Context, application context
 * @return AndroidViewModel
 */
class UserViewModel(application: Application): AndroidViewModel(application) {
    /**
     * Retrieves a list of all UserEntities in the users table
     */
    val readAll: LiveData<List<UserEntity>>

    val user: LiveData<UserEntity>

    /**
     * UserRepository Object
     */
    private val repository: UserRepository

    /**W
     * Function to initialize the view.
     * Initializes the WCDatabase, repository and the list of all user entities
     */
    init{
        val userDao = WCDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao = userDao)
        readAll = repository.getAll
        user = repository.user
    }

    /**
     * Function to initialize a coroutine to add a userEntity to the database
     * @param item, a UserEntityUserViewModel.kt
     */
    fun addUser(item: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.addUser(item = item)
    }

    /**
     * Function to initialize a coroutine to update a user in the users table with the values in the provided UserEntity
     * @param item, a UserEntity
     */
    fun updateUser(item: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUser(item = item)
    }

    /**
     * Function to initialize a coroutine to delete a user in the users table with the values in the provided UserEntity
     * @param item, a UserEntity
     */
    fun deleteUser(item: UserEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteUser(item = item)
        }
    }

    /**
     * Function to initialize a coroutine to retrieve the age of the user with a name equal to the provided string
     * @param name, a String
     * @return Int
     */
    fun getAge(name: String): Int{
        var age: Int = 0
        viewModelScope.launch(Dispatchers.IO){
           age = repository.getAge(name)
        }
        return age
    }

    /**
     * Function to initialize a coroutine to retrieve the age of the user with a name equal to the provided string
     * @param name, a String
     * @return Int
     */
    fun getHeightInInches(name: String): Int{
        var heightInInches: Int = 0
        viewModelScope.launch(Dispatchers.IO){
            heightInInches = repository.getAge(name)
        }
        return heightInInches
    }

    fun getWeight(name: String) : Double {
        var weight = 0.0
        viewModelScope.launch(Dispatchers.IO) {
            weight = repository.getWeight(name)
        }
        return weight
    }
}
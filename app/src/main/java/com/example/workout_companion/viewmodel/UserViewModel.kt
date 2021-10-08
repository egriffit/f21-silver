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
    }

    /**
     * Function to initialize a coroutine to add a userEntity to the database
     * @param item, a UserEntityUserViewModel.kt
     */
    fun addUser(item: UserEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(item = item)
        }
    }

    /**
     * Function to initialize a coroutine to update a user in the users table with the values in the provided UserEntity
     * @param item, a UserEntity
     */
    fun updateUser(item: UserEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUser(item = item)
        }
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
}

/**
 * UserViewModel Factory class that is used to initialize the UserViewModel
 * @param Application context
 * @return ViewModelProvider.Factory
 */
class UserViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory{
    /**
     * Method to create an instance of the UserModelView
     */
    override fun <T: ViewModel?> create(modelClass: Class<T>): T{
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(application) as T
        }
        throw IllegalArgumentException("Unkown View Model Class")
    }
}
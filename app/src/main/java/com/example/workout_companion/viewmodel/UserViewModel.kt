package com.example.workout_companion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.workout_companion.database.WCDatabase
import com.example.workout_companion.entity.UserEntity
import com.example.workout_companion.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class UserViewModel(application: Application): AndroidViewModel(application) {
    val readAll: LiveData<List<UserEntity>>
    private val repository: UserRepository

    init{
        val userDao = WCDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao = userDao)
        readAll = repository.readAll
    }

    fun addUser(item: UserEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(item = item)
        }
    }

    fun updateUser(item: UserEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUser(item = item)
        }
    }

    fun deleteUser(item: UserEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteUser(item = item)
        }
    }
}

class UserViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory{
    override fun <T: ViewModel?> create(modelClass: Class<T>): T{
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(application) as T
        }
    throw IllegalArgumentException("Unkown View Model Class")
}
}
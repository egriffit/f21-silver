package com.example.workout_companion.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout_companion.api.adviceAPI.AdviceApi
import com.example.workout_companion.api.adviceAPI.Properties.base_url
import com.example.workout_companion.api.adviceAPI.adviceApi
import com.example.workout_companion.api.adviceAPI.entity.adviceItem
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdviceAPIViewModel: ViewModel() {
    val advice = mutableStateListOf<adviceItem>()
    var success = false

    fun getAdviceByType(adviceType: String) {
        viewModelScope.launch {
            val call = adviceApi()
                .create(AdviceApi::class.java)

            try {
                val found = call.getAdviceByType(adviceType)
                success = true
                advice.clear()
                if(found.size > 0){
                    for(a in found) {
                        advice.add(a)
                    }
                }
            } catch (e: Exception) {
                success = false

                advice.clear()
            }
        }

    }
}
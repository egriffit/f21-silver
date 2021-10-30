//package com.example.workout_companion.viewmodel;
//
//
//import android.app.Application
//import androidx.lifecycle.*
//import com.example.workout_companion.api.nutrition_api_ninja.Properties
//import com.example.workout_companion.api.nutrition_api_ninja.NutritionApiNinjaApi
//import com.example.workout_companion.api.nutrition_api_ninja.entities.ApiNinjaNutrition
//import com.example.workout_companion.api.nutrition_api_ninja.nutritionApiNinjaApi
//import com.example.workout_companion.api.nutrition_api_ninja.repository.NutritionApiNinjaRepository
//import com.example.workout_companion.api.utility.FoodData
//import com.example.workout_companion.entity.MealWithFoodsEntity
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import retrofit2.Retrofit
//import java.lang.IllegalArgumentException
//
//class NutritionApiNinjaViewModel(application: Application): AndroidViewModel(application){
//    /**
//     * API Object
//     */
//    private val api: Retrofit
//
//    /**
//     * NutritionApiNinja Repository Object
//     */
//    private val repository: NutritionApiNinjaRepository
//
//        /**
//         * Function to initialize the view.
//         * Initializes the Nurition API by App Ninja and repository
//         */
//        init {
//            api = nutritionApiNinjaApi()
//            repository = NutritionApiNinjaRepository(api)
//        }
//
//    /**
//     * Function to initialize a coroutine to retrieve the  ApiNinjaNutrition object from the
//     * Nutrition by Api Ninja api witha  provided search term
//     * @param food: string
//     * @return List<FoodData>
//     */
//    fun getFood(food: String): LiveData<List<FoodData>> {
//        var found: ApiNinjaNutrition? = null
//        var foundFoods = MutableLiveData<List<FoodData>>()
//
//        viewModelScope.launch(Dispatchers.IO){
//            found = repository.getFood(food).data
//        }
//        foundFoods.postValue(getFoodsFromResponse(found))
//        return foundFoods
//    }
//
//    /**
//     * Static function to convert response to a list of FoodData objects
//     *
//     * @param response, ApiNinjaNutrition object
//     * @return List<FoodData>
//     */
//    companion object {
//        fun getFoodsFromResponse(response: ApiNinjaNutrition?): List<FoodData> {
//            var name: String = ""
//            var calories: Int = 0
//            var carbohydrates: Double = 0.0
//            var protein: Double = 0.0
//            var fat: Double = 0.0
//            var foodId: String = ""
//            var servings = mutableMapOf<String, Double>()
//            var foodList: MutableList<FoodData> = mutableListOf()
//            response?.forEach {
//                name = it.name
//                calories = it.calories.toInt()
//                carbohydrates = it.carbohydrates_total_g
//                protein = it.protein_g
//                fat = it.fat_total_g
//                servings.put("gram", it.serving_size_g)
//                foodList.add(
//                    FoodData(
//                        name,
//                        foodId,
//                        calories,
//                        carbohydrates,
//                        protein,
//                        fat,
//                        servings
//                    )
//                )
//            }
//            return foodList
//        }
//    }
//
//    /**
//     * EdamamViewModel Factory class that is used to initialize the EdamamViewModel
//     * @param application context
//     * @return ViewModelProvider.Factory
//     */
//    class NutritionAPiNinjaViewModelFactory(
//        private val application: Application
//    ): ViewModelProvider.Factory{
//        /**
//         * Method to create an instance of the EdamamViewModel
//         */
//        override fun <T: ViewModel?> create(modelClass: Class<T>): T{
//            @Suppress("UNCHECKED_CAST")
//            if (modelClass.isAssignableFrom(NutritionApiNinjaViewModel::class.java)) {
//                return NutritionApiNinjaViewModel(application) as T
//            }
//            throw IllegalArgumentException("Unknown View Model Class")
//        }
//    }
//
//
//
//}

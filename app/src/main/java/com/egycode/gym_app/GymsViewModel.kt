package com.egycode.gym_app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel() : ViewModel() {

    var state by mutableStateOf(emptyList<Gym>())
    private var gymApiService: GymApiService

    private val gymsDao = GymsDataBase.getDaoInstance(GymsApplication.getApplicationContext())

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gyms-fe7ee-default-rtdb.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        gymApiService = retrofit.create(GymApiService::class.java)
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch {
            val gyms = getAllGyms()
            state = gyms
        }
    }

    private suspend fun getAllGyms() = withContext(Dispatchers.IO) {
        try {
           updateLocalDatabase()
        } catch (e: Exception) {
            if(gymsDao.getAllGyms().isEmpty())
                throw Exception("Something went wrong . no data found , try connecting to internet.")
        }
        gymsDao.getAllGyms()
    }

    private suspend fun updateLocalDatabase(){
        val gyms = gymApiService.getGyms()
        val favouriteGymsList = gymsDao.getFavouriteGyms()
        gymsDao.addAllGyms(gyms)
        gymsDao.updateAllFavourite(favouriteGymsList.map{
            GymsFavouriteState(it.id ,true)
        })
    }

    fun toggleFavouriteState(gymId: Int) {
        val gyms = state.toMutableList()
        val indexGym = gyms.indexOfFirst { it.id == gymId }
        viewModelScope.launch {
            state =  toggleFavoriteGym(gymId,!gyms[indexGym].isFavourite)
        }
    }

    private suspend fun toggleFavoriteGym(gymId: Int, newFavouriteState: Boolean) = withContext(Dispatchers.IO){
        gymsDao.setFavouriteGym(GymsFavouriteState(gymId,newFavouriteState))
        gymsDao.getAllGyms()
    }



}


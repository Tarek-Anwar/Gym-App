package com.egycode.gym_app

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsRepository {

     private var gymApiService: GymApiService = Retrofit.Builder()
        .baseUrl("https://gyms-fe7ee-default-rtdb.firebaseio.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GymApiService::class.java)

     private val gymsDao = GymsDataBase.getDaoInstance(GymsApplication.getApplicationContext())


    private suspend fun updateLocalDatabase() {
        val gyms = gymApiService.getGyms()
        val favouriteGymsList = gymsDao.getFavouriteGyms()
        gymsDao.addAllGyms(gyms)
        gymsDao.updateAllFavourite(favouriteGymsList.map {
            GymsFavouriteState(it.id, true)
        })
    }

     suspend fun getAllGyms() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (gymsDao.getAllGyms().isEmpty())
                throw Exception("Something went wrong . no data found , try connecting to internet.")
        }
        gymsDao.getAllGyms()
    }

     suspend fun toggleFavoriteGym(gymId: Int, newFavouriteState: Boolean) =
        withContext(Dispatchers.IO) {
            gymsDao.setFavouriteGym(GymsFavouriteState(gymId, newFavouriteState))
            gymsDao.getAllGyms()
        }
}
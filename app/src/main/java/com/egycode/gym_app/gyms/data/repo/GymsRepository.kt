package com.egycode.gym_app.gyms.data.repo

import com.egycode.gym_app.GymsApplication
import com.egycode.gym_app.gyms.data.local.GymsDataBase
import com.egycode.gym_app.gyms.data.local.LocalGym
import com.egycode.gym_app.gyms.data.local.LocalGymsFavouriteState
import com.egycode.gym_app.gyms.data.remote.GymApiService
import com.egycode.gym_app.gyms.domain.Gym
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
        gymsDao.addAllGyms(
            gyms.map { LocalGym( it.id, it.name, it.place, it.isOpen) }
        )
        gymsDao.updateAllFavourite(favouriteGymsList.map {
            LocalGymsFavouriteState(it.id, true)
        })
    }

    suspend fun loadGyms() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (gymsDao.getAllGyms().isEmpty())
                throw Exception("Something went wrong . no data found , try connecting to internet.")
        }
    }

    suspend fun getGyms() = withContext(Dispatchers.IO) { gymsDao.getAllGyms().map {
        Gym(it.id, it.name, it.place, it.isOpen,it.isFavourite)
    } }
    suspend fun toggleFavoriteGym(gymId: Int, state: Boolean) =
        withContext(Dispatchers.IO) {
            gymsDao.setFavouriteGym(LocalGymsFavouriteState(gymId, state))
            gymsDao.getAllGyms()
        }
}
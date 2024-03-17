package com.egycode.gym_app.gyms.data.repo

import com.egycode.gym_app.gyms.data.local.GymsDao
import com.egycode.gym_app.gyms.data.local.LocalGym
import com.egycode.gym_app.gyms.data.local.LocalGymsFavouriteState
import com.egycode.gym_app.gyms.data.remote.GymApiService
import com.egycode.gym_app.gyms.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsRepository @Inject constructor(
    private var gymApiService: GymApiService,
    private val gymsDao: GymsDao
) {


    private suspend fun updateLocalDatabase() {
        val gyms = gymApiService.getGyms()
        val favouriteGymsList = gymsDao.getFavouriteGyms()
        gymsDao.addAllGyms(
            gyms.map { LocalGym(it.id, it.name, it.place, it.isOpen) }
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

    suspend fun getGyms() = withContext(Dispatchers.IO) {
        gymsDao.getAllGyms().map {
            Gym(it.id, it.name, it.place, it.isOpen, it.isFavourite)
        }
    }

    suspend fun toggleFavoriteGym(gymId: Int, state: Boolean) =
        withContext(Dispatchers.IO) {
            gymsDao.setFavouriteGym(LocalGymsFavouriteState(gymId, state))
            gymsDao.getAllGyms()
        }


}
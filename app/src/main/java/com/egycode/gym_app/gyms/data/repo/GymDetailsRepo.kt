package com.egycode.gym_app.gyms.data.repo

import com.egycode.gym_app.gyms.data.local.GymsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymDetailsRepo @Inject constructor(
    private val gymsDao: GymsDao
) {

    suspend fun getGymFromRoom(id: Int) = withContext(Dispatchers.IO) {
        gymsDao.getGymSelected(id)

    }

}
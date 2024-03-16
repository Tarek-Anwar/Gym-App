package com.egycode.gym_app.gyms.data.repo

import com.egycode.gym_app.GymsApplication
import com.egycode.gym_app.gyms.data.local.GymsDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GymDetailsRepo {

    private val gymsDao = GymsDataBase.getDaoInstance(GymsApplication.getApplicationContext())

    suspend fun getGymFromRoom(id: Int) = withContext(Dispatchers.IO) {
        gymsDao.getGymSelected(id)

    }

}
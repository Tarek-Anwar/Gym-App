package com.egycode.gym_app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymDetailsViewModel(
    private val saveStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf<Gym?>(null)
    private val gymsDao = GymsDataBase.getDaoInstance(GymsApplication.getApplicationContext())

    init {
        val gymId = saveStateHandle.get<Int>("gym_id") ?: 0
        getGym(gymId)
    }

    private fun getGym(id: Int){
        try {
            viewModelScope.launch {
                val gym = getGymFromRoom(id)
                state = gym
            }
        }catch (e : Exception){
            throw Exception(e.message)
        }
    }

    private suspend fun getGymFromRoom(id : Int) = withContext(Dispatchers.IO){
        gymsDao.getGymSelected(id)
    }

}
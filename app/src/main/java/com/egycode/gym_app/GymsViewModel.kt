package com.egycode.exercise

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

class GymsViewModel(
    private val stateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(emptyList<GymModel>())
    private var gymApiService: GymApiService


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
          val gyms = getGymsFromRemoteDB()
          state = gyms.restoreSelectedGyms()
      }
    }

    private suspend fun getGymsFromRemoteDB() = withContext(Dispatchers.IO){ gymApiService.getGyms()}

    fun toggleFavouriteState(gymId: Int) {
        val gyms = state.toMutableList()
        val indexGym = gyms.indexOfFirst { it.id == gymId }
        gyms[indexGym] = gyms[indexGym].copy(isFavourit = !gyms[indexGym].isFavourit)
        storeSelectedGyms(gyms[indexGym])
        state = gyms
    }

    private fun storeSelectedGyms(gym: GymModel) {
        val saveHandelList = stateHandle.get<List<Int>>(FAV_IDS).orEmpty().toMutableList()
        if (gym.isFavourit) saveHandelList.add(gym.id)
        else saveHandelList.remove(gym.id)
        stateHandle[FAV_IDS] = saveHandelList
    }

    private fun List<GymModel>.restoreSelectedGyms(): List<GymModel> {
        stateHandle.get<List<Int>>(FAV_IDS)?.let { saveIDs ->
            saveIDs.forEach { gymId ->
                this.find { it.id == gymId }?.isFavourit = true
            }
        }
        return this
    }

    companion object {
        const val FAV_IDS = "FavouriteGymsIds"
    }
}


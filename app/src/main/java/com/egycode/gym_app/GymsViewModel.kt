package com.egycode.gym_app

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class GymsViewModel : ViewModel() {

    private var _state by mutableStateOf(
        GymsScreenState(
            emptyList(),
            true
        )
    )

    val state : State<GymsScreenState>
        get() = derivedStateOf { _state }

    private val repo = GymsRepository()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _state = _state.copy(
            isLoading = false,
            error = throwable.message
        )
    }

    init {
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(errorHandler) {
            val gyms = repo.getAllGyms()
            _state = GymsScreenState(gyms, false)
        }
    }

    fun toggleFavouriteState(gymId: Int) {
        val gyms = _state.gyms.toMutableList()
        val indexGym = gyms.indexOfFirst { it.id == gymId }
        viewModelScope.launch {
           val updateGymsList = repo.toggleFavoriteGym(gymId, !gyms[indexGym].isFavourite)
            _state = _state.copy(gyms = updateGymsList)
        }
    }


}


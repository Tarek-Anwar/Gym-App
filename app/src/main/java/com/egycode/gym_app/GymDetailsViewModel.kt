package com.egycode.gym_app

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class GymDetailsViewModel(
    private val saveStateHandle: SavedStateHandle
) : ViewModel() {

    private var _state by mutableStateOf(
        GymDetailsState(
            null,
            true
        )
    )

    val state: State<GymDetailsState>
        get() = derivedStateOf { _state }

    private val repo = GymDetailsRepo()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _state = _state.copy(
            isLoading = false,
            error = throwable.message
        )
    }

    init {
        val gymId = saveStateHandle.get<Int>("gym_id") ?: 0
        getGym(gymId)
    }

    private fun getGym(id: Int) {
        viewModelScope.launch(errorHandler) {
            val gym = repo.getGymFromRoom(id)
            _state = if (gym == null) {
                GymDetailsState(null, false, "Not Gym Found")
            } else {
                GymDetailsState(gym, false)
            }
        }

    }
}
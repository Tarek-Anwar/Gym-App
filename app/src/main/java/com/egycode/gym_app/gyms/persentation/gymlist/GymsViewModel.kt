package com.egycode.gym_app.gyms.persentation.gymlist

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egycode.gym_app.gyms.domain.GetInitialGymsUseCase
import com.egycode.gym_app.gyms.domain.ToggleFavouriteStateUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymsViewModel @Inject constructor(
    private val getAllGymsUseCase: GetInitialGymsUseCase,
    private val toggleFavouriteStateUserCase: ToggleFavouriteStateUserCase
) : ViewModel() {

    private var _state by mutableStateOf(
        GymsScreenState(
            emptyList(),
            true
        )
    )

    val state: State<GymsScreenState>
        get() = derivedStateOf { _state }


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
            val gyms = getAllGymsUseCase()
            _state = GymsScreenState(gyms, false)
        }
    }

    fun toggleFavouriteState(gymId: Int, oldState: Boolean) {
        viewModelScope.launch {
            val updateGymsList = toggleFavouriteStateUserCase(gymId, oldState)
            _state = _state.copy(gyms = updateGymsList)
        }
    }


}


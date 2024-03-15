package com.egycode.gym_app.gyms.domain

import com.egycode.gym_app.gyms.data.repo.GymsRepository

class ToggleFavouriteStateUserCase {
    private val gymsRepository = GymsRepository()
    private val getSortedGymsUseCase = GetSortedGymsUseCase()
    suspend operator fun invoke(gymId: Int, state: Boolean) : List<Gym> {
        gymsRepository.toggleFavoriteGym(gymId, state.not())
        return getSortedGymsUseCase()
    }
}
package com.egycode.gym_app.gyms.domain

import com.egycode.gym_app.gyms.data.repo.GymsRepository
import javax.inject.Inject

class ToggleFavouriteStateUserCase @Inject constructor(
    private val gymsRepository: GymsRepository,
    private val getSortedGymsUseCase: GetSortedGymsUseCase
) {

    suspend operator fun invoke(gymId: Int, state: Boolean): List<Gym> {
        gymsRepository.toggleFavoriteGym(gymId, state.not())
        return getSortedGymsUseCase()
    }
}
package com.egycode.gym_app.gyms.domain

import com.egycode.gym_app.gyms.data.repo.GymsRepository

class GetInitialGymsUseCase {
    private val gymsRepository = GymsRepository()
    private val getSortedGymsUseCase = GetSortedGymsUseCase()
    suspend operator fun invoke() : List<Gym> {
        gymsRepository.loadGyms()
        return getSortedGymsUseCase()
    }
}
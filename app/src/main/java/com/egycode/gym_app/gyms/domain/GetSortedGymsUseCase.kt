package com.egycode.gym_app.gyms.domain

import com.egycode.gym_app.gyms.data.repo.GymsRepository
import javax.inject.Inject

class GetSortedGymsUseCase @Inject constructor(
    private val gymsRepository: GymsRepository

) {
    suspend operator fun invoke() = gymsRepository.getGyms().sortedBy { it.name }
}
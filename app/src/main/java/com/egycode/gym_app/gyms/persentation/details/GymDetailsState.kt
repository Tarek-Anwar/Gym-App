package com.egycode.gym_app.gyms.persentation.details

import com.egycode.gym_app.gyms.domain.Gym

data class GymDetailsState(
    var gym : Gym?,
    val isLoading : Boolean,
    val error : String? = null
)
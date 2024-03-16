package com.egycode.gym_app.gyms.persentation.gymlist

import com.egycode.gym_app.gyms.domain.Gym

data class GymsScreenState(
    var gyms : List<Gym>,
    val isLoading : Boolean,
    val error : String? = null
)
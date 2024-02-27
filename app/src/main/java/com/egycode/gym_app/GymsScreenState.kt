package com.egycode.gym_app

data class GymsScreenState(
    var gyms : List<Gym>,
    val isLoading : Boolean,
    val error : String? = null
)
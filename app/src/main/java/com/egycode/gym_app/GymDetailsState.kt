package com.egycode.gym_app

data class GymDetailsState(
    var gym : Gym?,
    val isLoading : Boolean,
    val error : String? = null
)
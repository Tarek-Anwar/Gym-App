package com.egycode.exercise

import com.google.gson.annotations.SerializedName


data class GymModel(
    val id : Int,
    @SerializedName("gym_name")
    val name: String,
    @SerializedName("gym_location")
    val place: String,
    @SerializedName("is_open")
    val isOpen : Boolean,
    var isFavourit : Boolean = false
)

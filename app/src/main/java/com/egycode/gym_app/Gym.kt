package com.egycode.gym_app

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("gyms")
data class Gym(
    @PrimaryKey
    @ColumnInfo("gym_id")
    val id : Int,

    @ColumnInfo("gym_name")
    @SerializedName("gym_name")
    val name: String,

    @ColumnInfo("gym_location")
    @SerializedName("gym_location")
    val place: String,

    @SerializedName("is_open")
    val isOpen : Boolean,

    @ColumnInfo("is_favourite")
    val isFavourite : Boolean = false
)
